package problemSolver;

import java.util.Arrays;

public class KQueenState implements State {

	public final int k;
	public int[] queens;
	public State[] neighbours;
	public double value;
	public int conflicts;

	// Constructor blank state
	public KQueenState(int k) {
		this.k = k;
	}

	// Constructor state
	public KQueenState(int k, int[] queens, int row, int value) {
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
				System.out.println("Queen index: " + queens[i]);
			}
		}
		System.out.println("Conflicts: " + conflicts);
		System.out.println("Value: " + value);
		System.out.println();
	}

	// Calculate conflicts
	public void calculateConflicts() {
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

		this.conflicts = conflicts;
	}

	// Get conflicts
	public int getConflicts() {
		return conflicts;
	}

	// Calculate state Value
	public void calculateStateValue() {
		this.value = 1 - (double) conflicts / (2 * k - 3);
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
			neighbours[i] = new KQueenState(k, newQueens, row, value);
		}
	}
	
	// Move a conflicting nodeTo
	public void alterConflicted() {
		
		//Choose a random row
		int row = (int)(Math.random()*k);
		
		//Check if the queen is conflicted
		boolean isConflicted = false;
		
		
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

	@Override
	public void swap() {
		// TODO Auto-generated method stub
		
	}


}
