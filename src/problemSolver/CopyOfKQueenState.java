package problemSolver;

import java.util.Arrays;

import com.sun.org.apache.xpath.internal.operations.Quo;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class CopyOfKQueenState implements State {

	public final int k;
	public int[] queens;
	public State[] neighbours;
	public State best;
	
	public double value;
	public int conflicts;

	// Constructor blank state
	public CopyOfKQueenState(int k) {
		this.k = k;
	}

	// Constructor state
	public CopyOfKQueenState(int k, int[] queens, int row, int value) {
		this.k = k;
		this.queens = queens;
		queens[row] = value;

		this.conflicts = calculateConflicts();
		this.value = calculateStateValue(conflicts);
	}

	// Initiate state
	public void initState() {
		queens = new int[k];
		for (int i = 0; i < k; i++) {
			queens[i] = (int) (Math.random() * k);
		}
		this.conflicts = calculateConflicts();
		this.value = calculateStateValue(conflicts);

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
	private int calculateConflicts() {
		//long time2 = System.nanoTime();
		int newConflict = 0;
		for (int i = 0; i < k; i++) {
			for (int j = i+1; j < k; j++) {
				if(queens[i] == queens[j] || (queens[i]-i == queens[j]-j) || 
						(-queens[i]-i == -queens[j]-j)) newConflict++;
			}
		}
		
		return newConflict;
	}

	// Get conflicts
	public int getConflicts() {
		return conflicts;
	}

	// Calculate state Value
	private double calculateStateValue(int conflicts) {
		return 1 - (double) conflicts / (2 * k - 3);
	}

	// Get state value
	public double getStateValue() {
		return value;
	}

	// Generates n neighbour states
	public void generateNeighbours(int n) {
		int bestCol=0, bestRow=0, oldCol=0;
		float bestCon = 10000, newCon;
		for (int i = 0; i < n; i++) {
			int row = (int) (Math.random() * k);
			int col = (int) (Math.random() * k);
			oldCol = queens[row];
			queens[row] = col;
			newCon = this.calculateConflicts();
			//Revert change
			queens[row] = oldCol;
			if(newCon < bestCon){
				bestCon = newCon;
				bestRow = row;
				bestCol = col;
			}
			
		}
		
		best = new CopyOfKQueenState(k, Arrays.copyOf(queens, k), bestRow, bestCol);
		
	}

	// Get the best neighbour
	public State getBestNeighbour() {
		return best;
	}

	// Get a random neighbour
	public State getRandomNeighbour() {
		return new CopyOfKQueenState(k, Arrays.copyOf(queens, k),  (int) (Math.random() * k),  (int) (Math.random() * k));
	}

}
