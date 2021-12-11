package classes;

import java.io.*;
import java.util.*;

public class ExtensibleHash<K, V> {
    HashMap<Integer, Long> bucket 			= new HashMap<Integer, Long>();
    HashMap<Long, Integer> bucketSortKey 	= new HashMap<Long, Integer>();
    
    public void insertSortKey() {
	    RandomAccessFile RACFile = null;
	    
	    String line;
	    String sortKey = "";
	    String filePath = "WorkDatabase.db";
	
	    int key = 0;
	    
	    try {
	        RACFile = new RandomAccessFile(filePath, "rw");
	        
	        long pointer = RACFile.getFilePointer();
	        
	        line = RACFile.readLine();
	        
	        while(line != null) {
	            if(!line.equals("")) {
	                String[] trimmed = line.split("|");
	                
	                sortKey = trimmed[0];
	                
	                if(!sortKey.equals("|")) {
		                key = Integer.valueOf(sortKey);
		                
		                bucketSortKey.put(pointer, key);
	                }
	            }
	            
	            pointer = RACFile.getFilePointer();
	            
	            line = RACFile.readLine();
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            RACFile.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    System.out.println(bucketSortKey);
	}
    
    public void insert() {
        RandomAccessFile RACFile = null;
        
        String line;
        String index = "";
        String filePath = "WorkDatabase.db";
        
        int random = 1;
        
        try {
            RACFile = new RandomAccessFile(filePath, "rw");
            
            long pointer = RACFile.getFilePointer();
            
            line = RACFile.readLine();
            
            while(line != null) {
                if(!line.equals("")) {
                    String[] trimmed = line.split("|");
                    index = trimmed[0];
                    random = Integer.valueOf(index);

                    if(!bucket.containsKey(random)) bucket.put(random, pointer);
                }
                pointer = RACFile.getFilePointer();
                line = RACFile.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                RACFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(bucket);
    }

    void updateHash(Integer idx, Integer sortKey) {
        bucket.put(idx, searchHash(idx));
    }

    public Long searchHash(Integer id) {
    	for(Integer i : bucket.keySet()) {
    		if(i == id) return bucket.get(i);
         }
         return null;
    }

    Long searchHashSK(Integer id) {
        Iterator<Long> j = bucketSortKey.keySet().iterator();
        
        while(j.hasNext()) {
        	Long key = j.next();
        	Integer sortKey = bucketSortKey.get(key);
        	
            if(sortKey == id) return key;
        }
        return null;
    }

    public void removeHash(Integer i) {
        System.out.println("Before remove: " + bucket);
        
        bucket.remove(i);
        
        System.out.println("After remove: " + bucket);
    }
    
    public void listBigger(Integer sortKey, int option) {
        TreeMap<Long,Integer> newMap = new TreeMap<>();
        
        newMap.putAll(bucketSortKey);
        
        if(option == 1) {
            for (Integer i : newMap.values()) {
                if(i > sortKey) CRUD.search(searchHashSK(i));
            }
        } else if(option == 2) {
            TreeMap<Long,Integer> newMapR = reverseHash(bucketSortKey);

            for (Integer i : newMapR.values()) {
                if(i > sortKey) CRUD.search(searchHashSK(i));
            }
        }
    }

    public void listSmaller(Integer sortKey, int option) {
        TreeMap<Long,Integer> newMap = new TreeMap<>();
        
        newMap.putAll(bucketSortKey);
        
        if(option == 1) {
            for (Integer i : newMap.values()) {
                if(i < sortKey) CRUD.search(searchHashSK(i));
            }
        } else if(option == 2) {
            TreeMap<Long,Integer> newMapR = reverseHash(bucketSortKey);

            for (Integer i : newMapR.values()) {
                if(i < sortKey) CRUD.search(searchHashSK(i));
            }
        }
    }
    
    public void listSame(Integer sortKey,int option) {
        TreeMap<Long,Integer> newMap = new TreeMap<>();
        
        newMap.putAll(bucketSortKey);

        if(option == 1) {
            for(Integer i : newMap.values()) {
                if(sortKey == i) CRUD.search(searchHashSK(i));
            }
        } else if(option == 2) {
            TreeMap<Long,Integer> newMapR = reverseHash(bucketSortKey);

            for(Integer i : newMapR.values()) {
                if(i == sortKey) CRUD.search(searchHashSK(i));
            }
        }
    }
    
    public void listSmallerOrSame(Integer sortKey, int option) {
        TreeMap<Long,Integer> newMap = new TreeMap<>();
        
        newMap.putAll(bucketSortKey);
        
        if(option == 1) {
            for(Integer i : newMap.values()) {
                if(i <= sortKey) CRUD.search(searchHashSK(i));
            }
        } else if(option==2) {
            TreeMap<Long,Integer> newMapR = reverseHash(bucketSortKey);

            for(Integer i : newMapR.values()) {
                if(i <= sortKey) CRUD.search(searchHashSK(i));
            }
        }
    }
    
    public void listBiggerOrSame(Integer sortKey, int option) {
        TreeMap<Long,Integer> newMap = new TreeMap<>();
        
        newMap.putAll(bucketSortKey);
        
        if(option == 1) {
            for(Integer i : newMap.values()) {
                if(i >= sortKey) CRUD.search(searchHashSK(i));
            }
        } else if(option == 2) {
            TreeMap<Long,Integer> newMapR = reverseHash(bucketSortKey);

            for(Integer i : newMapR.values()) {
                if(i >= sortKey) CRUD.search(searchHashSK(i));
            }
        }
    }

    TreeMap<Long, Integer> reverseHash(HashMap<Long,Integer> hash) {
        TreeMap<Long,Integer> map = new TreeMap<>(Collections.reverseOrder());
    
        map.putAll(bucketSortKey);
        
        return map;
    }
}

