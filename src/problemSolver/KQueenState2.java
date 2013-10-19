package problemSolver;

import java.util.ArrayList;
import java.util.Arrays;

public class KQueenState2 implements State {

	public final int k; 		// Size of the grid
	public int[] queens; 		// Index for queens
	public int[][] conflicts; 	// Conflicts in each cell
	public double value; 		// State value
	public int sumConflicts; 	// Total number of conflicts
	public State[] neighbours; 	// State neighbours

	// Constructor blank state
	public KQueenState2(int k) {
		this.k = k;
	}

	// Constructor state
	public KQueenState2(int k, int[] queens, int row, int value) {
		this.k = k;
		this.queens = queens;
		queens[row] = value;

		calculateConflicts();
		calculateStateValue();
	}
	
	// Initiate state
	public void initState() {
		queens = new int[k];

		for (int i = 0; i < k; i++) {
			queens[i] = (int) (Math.random() * k);
		}
		calculateConflicts();
		calculateStateValue();
		initConflicts();	//Bare bruk av min-conflict
	}
	
	// Display solution
	public void printState() {
		if (k <= 50) {
			for (int i = 0; i < k; i++) {
				for (int j = 0; j < k; j++) {
					if (queens[i] == j)
						System.out.print("1 ");
					else
						System.out.print("0 ");
				}
				System.out.println();
			}
		}
		System.out.println("Conflicts: " + getConflicts());
		System.out.println("Value: " + getStateValue());
		System.out.println();
	}

	// Calculates the sum of conflicts
	private void calculateConflicts() {
		int conflicts = 0;
		int conflictCount = 0;

		// Get conflicts on column
		for (int i = 0; i < k; i++) {
			conflictCount = 0;
			for (int j = 0; j < k; j++) {
				if (queens[j] == i) {
					conflictCount++;
				}
			}
			conflicts += Math.max(0, (conflictCount - 1));
		}

		// Get conflicts on diagonal-right
		for (int i = 0; i < k; i++) {
			conflictCount = 0;
			for (int j = 0; i + j < k; j++) {
				if (queens[i + j] == j) {
					conflictCount++;
				}
			}
			conflicts += Math.max(0, (conflictCount - 1));
		}

		for (int i = 1; i < k; i++) {
			conflictCount = 0;
			for (int j = 0; i + j < k; j++) {
				if (queens[j] == (i + j)) {
					conflictCount++;
				}
			}
			conflicts += Math.max(0, (conflictCount - 1));
		}

		// Get conflicts on diagonal-left
		for (int i = k - 1; i > 0; i--) {
			conflictCount = 0;
			for (int j = 0; j < k; j++) {
				if (queens[j] == (i - j)) {
					conflictCount++;
				}
			}
			conflicts += Math.max(0, (conflictCount - 1));
		}

		for (int i = 1; i < k; i++) {
			conflictCount = 0;
			for (int j = 0; i + j < k; j++) {
				if (queens[i + j] == (k - 1 - j)) {
					conflictCount++;
				}
			}
			conflicts += Math.max(0, (conflictCount - 1));
		}

		sumConflicts = conflicts;
	}

	// Get conflicts
	public int getConflicts() {
		return sumConflicts;
	}

	// Calculate state Value
	private void calculateStateValue() {
		this.value = 1 - (double) sumConflicts / (2 * k - 3);
	}

	// Get state value
	public double getStateValue() {
		return value;
	}

	// Generates n neighbour states
	public void generateNeighbours(int n) {
		neighbours = new State[n];

		for (int i = 0; i < n; i++) {
			int row = (int) (Math.random() * k);
			int value = (int) (Math.random() * k);
			int[] newQueens = Arrays.copyOf(queens, k);
			neighbours[i] = new KQueenState2(k, newQueens, row, value);
		}
	}

	// Get the best neighbour
	public State getBestNeighbour() {

		double bestValue = 0;
		int bestIndex = 0;

		for (int i = 0; i < neighbours.length; i++) {
			if (neighbours[i].getStateValue() > bestValue) {
				bestValue = neighbours[i].getStateValue();
				bestIndex = i;
			}
		}
		return neighbours[bestIndex];

	}

	// Get a random neighbour
	public State getRandomNeighbour() {
		int random = (int) (Math.random() * neighbours.length);
		return neighbours[random];
	}

	// Init conflict array
	private void initConflicts() {
		conflicts = new int[k][k];
		// Iterate through each queen
		for (int i = 0; i < k; i++) {
			addK(i, queens[i]);
		}
	}

	// Relocate conflicted K
	public void swap() {

		// Find row where K is involved in a conflict
		int row = 0;
		do {
			row = (int) (Math.random() * k);
		} while (conflicts[row][queens[row]] == 0);

		// Removes K in this row
		removeK(row);

		// Find column with the lowest conflicts
		int bestConflicts = k;
		for (int i = 0; i < k; i++) {
			if (conflicts[row][i] < bestConflicts) {
				bestConflicts = conflicts[row][i];
			}
		}

		// Columns with the same number of conflicts gets added to a list
		ArrayList<Integer> places = new ArrayList<Integer>();

		for (int i = 0; i < k; i++) {
			if (conflicts[row][i] == bestConflicts) {
				places.add(i);
			}
		}

		// Randomly choose column from the list
		int random = (int) (Math.random() * places.size());
		int bestIndex = places.get(random);

		// Place K in this column
		addK(row, bestIndex);

		// Recalculate sum of conflicts and state value
		calculateConflicts();
		calculateStateValue();
	}

	// Remove K from the grid
	private void removeK(int row) {

		int column = queens[row];
		queens[row] = -1;

		// Remove conflict on columns
		for (int j = 0; j < k; j++) {
			conflicts[j][column] -= 1;
		}
		conflicts[row][column] += 1;

		// Remove conflict on diagonal-right
		int startColumn = Math.max(0, column - row);
		int startRow = Math.max(0, row - column);

		while (startColumn < k && startRow < k) {

			conflicts[startRow][startColumn] -= 1;
			startColumn++;
			startRow++;
		}
		conflicts[row][column] += 1;

		// Remove conflict on diagonal-left
		startColumn = Math.min(k - 1, row + column);
		startRow = Math.max(0, row - (k - 1 - column));

		while (startColumn >= 0 && startRow < k) {

			conflicts[startRow][startColumn] -= 1;
			startColumn--;
			startRow++;
		}
		conflicts[row][column] += 1;
	}

	// Place K on the grid
	private void addK(int row, int column) {

		queens[row] = column;

		// Add conflict on columns
		for (int j = 0; j < k; j++) {
			conflicts[j][column] += 1;
		}
		conflicts[row][column] -= 1;

		// Add conflict on diagonal-right
		int startColumn = Math.max(0, column - row);
		int startRow = Math.max(0, row - column);

		while (startColumn < k && startRow < k) {

			conflicts[startRow][startColumn] += 1;
			startColumn++;
			startRow++;
		}
		conflicts[row][column] -= 1;

		// Add conflict on diagonal-left
		startColumn = Math.min(k - 1, row + column);
		startRow = Math.max(0, row - (k - 1 - column));

		while (startColumn >= 0 && startRow < k) {

			conflicts[startRow][startColumn] += 1;
			startColumn--;
			startRow++;
		}
		conflicts[row][column] -= 1;
	}
}
