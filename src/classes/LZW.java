package classes;

import java.io.*;
import java.util.*;

public class LZW {
    public static void compressLZW() throws IOException {
        File databaseFile 	= new File("WorkDatabase.db");
        File lzwFile 		= new File("WorkDatabase.lzw");
        FileWriter writeFile;
        FileWriter writeFileLZW;
        BufferedWriter buffToWrite;
        BufferedReader buffToRead;
        ObjectOutputStream fileStream = new ObjectOutputStream(new FileOutputStream("WorkDatabase.lzw"));

        if(databaseFile.exists()) writeFile = new FileWriter(databaseFile, true); else writeFile = new FileWriter(databaseFile);
        
        if(lzwFile.exists()) writeFileLZW = new FileWriter(lzwFile, true); else writeFileLZW = new FileWriter(lzwFile);
        
        buffToWrite = new BufferedWriter(writeFileLZW);
        buffToRead 	= new BufferedReader(new FileReader("WorkDatabase.db"));
        
        String text = "";
        String line;
        
        while((line = buffToRead.readLine()) != null) {
            text = text.concat(line);
            text = text.concat("|");
        }
        
        int dicSize = 256;
        
        Map<String, Integer> dictionary = new HashMap<String, Integer>();
        
        for(int i = 0; i < 256; i++) dictionary.put("" + (char) i, i);
        
        String w = "";
        
        List<Integer> result = new ArrayList<Integer>();
        
        for(char c : text.toCharArray()) {
            String wc = w + c;
            
            if(dictionary.containsKey(wc))
                w = wc;
            else {
                result.add(dictionary.get(w));
                
                dictionary.put(wc, dicSize++);
                w = "" + c;
            }
        }
        
        if(!w.equals("")) result.add(dictionary.get(w));
        
        fileStream.writeObject(result.toString());
        
        buffToRead.close();
        
        buffToWrite.close();
        
        writeFile.close();
        
        fileStream.close();
    }

    public static void decompressLZW() throws IOException {
        BufferedWriter bufferToWrite 	= null;
        File databaseFile 				= null;
        FileWriter lzwFile				= null;
        
        int dicSize = 256;
        
        String inputFile = "WorkDatabase.lzw";
        String text = "";
        
        Map<Integer, String> dictionary = new HashMap<Integer, String>();
        
        InputStream inputStream = new FileInputStream(inputFile);

        databaseFile = new File("WorkDatabase.db");
        lzwFile = new FileWriter(databaseFile);

        bufferToWrite = new BufferedWriter(lzwFile);

        int data = inputStream.read();
        
        while((data = inputStream.read()) != -1) {
            text = text.concat(String.valueOf((char) data));
        }
        
        text = text.substring(7);
        text = text.substring(0, text.length() - 1);
        
        System.out.println(text);
        
        for(int i = 0; i < 256; i++)
            dictionary.put(i, "" + (char) i);
        
        String[] texto = text.split(", ");
        
        List<Integer> compressed = new ArrayList<Integer>();
        
        for(int i = 0; i < texto.length; i++) compressed.add(Integer.parseInt(texto[i]));
        
        String w = "" + (char) (int) compressed.remove(0);
        
        StringBuffer result = new StringBuffer(w);
        
        for(int k : compressed) {
            String entry;
            
            if(dictionary.containsKey(k)) entry = dictionary.get(k); else if (k == dicSize) entry = w + w.charAt(0); else throw new IllegalArgumentException("Bad compressed: " + k);

            result.append(entry);
            dictionary.put(dicSize++, w + entry.charAt(0));

            w = entry;
        }
        
        String r = result.toString();
        String rr = "";
        
        String [] split = r.split("|");
        
        rr = rr.concat(split[0] + "|" + split[1] + "|" + split[2]);
        
        for(int i = 3; i < split.length; i += 3) rr = rr.concat("\n" + split[i] + "|" + split[i+1] + "|" + split[i+2] );
        
        bufferToWrite.write(rr.toString());
        bufferToWrite.close();
        inputStream.close();
    }
}
