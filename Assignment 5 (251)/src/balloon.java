import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.math.*;
import static java.lang.System.out;

/**
 * @author albert
 *
 */
public class balloon {

	private static final String INPUT_FILE = "testBalloons.txt";
	private static final String OUTPUT_FILE = "testBalloons_solution.txt";

	public static void main(String[] a) {
		//long startTime = System.currentTimeMillis();
		FileReader fr = null;
		FileWriter fw = null;

		try {
			fr = new FileReader(INPUT_FILE);
			BufferedReader in = new BufferedReader(fr);
			
			fw = new FileWriter(OUTPUT_FILE);
			BufferedWriter out = new BufferedWriter(fw);

			int number_problems = Integer.parseInt(in.readLine()); // first line is the number of problems

			// store the number of balloons for every problem
			String[] number_balloons = new String[number_problems];
			number_balloons = in.readLine().split("\\s+");
			int p = 0; // counter for problems
			int number_arrows = 0;

			while (number_problems != 0) {

				// store every element of this line in array height in string format
				String[] heightS = new String[Integer.parseInt(number_balloons[p])];
				heightS = in.readLine().split("\\s+");

				// convert string array to int array
				int[] height = new int[Integer.parseInt(number_balloons[p])];
				for (int i = 0; i < height.length; i++) {
					height[i] = Integer.parseInt(heightS[i]);
				}

				
				out.write(Integer.toString(arrows_shot(height)));
				out.newLine();
				number_problems--;
				p++;
			}
			
			in.close();
			out.close();
			/*long endTime = System.currentTimeMillis();
			System.out.print("Total time: " + (endTime - startTime) + " ms");
*/
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}

	}

	public static int arrows_shot(int[] height) {
		int balloons_left = height.length;
		int room_length = height.length;

		// store the status of the balloon
		boolean[] pop = new boolean[height.length];

		// number of arrows used
		int arrows = 0;

		int max_height = findMaxHeight(height);

		// height at which the arrow will be shot everytime, first shot will be made at
		// max height
		int h_start = max_height;

		// actual position and height of the arrow
		int x;
		int h;
		
		// the loop below represent a whole problem
		while (balloons_left != 0) {

			x = 0;
			
			// check if there are balloon left at shooter height
			int check = balloon_At_Hstart(h_start, height, pop);
			if (check != -1) {
				h = h_start; 
				x = check; //position of the arrow directly to the balloon  instead of checking every position
				
			// the loop below represents one arrow shot
			while (x != room_length) {
				// if height of balloon at position x equals the actual height of arrow
				if (height[x] == h && !pop[x]) {
					h--; // decrease actual height of balloon
					pop[x] = true; // balloon at position x is destroyed
					balloons_left--; // decrement number of balloons left
				}
				x++;
			}
			arrows++;
			}
			
			//if there are no balloons left decrease shooter's height
			else {
				h_start--;
			}

		}
		return arrows;
	}

	/*
	 * Find max value of an int array
	 */
	private static int findMaxHeight(int[] height) {
		int maxValue = height[0];
		for (int i = 1; i < height.length; i++) {
			if (height[i] > maxValue) {
				maxValue = height[i];
			}
		}
		return maxValue;
	}

	/*
	 * check if there is a balloon at height h_start
	 * return -1 if there are no balloons
	 * return position of first balloon at this height
	 */
	private static int balloon_At_Hstart(int h_start, int[] height, boolean[] pop) {
		int balloonOnH = -1;
		for (int i = 0; i < height.length; i++) {
			if (height[i] == h_start && !pop[i]) {
				balloonOnH = i;
				break;
			}
		}

		return balloonOnH;
	}
}