package classes;

import java.io.*;

public class CRUD {
	public static void insert(Integer intFromMain, String stringValueFromMain) {
		File 			fileToRead 	= null;
        FileWriter 		fileToWrite = null;
        BufferedWriter 	buffToWrite = null;
        BufferedReader 	buffToRead 	= null;

        try {
        	fileToRead = new File("WorkDatabase.db");

            if(fileToRead.exists() == true) fileToWrite = new FileWriter(fileToRead, true); else fileToWrite = new FileWriter(fileToRead);

            buffToWrite = new BufferedWriter(fileToWrite);
            buffToRead  = new BufferedReader(new FileReader("WorkDatabase.db"));

            int index = 1;
            int smallerValue = Integer.MAX_VALUE;
            int biggerValue  = Integer.MIN_VALUE;
            
            String firstIndex = "1";
            String sortKey    = String.valueOf(intFromMain);
            String line, newContent;

            while((line = buffToRead.readLine()) != null) {
                if(!line.equals("")) {
                    String [] trimmed = line.split("|");
                    firstIndex = trimmed [0];

                    if(!firstIndex.equals("")) {
                        index = Integer.parseInt(firstIndex);
                        smallerValue = index;
                        
                        if(biggerValue < smallerValue) biggerValue = smallerValue;
                        
                        index = biggerValue+1;
                        firstIndex = firstIndex.valueOf(index);
                    }
                } else {
                    firstIndex = "1";
                }
            }

            newContent = firstIndex + "|" + sortKey + "|" + stringValueFromMain;

            if(index == 1) buffToWrite.write(newContent);

            if(index != 1) buffToWrite.write("\n" + newContent);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                buffToRead.close();
                buffToWrite.close();
                fileToWrite.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void remove(Integer intFromMain) {
        File 			deleteTempFile 	= null;
        FileWriter 		fileToWrite 	= null;
        File 			finalFile 		= null;
        
        BufferedWriter 	buffToWrite 	= null;
        BufferedReader 	buffToRead 		= null;

        String filePath = "WorkDatabase.db";
        String fileTemp = "WorkDatabaseTemp.db";
        
        int count = 0;
        
        try {
            deleteTempFile 	= new File (fileTemp);
            fileToWrite 	= new FileWriter(fileTemp);
            finalFile 		= new File(filePath);
            buffToWrite 	= new BufferedWriter(fileToWrite);
            buffToRead 		= new BufferedReader(new FileReader("WorkDatabase.db"));

            String lineToRemove = String.valueOf(intFromMain);
            String line;

            while((line = buffToRead.readLine()) != null) {
                if(!line.equals("")) {
                    String[] trimmed = line.split("|");
                    String key = trimmed[0];

                    if(!key.equals(lineToRemove)) {
                        if(count == 0) buffToWrite.write(line); else buffToWrite.write("\n" + line);
                        
                        count++;
                    }
                }
            }
            
            buffToRead.close();
            buffToWrite.close();
            fileToWrite.close();

            finalFile.delete();
            deleteTempFile.renameTo(finalFile);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                buffToRead.close();
                buffToWrite.close();
                fileToWrite.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void search(Long longFromMain) {
        RandomAccessFile buffToRead = null;
        
        String line;
        String key;
        String sortKey;
        String value;

        try {
            buffToRead = new RandomAccessFile("WorkDatabase.db", "rw");
            buffToRead.seek(longFromMain == null ? 0 : longFromMain);

            line = buffToRead.readLine();
            
            String[] trimmed = line.split("|");
            
            key 	= trimmed[0];
            sortKey = trimmed[2];
            value 	= trimmed[4];

            if(longFromMain != null) System.out.println(key + "|" + sortKey + "|" + value); else System.out.println("Not Found!");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                buffToRead.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void update(Integer key, Integer newsortKey, String value) {
        BufferedReader 	buffToRead 	= null;
        FileWriter 		fileToWrite = null;

        try {
            buffToRead = new BufferedReader(new FileReader("WorkDatabase.db"));

            String line;
            String newString 	= "";
            String oldString 	= "";
            String oldContent 	= "";
            String updateLine 	= String.valueOf(key);

            while((line = buffToRead.readLine()) != null) {
                if(!line.equals("")) {
                    oldContent = oldContent + line + System.lineSeparator();
                    
                    String[] 	trimmed = line.split("|");
                    String 		index 	= trimmed[0];

                    if(index.equals(updateLine)) {
                        oldString = line;

                        String typeToUpdate;
                        
                        typeToUpdate = String.valueOf(newsortKey);

                        String valueToUpdate;
                        
                        valueToUpdate = value;

                        newString = key + "|" + typeToUpdate + "|" + valueToUpdate;
                    }
                }
            }
            
            String newContent = oldContent.replaceAll(oldString,newString);

            fileToWrite = new FileWriter(("WorkDatabase.db"));
            fileToWrite.write(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                buffToRead.close();
                fileToWrite.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void list() {
        BufferedReader buffToRead = null;
        
        try {
            buffToRead = new BufferedReader(new FileReader("WorkDatabase.db"));
            String line;

            while((line = buffToRead.readLine()) != null) System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                buffToRead.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
