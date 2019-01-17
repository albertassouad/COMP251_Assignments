import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.math.*;
import static java.lang.System.out;

public class islands {

	private static final String INPUT_FILE = "testIslands.txt";
	private static final String OUTPUT_FILE = "testIslands_solution.txt";

	public static void main(String[] a) {
		//long startTime = System.currentTimeMillis();
		
		FileReader fr =  null;
		FileWriter fw = null;

		try {
			
			fr = new FileReader(INPUT_FILE);
	        BufferedReader in = new BufferedReader(fr);
	        
			fw = new FileWriter(OUTPUT_FILE);
			BufferedWriter out = new BufferedWriter(fw);

			
			int number_problems = Integer.parseInt(in.readLine()); // first line is the number of problems
			String[] mapDimension = new String[2]; // size of the 2d array
			
			// define array here for scope purposes (size doesnt matter)
			char[][] map = new char[3][1];
			
			// the loop below will iterate through each problem
			while (number_problems != 0) {
				mapDimension = in.readLine().split("\\s+");

				// create an array of size m x n
				 map = new char[Integer.parseInt(mapDimension[0])][Integer.parseInt(mapDimension[1])];

				// populate the map array
				for (int i = 0; i < Integer.parseInt(mapDimension[0]); i++) {

					String stringLine = in.readLine();
					if (stringLine != null) {
						map[i] = stringLine.toCharArray();
					}
				}
				
				out.write(Integer.toString(numberOFislands(map)));
				out.newLine();
				number_problems--;
			}
			
			in.close();
			out.close();
			
			/*long endTime = System.currentTimeMillis();
			System.out.print("Total time: " + (endTime - startTime) + " ms");*/
			
		} catch (IOException e) {
			System.out.println(e.toString());
			System.exit(1);
		}

	}

	public static int numberOFislands(char[][] map) {
		//long startTime = System.currentTimeMillis();
		int firstOceanPixel = -1;
		DisjointSets djs = new DisjointSets( (map.length) * (map[0].length) ); 

		for( int i = 0 ; i < map.length ; i++) {
			for( int j = 0 ; j < map[0].length ; j++) {
		
				if( map[i][j] == '-') { // if it is a land , check its neighbor

					
					// upper middle neighbor
					if( i != 0  && map[i-1][j] == '-') {
						djs.union( i*map[0].length + j , (i-1)*map[0].length + j);
					}

					
					// left neighbor
					if( j != 0 && map[i][j-1] == '-') {
						djs.union( i*map[0].length + j , i*map[0].length + j-1);
					}
					
					// right neighbor
					if(j != (map[0].length - 1)  && map[i][j+1] == '-') {
						djs.union( i*map[0].length + j , i*map[0].length + j+1);
					}

					
					// lower middle neighbor
					if( i != (map.length - 1 ) && map[i+1][j] == '-') {
						djs.union( i*map[0].length + j , (i+1)*map[0].length + j);
					}
					
					
				}
				else {
					if(firstOceanPixel == -1) {
						firstOceanPixel = i*map[0].length + j;
					}
					else {
						djs.union( i*map[0].length + j , firstOceanPixel);
					}
				}
			}
		}

		int numOfislands = djs.getNumberOfSets() - 1;
        return numOfislands; 
	}

}

/****************************
 *
 * COMP251 template file
 *
 * Assignment 2, Question 1
 *
 *****************************/

class DisjointSets {

	private int[] par;
	private int[] rank;

	 //Constructor: creates a partition of n elements. 
	 //Each element is in a separate disjoint set 
	DisjointSets(int n) {
		if (n > 0) {
			par = new int[n];
			rank = new int[n];
			for (int i = 0; i < this.par.length; i++) {
				par[i] = i;
			}
		}
	}

	public String toString() {
		int pari, countsets = 0;
		String output = "";
		String[] setstrings = new String[this.par.length];
		 //build string for each set 
		for (int i = 0; i < this.par.length; i++) {
			pari = find(i);
			if (setstrings[pari] == null) {
				setstrings[pari] = String.valueOf(i);
				countsets += 1;
			} else {
				setstrings[pari] += "," + i;
			}
		}
		 //print strings 
		output = countsets + " set(s):\n";
		for (int i = 0; i < this.par.length; i++) {
			if (setstrings[i] != null) {
				output += i + " : " + setstrings[i] + "\n";
			}
		}
		return output;
	}

	 //find resentative of element i 
	public int find(int i) {

		// Fill this method (The statement return 0 is here only to compile) 

		if (par[i] == i) { // if it is a root
			return i;
		} else {
			par[i] = find(par[i]); // Path compression: the new representative of i is the representative of its
									// parent
			return par[i];
		}

	}

	 //merge sets containing elements i and j 
	public int union(int i, int j) {

		 //Fill this method (The statement return 0 is here only to compile) 

		// variable that will hold the value of the representative of the new set (after
		// union)
		int rep = 0;

		// if i and j dont have the same representative
		if (find(i) != find(j)) {
			if (rank[find(j)] >= rank[find(i)]) { // if rank of representative of j is greater or equal to rep of i
				par[find(i)] = par[find(j)]; // assign the representative of j to the representative of i
				rank[find(j)]++; // increment the rank of the new rep
			}

			else { // if rank of representative of i is higher than rank of representative of j
				par[find(j)] = par[find(i)]; // assign the representative of i to the representative of j
				rank[find(i)]++; // increment the rank of the new rep
			}

		}
		// if i and j have the same rep
		else {
			rep = find(i);
		}

		return rep;

	}
	
	public int getNumberOfSets() {
		ArrayList<Integer> parents = new ArrayList<Integer>();
		for (int i = 0; i < par.length; i++)
			if(!parents.contains(find(i))) {
				parents.add(find(i));
			}
		return parents.size();
	}
}