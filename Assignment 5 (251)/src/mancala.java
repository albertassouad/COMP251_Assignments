import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.math.*;
import static java.lang.System.out;

public class mancala {

	private static final String INPUT_FILE = "testMancala.txt";
	private static final String OUTPUT_FILE = "testMancala_solution.txt";

	public static void main(String[] a) {
		// long startTime = System.currentTimeMillis();
		String file = INPUT_FILE;
		FileReader fr = null;
		FileWriter fw = null;

		try {
			fr = new FileReader(INPUT_FILE);
			BufferedReader in = new BufferedReader(fr);

			fw = new FileWriter(OUTPUT_FILE);
			BufferedWriter out = new BufferedWriter(fw);

			int number_problems = Integer.parseInt(in.readLine()); // first line is the number of problems

			
			int[] board = new int[12];

			// the loop below will iterate through each problem
			while (number_problems != 0) {
				board = new int[12];
				
				String[] LineS = in.readLine().split("\\s+");
				for (int i = 0; i < 12; i++) {
					board[i] = Integer.parseInt(LineS[i]);
				}
				
				out.write(Integer.toString(findMinPebbles(board)));
				out.newLine();
				number_problems--;

				
			}
			
			in.close();
			out.close();
//			long endTime = System.currentTimeMillis();
//			System.out.print("Total time: " + (endTime - startTime) + " ms");

		} catch (IOException e) {
			System.out.println(e.toString());
			System.exit(1);
		}

	}

	public static int findMinPebbles(int[] board) {
		int min = Integer.MAX_VALUE;
		int currentNbPebbles = 0;
		Random random = new java.util.Random();
		for (int i = 0; i < 100; i++) {
			int[] boardCopy = new int[12];
			for (int j = 0; j < board.length; j++) {
				boardCopy[j] = board[j];
			}
			currentNbPebbles = findNbPebbles(boardCopy, random);
			if (currentNbPebbles < min) {
				min = currentNbPebbles;
			}
			if (min == 0 || min == 1) {
				break;
			}
		}
		return min;
	}

	public static int findNbPebbles(int[] board, Random random) {
		for (int i = 0; i < board.length - 3; i++) {
			if (board[i] == 0 && board[i + 1] == 1 && board[i + 2] == 1 && board[i + 3] == 0) {
				if (random.nextDouble() > 0.5) {
					board[i] = 0;
					board[i + 1] = 0;
					board[i + 2] = 0;
					board[i + 3] = 1;
				} else {
					board[i] = 1;
					board[i + 1] = 0;
					board[i + 2] = 0;
					board[i + 3] = 0;
				}

				i = 0;
			}
		}
		for (int i = 0; i < board.length - 4; i++) {
			if (board[i] == 1 && board[i + 1] == 1 && board[i + 2] == 0 && board[i + 3] == 1 && board[i + 4] == 1) {
				if (random.nextDouble() > 0.5) {
					board[i] = 0;
					board[i + 1] = 0;
					board[i + 2] = 1;
					board[i + 3] = 1;
					board[i + 4] = 1;
				} else {
					board[i] = 1;
					board[i + 1] = 1;
					board[i + 2] = 1;
					board[i + 3] = 0;
					board[i + 4] = 0;
				}

				i = 0;
			}
		}
		if (board[0] == 1 && board[1] == 1 && board[2] == 0) {
			board[0] = 0;
			board[1] = 0;
			board[2] = 1;
			findNbPebbles(board, random);
		}
		for (int i = 0; i < board.length - 3; i++) { 
			if (board[i] == 1 && board[i+1] == 1 && board[i+2] == 1 && board[i+3] == 0) {
				board[i + 1] = 0;
				board[i+2] = 0;
				board[i+3] = 1;
				i = 0;
				findNbPebbles(board, random);
			}
			if (board[i+1] == 0 && board[i+2] == 1 && (((i + 3) == board.length) || board[i+3] == 1)) {
				board[i+1] = 1;
				board[i+2] = 0;
				board[i+3] = 0;
				i = 0;
				findNbPebbles(board, random);
			}
		}

		return countPebbles(board);
	}

	public static int countPebbles(int[] board) {
		int result = 0;
		for (int i = 0; i < board.length; i++) {
			if (board[i] == 1) {
				result++;
			}
		}
		return result;
	}

}