package database;

import java.io.IOException;

import classes.CRUD;
import classes.ExtensibleHash;
import classes.SelectionSort;

import static classes.LZW.*;
import static classes.Huffman.*;


public class Main {
    public static void main(String[] args) throws IOException {
        ExtensibleHash 	HashClass = new ExtensibleHash();

        if(args.length == 0 || args[0].equals("--help")) {
            System.out.println("WorkDatabase\n" +
                    "  --insert=<sort-key, value>\n" +
                    "  --remove=<key>\n" +
                    "  --search=<key>\n" +
                    "  --update=<key, sort-key, value>\n" +
                    "  --list=<expr>\n" +
                    "  --reverse-list=<expr>\n" +
                    "  --compress=[lzw || huffman]\n" +
                    "  --decompress=[lzw || huffman]\n");
        } else {
            String[] arg = args[0].split("=");
            
            if(arg.length == 1) System.out.println("Out of sufficient arguments to proceed in this operation! \n Enter the command: --help");
            
            HashClass.insert();
            HashClass.insertSortKey();
            
            if(arg[0].equals("--insert")) {
            	String[] insertArg = arg[1].split(",");
                
                if(insertArg.length == 2) {
                	CRUD.insert(Integer.valueOf(insertArg[0]), insertArg[1]);
                	
                	HashClass.insert();
                	HashClass.insertSortKey();
                } else System.out.println("Invalid arguments to proceed in this operation! \n Enter the command: --help");
            } else if(arg[0].equals("--remove")) {
            	CRUD.remove(Integer.valueOf(arg[1]));
                
                HashClass.removeHash(Integer.valueOf(arg[1]));
                HashClass.insert();
                HashClass.insertSortKey();
            } else if(arg[0].equals("--search")) {
            	CRUD.search(HashClass.searchHash(Integer.valueOf(arg[1])));
            } else if(arg[0].equals("--update")) {
            	String[] updateArg = arg[1].split(",");
                
                if(updateArg.length == 3) {
                    CRUD.update(Integer.valueOf(updateArg[0]), Integer.valueOf(updateArg[1]), updateArg[2]);
                    HashClass.insert();
                    HashClass.insertSortKey();
                } else System.out.println("Invalid arguments to proceed in this operation! \n Enter the command: --help");
            } else if(arg[0].equals("--list")) {
            	String[] n;
                
                if(arg[1].matches("key<[0-9]+")) {
                    n = arg[1].split("<");
                    
                    HashClass.listSmaller(Integer.valueOf(n[1]), 1);
                } else if(arg[1].matches("key>[0-9]+")) {
                    n = arg[1].split(">");
                    
                    HashClass.insertSortKey();
                    HashClass.listBigger(Integer.valueOf(n[1]), 1);
                } else if(arg.length == 3 && arg[1].matches("key") && arg[2].matches("[0-9]+")) {
                	HashClass.listSame(Integer.valueOf(arg[2]), 1);
                } else if(arg.length == 3 && arg[1].matches("key<") && arg[2].matches("[0-9]+")) {
                	HashClass.listSmallerOrSame(Integer.valueOf(arg[2]), 1);
                } else if(arg.length == 3 && arg[1].matches("key>") && arg[2].matches("[0-9]+")) {
                	HashClass.listBiggerOrSame(Integer.valueOf(arg[2]), 1);
                } else System.out.println("This option doesn't exists! \n Enter the command: --help");
            } else if(arg[0].equals("--reverse-list")) {
            	String[] n;
            	
            	if(arg[1].matches("key<[0-9]+")) {
                    n = arg[1].split("<");
                    
                    HashClass.listSmaller(Integer.valueOf(n[1]), 2);
                } else if(arg[1].matches("key+>[0-9]+")) {
                    n = arg[1].split(">");
                    
                    HashClass.listBigger(Integer.valueOf(n[1]), 2);
                } else if(arg[1].matches("key") && arg[2].matches("[0-9]+")) {
                	HashClass.listSame(Integer.valueOf(arg[2]), 2);
                } else if(arg.length == 3 && arg[1].matches("key<") && arg[2].matches("[0-9]+")) {
                	HashClass.listSmallerOrSame(Integer.valueOf(arg[2]), 2);
                } else if(arg.length == 3 && arg[1].matches("key+>") && arg[2].matches("[0-9]+")) {
                	HashClass.listBiggerOrSame(Integer.valueOf(arg[2]), 2);
                } else System.out.println("This option doesn't exists! \n Enter the command: --help");
            } else if(arg[0].equals("--compress")) {
            	if(arg[1].equals("lzw")) {
                    compressLZW();
                } else if(arg[1].equals("huffman")) {
                    compressHuffman();
                } else System.out.println("This option doesn't exists! \n Enter the command: --help");
            } else if(arg[0].equals("--decompress")) {
            	if(arg[1].equals("lzw")) {
                    decompressLZW();
                } else if(arg[1].equals("huffman")) {
                    decompressHuffman();
                } else System.out.println("This option doesn't exists! \n Enter the command: --help");
            } else {
            	System.out.println("Unavailable option!");
            }
        }
    }
}
