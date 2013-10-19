package problemSolver;

import java.util.ArrayList;

public class test {

	public static int k = 5;
	public static int[] queens = { 4, 3, 1, 4, 2 };
	public static int[][] conflicts;
	public static int sumConflicts;

	public static void main(String[] args) {
		
		ArrayList<Integer> places = new ArrayList<Integer>();
		
		places.add(1);
		places.add(4);
		
		
	}
	
	public static void alterConflicted(){
	
		//Find row where K is involved in a conflict
		int row = 0;
		do{
			row = (int)(Math.random()*k);	
		}while(conflicts[row][queens[row]]==0);
		
		System.out.print("Move queen in row " + row + " from col " + queens[row]);
		
		//Removes K in this row
		removeK(row);

		//Find the best column to place K in this row
		int bestIndex = 0;
		int bestConflicts = k;
		
		for (int i = 0; i < k; i++) {
			if(conflicts[row][i] < bestConflicts){
				bestConflicts = conflicts[row][i];
				bestIndex = i;
			}
		}
		System.out.println(" to col " + bestIndex);
		
		//Place K in this column
		addK(row, bestIndex);
	}
	
	private static void initConflicts() {
		// Iterate through each queen
		for (int i = 0; i < k; i++) {
			addK(i, queens[i]);
		}
	}

	private static void removeK(int row) {

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

	private static void addK(int row, int column) {

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

	public static void printConflicts() {
		System.out.println("Conflicts");
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < k; j++) {
				System.out.print(conflicts[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void printState() {
		System.out.println("State");
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < k; j++) {
				if (queens[i] == j)
					System.out.print("1 ");
				else
					System.out.print("0 ");
			}
			System.out.println("Queen index: " + queens[i] + " conflict: "
					+ conflicts[i][queens[i]]);
		}
		System.out.println();
	}

	public static void calculateSumConflicts() {
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
		System.out.println("Sum conflicts 1:" + sumConflicts);
	}

}
