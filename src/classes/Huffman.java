package classes;

import java.util.PriorityQueue;

import libs.BinaryIn;
import libs.BinaryOut;

public class Huffman {
    private static int R = 256;
    
    private static class NoTrie implements Comparable<NoTrie> {
        private char 	symbol;
        private int 	frequence;
        private NoTrie 	left;
        private NoTrie 	right;

        NoTrie(char symbol, int frequence, NoTrie left, NoTrie right) {
            this.symbol 	= symbol;
            this.frequence 	= frequence;
            this.left 		= left;
            this.right 		= right;
        }

        NoTrie(char symbol) {
            this.symbol = symbol;
        }

        NoTrie(NoTrie left, NoTrie right) {
            this.left 	= left;
            this.right 	= right;
        }
        
        boolean isSheet() {
            return left == null && right == null;
        }
        
        @Override
        public int compareTo(NoTrie nt) {
            return this.frequence - nt.frequence;
        }
    }
    
    private static NoTrie createTrie(int[] frequence) {
        PriorityQueue<NoTrie> priorityQueue = new PriorityQueue<>();
        
        for (char c = 0; c < R; c++) {
            if (frequence[c] > 0) priorityQueue.add(new NoTrie(c, frequence[c], null, null));
        }
        
        while(priorityQueue.size() > 1) {
            NoTrie first = priorityQueue.remove();
            NoTrie second = priorityQueue.remove();
            
            NoTrie parent = new NoTrie('\0', first.frequence + second.frequence, first, second);
            priorityQueue.add(parent);
        }
        return priorityQueue.remove();
    }

    private static void createCode(String[] var, NoTrie nt, String str) {
        if(nt.isSheet()) {
            var[nt.symbol] = str;
            return;
        }
        
        createCode(var, nt.left, str + '0');
        createCode(var, nt.right, str + '1');
    }
    
    private static void writeTrie(NoTrie nt, BinaryOut out) {
        if (nt.isSheet()) {
            out.write(true);
            out.write(nt.symbol);
        } else {
            out.write(false);
            
            writeTrie(nt.left, out);
            writeTrie(nt.right, out);
        }
    }
    
    public static void compressHuffman() {
        BinaryIn  in 	= new BinaryIn("WorkDatabase.db");
        BinaryOut out 	= new BinaryOut("WorkDatabase.huffman");

        String input 	= in.readString();
        char[] message 	= input.toCharArray();

        int[] frequence = new int[R];

        for(int i = 0; i < message.length; i++) frequence[message[i]]++;

        NoTrie trie = createTrie(frequence);

        String[] trieString = new String[R];
        
        createCode(trieString, trie, "");

        writeTrie(trie, out);

        out.write(message.length);

        for(int i = 0; i < message.length; i++) {
            String code = trieString[message[i]];
            
            for(int j = 0; j < code.length(); j++) {
                if(code.charAt(j) == '1') out.write(true); else out.write(false);
            }
        }
        out.close();
    }
    
    public static void decompressHuffman() {
        BinaryIn in = new BinaryIn("WorkDatabase.huffman");
        BinaryOut out = new BinaryOut("WorkDatabase.db");

        NoTrie trie = readTrie(in);
        
        int n = in.readInt();
        
        for(int i = 0; i < n; ++i) {
            NoTrie nt = trie;
            do {
                if(in.readBoolean()) nt = nt.right; else nt = nt.left;
            } while (!nt.isSheet());
            
            out.write(nt.symbol);
        }
        out.close();
    }
    
    private static NoTrie readTrie(BinaryIn bin) {
        if (bin.readBoolean()) return new NoTrie(bin.readChar()); else return new NoTrie(readTrie(bin), readTrie(bin));
    }
}