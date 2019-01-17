package A1;

import java.util.*;
import static A1.main.*;

public class Chaining {

    public int m; // number of SLOTS AVAILABLE
    public int A; // the default random number
    int w;
    int r;
    public ArrayList<ArrayList<Integer>> Table;

    //Constructor for the class. sets up the data structure for you
    protected Chaining(int w, int seed) {
        this.w = w;
        this.r = (int) (w - 1) / 2 + 1;
        this.m = power2(r);
        this.Table = new ArrayList<ArrayList<Integer>>(m);
        for (int i = 0; i < m; i++) {
            Table.add(new ArrayList<Integer>());
        }
        this.A = generateRandom((int) power2(w - 1), (int) power2(w), seed);
    }

    /**
     * Implements the hash function h(k)
     */
    public int chain(int key) {
        //ADD YOUR CODE HERE (change return statement)
    	int hfn = ((A*key) % power2(w)) >> (w-r);
        return hfn ;
    }

    /**
     * Checks if slot n is empty
     */
    public boolean isSlotEmpty(int hashValue) {
        return Table.get(hashValue).size() == 0;
    }

    /**
     * Inserts key k into hash table. Returns the number of collisions
     * encountered
     */
    public int insertKey(int key) {
        //ADD YOUR CODE HERE (chane return statement)
    	/* 
    	 */
    	
    	// check if key is already inserted, to avoid duplicates
    	boolean keyAvailable = false;
    	//for loop that goes through array list to check if key is already inserted
    	//for( int i = 0 ; i < this.Table.size(); i++ ) {
    		/*// for loop that goes through each array/slot
    		for ( int j = 0 ; j < this.Table.get(i).size(); j++) {
    			if( key == this.Table.get(i).get(j)) {
        			keyAvailable = true;
        			break; //exit for-loop
        		}
    		}
    		
    	}
    	
    	if(keyAvailable)
    		return 0;*/
    	//else {
    	// Find the hash value of the key,
    	int hashv = chain(key);
    	
    	//index of the key if it is available
    	int j = 0;
    	//check if key is already in the slot
    	for ( j = 0 ; j < this.Table.get(hashv).size(); j++) {
			if( key == this.Table.get(hashv).get(j)) {
    			keyAvailable = true;
    			break; //exit for-loop
    		}
    	}
    	if(keyAvailable) {
    		return j;
    	}
    	else {
    	// Count the number of keys already in the slot (or arraylist),number of collisions ecause we add at the end of the list
    	int i = Table.get(hashv).size();
    	
    	//  add the key to the arraylist contained in the approrpriate slot 
    	Table.get(hashv).add(key);
    	
        return i;
    	}
    	}
    }
