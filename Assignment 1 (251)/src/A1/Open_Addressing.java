package A1;

import static A1.main.*;

public class Open_Addressing {

    public int m; // number of SLOTS AVAILABLE
    public int A; // the default random number
    int w;
    int r;
    public int[] Table;

    //Constructor for the class. sets up the data structure for you
    protected Open_Addressing(int w, int seed) {

        this.w = w;
        this.r = (int) (w - 1) / 2 + 1;
        this.m = power2(r);
        this.A = generateRandom((int) power2(w - 1), (int) power2(w), seed);
        this.Table = new int[m];
        //empty slots are initalized as -1, since all keys are positive
        for (int i = 0; i < m; i++) {
            Table[i] = -1;
        }

    }

    /**
     * Implements the hash function g(k)
     */
    public int probe(int key, int i) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
    	int gfn = (( ((A*key) % power2(w)) >> (w-r)) + i ) % power2(r);
        return gfn;
    }

    /**
     * Checks if slot n is empty
     */
    public boolean isSlotEmpty(int hashValue) {
        return Table[hashValue] == -1;
    }

    /**
     * Inserts key k into hash table. Returns the number of collisions
     * encountered
     */
    public int insertKey(int key) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
    	
    	// create a boolean variable to control the while loop
    	boolean isInserted = false;
    	
    	// i represents the number of collisions
    	int i = 0;
    	
    	// create a while-loop that keep on iterating if the key is not inserted
    	while(!isInserted) {
    		
    	// find the hash value of key
    	int hvalue = probe(key,i);
    	
    	//check if slot is empty or key is already inserted
    	if(isSlotEmpty(hvalue) || Table[hvalue] == key){  
    		Table[hvalue] = key;
    		isInserted = true;
    	}
    	else { 
    		// if you passed through all slots and did not insert the key ---> table is full
    		if(i == Table.length - 1) {
        		i = Table.length;
        		break;
    		}
    		// if key is not inserted, increment i
    		i++;
    	}
    	}
    	
        return i;
    	
    }

    /**
     * Removes key k from hash table. Returns the number of collisions
     * encountered
     */
    public int removeKey(int key) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
    
    	
    	// create a boolean variable to control the while-loop
    	boolean isDeleted = false;
    	
    	// i represents the number of collision
    	int i = 0;
    	
    	//create a while-loop that iterates until the key is deleted
    	while(!isDeleted ) {
    	
    		//find hash value of the key we want to delete
    		int hvalue = probe(key,i);
    		
    		//check if slot holds the key we want to delete
    		if(Table[hvalue] == key) { 
    			Table[hvalue] = -1;
    			isDeleted = true;
    		}
    		else { //increment i if key is not deleted
    			//if we checked all slots and key is not available, return size of the table (we visited every slots)
        		if(i== Table.length - 1) {			
            		//System.out.println("removeskip");
        			i = Table.length ;
            		break;
        		}
    			i++;
    		}
    		
    		}
    	
    	
    	return i;
    }

}
