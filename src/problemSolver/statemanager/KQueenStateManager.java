package problemSolver.statemanager;

import java.util.ArrayList;

public class KQueenStateManager extends StateManager {

	public final int k; // Number of queens
	private int[][] minConflicts; // Holds conflicts relative to each cell on the board

	/**
	 * Constructs a new state for KQueen
	 * 
	 * @param k board size
	 */
	public KQueenStateManager(int k) {
		super();
		this.k = k;
		this.setValueConstrains(0, k - 1);
		setValuesSize(k);
	}

	/**
	 * Initiate a blank state by randomly placing a Queen on each row
	 */
	public void initState() {

		// The value array holds the Queen index
		for (int i = 0; i < k; i++) {
			values[i] = (int) (Math.random() * k);
		}

		// Initiates conflicts
		this.minConflicts = new int[k][k];
		for (int i = 0; i < k; i++) {
			addK(i, values[i]);
		}
	}

	/**
	 * Displays the board, number of queens, number of conflicts and the state value
	 */
	public void printState() {
		if (k <= 0) {
			for (int i = 0; i < k; i++) {
				for (int j = 0; j < k; j++) {
					if (values[i] == j)
						System.out.print("1 ");
					else
						System.out.print("0 ");
				}
				System.out.println("Queen index: " + values[i]);
			}
		}
		System.out.println(k + "-Queens");
		System.out.println("Conflicts: " + calculateConflicts());
		System.out.println("Value: " + getStateValue());
	}

	/**
	 * Detects conflicts for each slot on the board
	 * 
	 * @return sum of conflicts
	 */
	private int calculateConflicts() {
		for (int i = 0; i < conflicts.length; i++) {
			conflicts[i] = 0;
		}
		int newConflict = 0;
		for (int i = 0; i < k; i++) {
			for (int j = i + 1; j < k; j++) {
				if (values[i] == values[j] || (values[i] - i == values[j] - j)
						|| (-values[i] - i == -values[j] - j)) {
					newConflict++;
					conflicts[i]++;
					conflicts[j]++;
				}
			}
		}

		return newConflict;
	}

	/**
	 * Calculates the state value. 1 - (number of conflicts / max number of conflicts)
	 * Maximum number of conflicts is calculated to be 2*k - 3, representing that two columns holds all the queens,
	 * with a queen on every odd numbered index on the first column, and even numbered index on the second column.
	 * 
	 * @return state value
	 */
	public double getStateValue() {
		return 1 - (double) calculateConflicts() / (2 * k - 3);
	}

	/**
	 * Swap is overwritten from the StateManager. 
	 * Swap will find a row where the Queen is involved in at least one conflict. 
	 * Then it will remove the Queen and search for the most optimal new placement. 
	 * If more than one such place exists, the placement of the Queen will be random among these. 
	 * The Queen is removed and placed using private methods, removeK(row, column) and addK(row) 
	 */
	@Override
	public void swap() {

		// Find row where K is involved in a conflict
		int row = 0;
		do {
			row = (int) (Math.random() * k);
		} while (minConflicts[row][values[row]] == 0);

		// Removes K in this row
		removeK(row);

		// Find column with the lowest conflicts
		int bestConflicts = k;
		for (int i = 0; i < k; i++) {
			if (minConflicts[row][i] < bestConflicts) {
				bestConflicts = minConflicts[row][i];
			}
		}

		// Columns with the same number of conflicts gets added to a list
		ArrayList<Integer> places = new ArrayList<Integer>();

		for (int i = 0; i < k; i++) {
			if (minConflicts[row][i] == bestConflicts) {
				places.add(i);
			}
		}

		// Randomly choose column from the list
		int random = (int) (Math.random() * places.size());
		int bestIndex = places.get(random);

		// Place K in this column
		addK(row, bestIndex);

	}

	/**
	 * Removes the Queen on a given row in order to recalculate conflicts
	 * 
	 * @param row
	 */
	private void removeK(int row) {

		int column = values[row];
		values[row] = -1;

		// Remove conflict on columns
		for (int j = 0; j < k; j++) {
			minConflicts[j][column] -= 1;
		}
		minConflicts[row][column] += 1;

		// Remove conflict on diagonal-right
		int startColumn = Math.max(0, column - row);
		int startRow = Math.max(0, row - column);

		while (startColumn < k && startRow < k) {

			minConflicts[startRow][startColumn] -= 1;
			startColumn++;
			startRow++;
		}
		minConflicts[row][column] += 1;

		// Remove conflict on diagonal-left
		startColumn = Math.min(k - 1, row + column);
		startRow = Math.max(0, row - (k - 1 - column));

		while (startColumn >= 0 && startRow < k) {

			minConflicts[startRow][startColumn] -= 1;
			startColumn--;
			startRow++;
		}
		minConflicts[row][column] += 1;
	}

	/**
	 * Places a Queen on the given row and column and recalculate the array of
	 * conflicts
	 * 
	 * @param row
	 * @param column
	 */
	private void addK(int row, int column) {

		values[row] = column;

		// Add conflict to columns
		for (int j = 0; j < k; j++) {
			minConflicts[j][column] += 1;
		}
		minConflicts[row][column] -= 1;

		// Add conflict to diagonal-right
		int startColumn = Math.max(0, column - row);
		int startRow = Math.max(0, row - column);

		while (startColumn < k && startRow < k) {

			minConflicts[startRow][startColumn] += 1;
			startColumn++;
			startRow++;
		}
		minConflicts[row][column] -= 1;

		// Add conflict to diagonal-left
		startColumn = Math.min(k - 1, row + column);
		startRow = Math.max(0, row - (k - 1 - column));

		while (startColumn >= 0 && startRow < k) {

			minConflicts[startRow][startColumn] += 1;
			startColumn--;
			startRow++;
		}
		minConflicts[row][column] -= 1;
	}

}
