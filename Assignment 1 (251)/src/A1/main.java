package A1;

import A1.Chaining.*;
import A1.Open_Addressing.*;
import java.io.*;
import java.util.*;

public class main {

    /**
     * Calculate 2^w
     */
    public static int power2(int w) {
        return (int) Math.pow(2, w);
    }

    /**
     * Uniformly generate a random integer between min and max, excluding both
     */
    public static int generateRandom(int min, int max, int seed) {
        Random generator = new Random();
        //if the seed is equal or above 0, we use the input seed, otherwise not.
        if (seed >= 0) {
            generator.setSeed(seed);
        }
        int i = generator.nextInt(max - min - 1);
        return i + min + 1;
    }

    /**
     * export CSV file
     */
    public static void generateCSVOutputFile(String filePathName, ArrayList<Double> alphaList, ArrayList<Double> avColListChain, ArrayList<Double> avColListProbe) {
        File file = new File(filePathName);
        FileWriter fw;
        try {
            fw = new FileWriter(file);
            int len = alphaList.size();
            fw.append("Alpha");
            for (int i = 0; i < len; i++) {
                fw.append("," + alphaList.get(i));
            }
            fw.append('\n');
            fw.append("Chain");
            for (int i = 0; i < len; i++) {
                fw.append("," + avColListChain.get(i));
            }
            fw.append('\n');
            fw.append("Open Addressing");
            for (int i = 0; i < len; i++) {
                fw.append(", " + avColListProbe.get(i));
            }
            fw.append('\n');
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        /*===========PART 1 : Experimenting with n===================*/
        //Initializing the three arraylists that will go into the output 
        ArrayList<Double> alphaList = new ArrayList<Double>();
        ArrayList<Double> avColListChain = new ArrayList<Double>();
        ArrayList<Double> avColListProbe = new ArrayList<Double>();

        //Keys to insert into both hash tables
        int[] keysToInsert = {164, 127, 481, 132, 467, 160, 205, 186, 107, 179,
            955, 533, 858, 906, 207, 810, 110, 159, 484, 62, 387, 436, 761, 507,
            832, 881, 181, 784, 84, 133, 458, 36};

        //values of n to test for in the experiment
        int[] nList = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32};
        //value of w to use for the experiment on n
        int w = 10;
        
        for (int n : nList) {

            //initializing two hash tables with a seed
            Chaining MyChainTable = new Chaining(w, 137);
            Open_Addressing MyProbeTable = new Open_Addressing(w, 137);

            /*Use the hash tables to compute the average number of 
                        collisions over keys keysToInsert, for each value of n. 
                        The format of the three arraylists to fillis as follows:
                        
                        alphaList = arraylist of all tested alphas 
                                   (corresponding to each tested n)
                        avColListChain = average number of collisions for each
                                         Chain experiment 
                                         (make sure the order matches alphaList)
                        avColListProbe =  average number of collisions for each
                                         Linear Probe experiment
                                           (make sure the order matches)
                        The CSV file will output the result which you can visualize
             */
            //ADD YOUR CODE HERE
            
            
            
            double totalChainCollision = 0; //sum of collisions during chain expereiment with n key
            double totalProbeCollision = 0; //sum of collisions during linear probe expereiment with n key
            
         // for-loop that insert n first keys from keysToInsert array to the hash tables and computing the total  number of collisions
            for(int i = 0; i<n ; i++) { 
            	//insert the keys in the hash table
            totalChainCollision += MyChainTable.insertKey(keysToInsert[i]); 
            totalProbeCollision += MyProbeTable.insertKey(keysToInsert[i]);
            }
            // divide the total number of collisions by the number of keys entered to get average number of collisions
            avColListChain.add(totalChainCollision / n); 
            avColListProbe.add(totalProbeCollision / n);
            
            // fill the alpha list
            alphaList.add(((double)n)/MyChainTable.m); //cast n into a double before dividing by m
            
            
            
        }

        generateCSVOutputFile("n_comparison.csv", alphaList, avColListChain, avColListProbe);

        /*===========    PART 2 : Test removeKey  ===================*/
 /* In this exercise, you apply your removeKey method on an example.
        Make sure you use the same seed, 137, as you did in part 1. You will
        be penalized if you don't use the same seed.
         */
        //Please not the output CSV will be slightly wrong; ignore the labels.
        ArrayList<Double> removeCollisions = new ArrayList<Double>();
        ArrayList<Double> removeIndex = new ArrayList<Double>();
        int[] keysToRemove = {6, 8, 164, 180, 127, 3, 481, 132, 4, 467, 5, 160,
            205, 186, 107, 179};

        //ADD YOUR CODE HERE
        Open_Addressing MyProbeTable2 = new Open_Addressing(w, 137);
        
        //for-loop to insert the 16 first keys into open adressing table
        for( int i = 0 ; i<16 ; i++) {
        	MyProbeTable2.insertKey(keysToInsert[i]);
        }
        
        // create a double to store number of collisions when removing one key
        double collisions = 0;
        
        // create a variable to store the index of the removed element
        double index = 0;
        
        //for-loop to make the removal process (check if key is available in table, save its index and remove it while storing the number of collisions)
        for(int i = 0; i < keysToRemove.length ; i++) {
        	removeIndex.add(index);
        	index++;
        	collisions = MyProbeTable2.removeKey(keysToRemove[i]);
        	removeCollisions.add(collisions);
        }

        
        generateCSVOutputFile("remove_collisions.csv", removeIndex, removeCollisions, removeCollisions);

        /*===========PART 3 : Experimenting with w===================*/

 /*In this exercise, the hash tables are random with no seed. You choose 
                values for the constant, then vary w and observe your results.
         */
        //generating random hash tables with no seed can be done by sending -1
        //as the seed. You can read the generateRandom method for detail.
        //randomNumber = generateRandom(0,55,-1);
        //Chaining MyChainTable = new Chaining(w, -1);
        //Open_Addressing MyProbeTable = new Open_Addressing(w, -1);
        //Lists to fill for the output CSV, exactly the same as in Task 1.
        ArrayList<Double> alphaList2 = new ArrayList<Double>();
        ArrayList<Double> avColListChain2 = new ArrayList<Double>();
        ArrayList<Double> avColListProbe2 = new ArrayList<Double>();

        //ADD YOUR CODE HERE
        
        int wList[] = {10, 12, 14, 16, 20};
        //For every w, and for an n of 55.0
        for (int ww: wList) {
        	double sumCollisionsChainSimulation = 0;
        	double sumCollisionsProbeSimulation = 0;
        	double alpha = 0;
           	double n = 55;
           	// do 10 simulations
            for (int i = 0; i < 10; i++) {
            	Chaining MyChainTable = new Chaining(ww, -1);
                Open_Addressing MyProbeTable3 = new Open_Addressing(ww, -1);
                
                //calculate load factor
                alpha = n / MyProbeTable3.m;
                
            	int random;
            	double avCollisionsChainSim = 0;
            	double avCollisionsProbeSim = 0;
            	// create a table where we will add duplicates
            	ArrayList<Integer> randomNumberCheck = new ArrayList<Integer>();
            	
            	// add n random numbers to both tables
            	for (int j = 0; j < n; j++) {
            		do {
            		random = generateRandom(0, 60, -1);
            		}
            		while (randomNumberCheck.contains(random)); // check if the key is available in the duplicate list
            		randomNumberCheck.add(random);
            		
            		// sum of the collisions for each key inserted
            		avCollisionsChainSim += MyChainTable.insertKey(random);
            		avCollisionsProbeSim += MyProbeTable3.insertKey(random);
            	}
            	
            	// sum of the averages of each simulation
            	sumCollisionsChainSimulation += (avCollisionsChainSim / n);
            	sumCollisionsProbeSimulation += (avCollisionsProbeSim / n);
            }
            
            // average the sums calculated for each w
            avColListChain2.add(sumCollisionsChainSimulation/10.0);
            avColListProbe2.add(sumCollisionsProbeSimulation/10.0);
            alphaList2.add(alpha);
        }
        
       
        generateCSVOutputFile("w_comparison.csv", alphaList2, avColListChain2, avColListProbe2);

    }

}
