package problemSolver.statemanager;

import java.util.ArrayList;

public class KQueenStateManager extends StateManager {

	public final int k;
	private final int[][] conflicts;

	// Constructor blank state
	public KQueenStateManager(int k) {
		super();
		this.k = k;
		this.conflicts = new int[k][k];
		this.setValueConstrains(0, k - 1);
		setValuesSize(k);
	}

	// Initiate state
	public void initState() {
		for (int i = 0; i < k; i++) {
			values[i] = (int) (Math.random() * k);
		}
		
		//Init conflicts
		for (int i = 0; i < k; i++) {
			addK(i, values[i]);
		}
	}

	// public void revertTo

	// Display solution
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
		System.out.println(k+"-Queens");
		System.out.println("Conflicts: " + calculateConflicts());
		System.out.println("Value: " + getStateValue());
	}

	// Calculate conflicts
	private int calculateConflicts() {
		int newConflict = 0;
		for (int i = 0; i < k; i++) {
			for (int j = i + 1; j < k; j++) {
				if (values[i] == values[j] || (values[i] - i == values[j] - j)
						|| (-values[i] - i == -values[j] - j)) {
					newConflict++;
				}
			}
		}

		return newConflict;
	}

	// Get state value
	public double getStateValue() {
		return 1 - (double) calculateConflicts() / (2 * k - 3);
	}
	
	// Relocate conflicted K
	public void swap() {

		// Find row where K is involved in a conflict
		int row = 0;
		do {
			row = (int) (Math.random() * k);
		} while (conflicts[row][values[row]] == 0);

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
//		calculateConflicts();
//		calculateStateValue();
	}

	// Remove K from the grid
	private void removeK(int row) {

		int column = values[row];
		values[row] = -1;

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

		values[row] = column;

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
