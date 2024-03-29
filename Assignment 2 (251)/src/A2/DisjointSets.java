package A2;
import java.io.*;
import java.util.*;


/****************************
*
* COMP251 template file
*
* Assignment 2, Question 1
*
*****************************/


public class DisjointSets {

    private int[] par;
    private int[] rank;
    
    /* contructor: creates a partition of n elements. */
    /* Each element is in a separate disjoint set */
    DisjointSets(int n) {
        if (n>0) {
            par = new int[n];
            rank = new int[n];
            for (int i=0; i<this.par.length; i++) {
                par[i] = i;
            }
        }
    }
    
    public String toString(){
        int pari,countsets=0;
        String output = "";
        String[] setstrings = new String[this.par.length];
        /* build string for each set */
        for (int i=0; i<this.par.length; i++) {
            pari = find(i);
            if (setstrings[pari]==null) {
                setstrings[pari] = String.valueOf(i);
                countsets+=1;
            } else {
                setstrings[pari] += "," + i;
            }
        }
        /* print strings */
        output = countsets + " set(s):\n";
        for (int i=0; i<this.par.length; i++) {
            if (setstrings[i] != null) {
                output += i + " : " + setstrings[i] + "\n";
            }
        }
        return output;
    }
    
    /* find resentative of element i */
    public int find(int i) {

        /* Fill this method (The statement return 0 is here only to compile) */
    	
    	if(par[i] == i) { //if it is a root
    		return i;
    	}
    	else {
    		par[i] = find(par[i]); // Path compression: the new representative of i is the representative of its parent
    		return par[i];
    	}
        
    }

    /* merge sets containing elements i and j */
    public int union(int i, int j) {
    
        /* Fill this method (The statement return 0 is here only to compile) */
    	
    	// variable that will hold the value of the representative of the new set (after union)
    	int rep = 0; 
    	
    	 // if i and j dont have the same representative
    	if( find(i) != find(j) ) { 
    		if(rank[find(j)] >= rank[find(i)]) { // if rank of representative of j is greater or equal to rep of i
    			par[find(i)] = par[find(j)]; // assign the representative of j to the representative of i
        		rank[find(j)]++; //increment the rank of the new rep
    		}
    		
    		else{ // if rank of representative of i is higher than rank of representative of j
    		par[find(j)] = par[find(i)]; // assign the representative of i to the representative of j
    		rank[find(i)]++; //increment the rank of the new rep
    		}
    		
    		
    	}
    	// if i and j have the same rep
    	else { 
    		rep = find(i);
    	}
    	
        return rep;
        
    }
    
    public static void main(String[] args) {
        
        DisjointSets myset = new DisjointSets(6);
        System.out.println(myset);
        System.out.println("-> Union 2 and 3");
        myset.union(2,3);
        System.out.println(myset);
        System.out.println("-> Union 2 and 3");
        myset.union(2,3);
        System.out.println(myset);
        System.out.println("-> Union 2 and 1");
        myset.union(2,1);
        System.out.println(myset);
        System.out.println("-> Union 4 and 5");
        myset.union(4,5);
        System.out.println(myset);
        System.out.println("-> Union 3 and 1");
        myset.union(3,1);
        System.out.println(myset);
        System.out.println("-> Union 2 and 4");
        myset.union(2,4);
        System.out.println(myset);
        
    }

}
