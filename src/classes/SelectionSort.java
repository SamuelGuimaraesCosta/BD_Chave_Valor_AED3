package classes;

import java.io.*;
import java.util.*;

public class SelectionSort {
    public static void selectionSort(String[] array, int n) {
        BufferedWriter bufferToWrite = null;
        
        for(int i = 0; i < n - 1; i++) {
            int minIndex = i;
            
            String minString = array[i];
            
            for(int j = i + 1; j < n; j++) {
                if(array[j].compareTo(minString) < 0) {
                    minString = array[j];
                    minIndex = j;
                }
            }
            
            if(minIndex != i) {
                String temp = array[minIndex];
                
                array[minIndex] = array[i];
                array[i] = temp;
            }
        }

        Scanner input = new Scanner(System.in);
        
        int option 	= input.nextInt();
        int count 	= 0;

        try {
        	bufferToWrite = new BufferedWriter(new FileWriter("WorkDatabase.db"));

            if (option == 1) {
                for (int k = 0; k < array.length; k++) {
                    if(count == 0) bufferToWrite.write(array[k]); else bufferToWrite.write("\n" + array[k]);
                    
                    count++;
                }
            } else if (option == 2) {
                for (int k = array.length - 1; k >= 0; k--) {
                    if(count == 0) bufferToWrite.write(array[k]); else bufferToWrite.write("\n" + array[k]);
                    
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
            	bufferToWrite.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        input.close();
    }

    public static void readFile() {
        BufferedReader bufferToRead = null;

        try{
        	bufferToRead = new BufferedReader(new FileReader("WorkDatabase.db"));
        	
            String line;
            String content = "";
            
            int counter = 0;

            while((line = bufferToRead.readLine()) != null) {
                if(!line.equals("")) {
                    content = content + line + ",";
                    
                    counter++;
                }
            }

            String[] trimmed = content.split(",");

            selectionSort(trimmed, counter);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
            	bufferToRead.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
