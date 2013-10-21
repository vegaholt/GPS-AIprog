package problemSolver.statemanager;

import java.util.ArrayList;

public class SudokuStateManager extends StateManager {

	public static String[] puzzle = { "9", "34", "0-0-4", "0-1-8", "0-2-7",
			"0-4-5", "0-7-6", "1-0-9", "1-3-4", "1-8-3", "2-0-2", "2-2-6",
			"2-4-8", "2-5-9", "2-6-5", "3-2-4", "3-4-1", "3-5-5", "3-6-6",
			"4-0-1", "4-5-4", "4-7-5", "5-1-7", "5-1-7", "5-2-8", "5-3-2",
			"6-5-8", "6-7-7", "7-0-7", "7-1-5", "7-7-3", "8-1-2", "8-4-3",
			"8-5-7", "8-6-4", "8-7-1" };

	public static int size;
	public static int bulkSize;
	public static int[][] values;
	public static boolean[][] fixedValues;
	public static int[][] conflicts;

	public void swap() {

	}

	public double getStateValue() {
		return 0;
	}

	public void printState() {
		System.out.println("Map");
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.print(values[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public void printFixed() {
		System.out.println("Fixed values");
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (fixedValues[i][j])
					System.out.print("1 ");
				else
					System.out.print("0 ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public void printConflicts() {
		System.out.println("Conflicts");
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.print(conflicts[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public void initState() {

		setValueConstrains(1, 9);
		// Read size from index 0
		size = Integer.parseInt(puzzle[0]);
		bulkSize = (int) Math.sqrt(size);

		// Initiate arrays
		values = new int[size][size];
		fixedValues = new boolean[size][size];
		conflicts = new int[size][size];

		// Read number of fixed values from index 1
		int numberOfFixed = Integer.parseInt(puzzle[1]);

		// Place fixed values in values and mak
		for (int i = 2; i < numberOfFixed + 2; i++) {

			// Split line
			String[] param = puzzle[i].split("-");
			values[Integer.parseInt(param[0])][Integer.parseInt(param[1])] = Integer
					.parseInt(param[2]);
			fixedValues[Integer.parseInt(param[0])][Integer.parseInt(param[1])] = true;

		}

		// Randomly hand out values
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (!fixedValues[i][j]) {
					values[i][j] = getRandomConstrained();
				}
			}
		}

		// Iterate throug each subbulk
		// build list with missing pieces
		// Randomly fill in values from this list

	}

	private void calculateConflicts() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {

				// Row
				for (int k = 0; k < size; k++) {
					if (values[i][j] == values[i][k])
						conflicts[i][j]++;
				}
				conflicts[i][j]--;

				// Column
				for (int k = 0; k < size; k++) {
					if (values[i][j] == values[k][j])
						conflicts[i][j]++;
				}
				conflicts[i][j]--;

				// Iterates throug each sub bulk
				int startRow = (i / bulkSize) * bulkSize;
				int startCol = (j / bulkSize) * bulkSize;

				for (int row = startRow; row < startRow + bulkSize; row++) {
					for (int col = startCol; col < startCol + bulkSize; col++) {
						if (values[i][j] == values[row][col])
							conflicts[i][j]++;
					}
				}
				conflicts[i][j]--;

			}
		}
		// System.out.println(n);
	}

	public static void main(String[] args) {
		SudokuStateManager sudoku = new SudokuStateManager();
		sudoku.initState();
		sudoku.printState();
		sudoku.printFixed();
		sudoku.calculateConflicts();
		sudoku.printConflicts();
	}

}
