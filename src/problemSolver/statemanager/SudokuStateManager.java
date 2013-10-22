package problemSolver.statemanager;

import java.util.ArrayList;
import java.util.Arrays;

public class SudokuStateManager extends StateManager {

	public int size;
	public int bulkSize;
	public int sumConflicts;
	public int[] valueFrequency;
	public FixedConstrains[] fixedConstrains;

	public SudokuStateManager(int[][] puzzle) {
		size = puzzle.length;
		bulkSize = (int) Math.sqrt(puzzle.length);
		setValueConstrains(1, size);
		setValuesSize(size * size);

		valueFrequency = new int[size + 1];

		this.constrainedIndexes = new boolean[values.length];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				values[i * size + j] = puzzle[i][j];
				if (puzzle[i][j] != 0)
					constrainedIndexes[i * size + j] = true;
			}
		}

		fixedConstrains = new FixedConstrains[values.length];
		for (int i = 0; i < fixedConstrains.length; i++) {
			fixedConstrains[i] = new FixedConstrains(size);
		}
		checkFixedConstraints();

		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle.length; j++) {
				FixedConstrains f = fixedConstrains[i * size + j];
				for (Integer integer : f) {
					System.out.print("" + integer);
				}
				System.out.print("  ");
			}
			System.out.println();
		}

		// Initiate fields
		sumConflicts = 0;
	}

	private void checkFixedConstraints() {

		boolean hasChanged = true;
		while (hasChanged) {
			hasChanged = false;
			for (int i = 0; i < fixedConstrains.length; i++) {
				int radIndex = (int) Math.floor(i / size);
				int colIndex = i % size;

				for (int k = radIndex * size, to = radIndex * size + size; k < to; k++) {
					if (i != k && isConstrainedIndex(k))
						fixedConstrains[i].removeValue(values[k]);
				}

				for (int j = colIndex; j < values.length; j += size) {
					if (i != j && isConstrainedIndex(j))
						fixedConstrains[i].removeValue(values[j]);
				}

				int startIndex = i - (radIndex % bulkSize) * size - colIndex
						% bulkSize, bulkLength = startIndex + size
						* (bulkSize - 1) + bulkSize;
				for (int k = startIndex; k < bulkLength; k += size) {
					for (int k2 = 0; k2 < bulkSize; k2++) {
						int index = k + k2;
						if (i != index && isConstrainedIndex(index))
							fixedConstrains[i].removeValue(values[index]);
					}
				}

				if (fixedConstrains[i].size() == 1 && !isConstrainedIndex(i)) {
					hasChanged = true;
					constrainedIndexes[i] = true;
					values[i] = fixedConstrains[i].get(0);
				}

			}
		}
	}

	public double getStateValue() {
		calculateConflicts();
		// System.out.println(sumConflicts*1.0 / (3 * size * size));
		return 1.0 - 1.0 * sumConflicts
				/ (size * size * size * size * size);
	}

	public void printState() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.print(values[i * size + j] + " ");
			}
			System.out.println();
		}
		System.out.println();

		System.out.println("Score:" + getStateValue());
	}

	public void printFixed() {
		System.out.println("Fixed values");
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (constrainedIndexes[i * size + j])
					System.out.print("1 ");
				else
					System.out.print("0 ");
			}
			System.out.println();
		}
		System.out.println();
	}

//	@Override
//	public int getRandomConstrained(int index) {
//		// if(index == size*3+size-3)
//		// System.out.println("Random:"+index+"->"+
//		// fixedConstrains[index].getRandom()+" value:"+values[index]);
//		// checkFixedConstraints();
//		// System.out.println( fixedConstrains[index].getRandom());
//		return fixedConstrains[index].getRandom();
//	}

	public void initState() {
		for (int i = 0; i < values.length; i++) {
			if (!constrainedIndexes[i])
				values[i] = 1;
		}
	}

	private void calculateConflicts() {
		sumConflicts = 0;
		for (int i = 0; i < values.length; i++) {
			conflicts[i] = 0;
			int radIndex = (int) Math.floor(i / size);
			int colIndex = i % size;

			for (int k = radIndex * size, to = radIndex * size + size; k < to; k++) {
				if (k != i && values[i] == values[k])
					conflicts[i]++;
			}

			for (int j = colIndex; j < values.length; j += size) {
				if (j != i && values[i] == values[j])
					conflicts[i]++;
			}

			int startIndex = i - (radIndex % bulkSize) * size - colIndex
					% bulkSize, bulkLength = startIndex + size * (bulkSize - 1)
					+ bulkSize;
			for (int k = startIndex; k < bulkLength; k += size) {
				for (int k2 = 0; k2 < bulkSize; k2++) {
					int index = k + k2;
					if (i != index && values[i] == values[index])
						conflicts[i]++;
				}
			}
			sumConflicts += conflicts[i];
		}
	}

	class FixedConstrains extends ArrayList<Integer> {
		public FixedConstrains(int size) {
			for (int i = 0; i < size; i++) {
				this.add(i + 1);
			}
		}

		public void removeValue(int value) {
			for (int i = 0; i < this.size(); i++) {
				if (this.get(i) == value) {
					this.remove(i);
					return;
				}
			}
		}

		public int getRandom() {
			return this.get((int) (Math.random() * this.size()));
		}
	}

	// public static void main(String[] args){
	// SudokuStateManager s = new SudokuStateManager(new int[][]
	// {{0, 0, 0, 0, 4, 2, 0, 0, 8},
	// {0, 4, 0, 3, 0, 0, 2, 7, 0},
	// {0, 5, 6, 7, 0, 0, 0, 0, 3},
	// {3, 0, 0, 0, 0, 0, 0, 0, 1},
	// {0, 9, 1, 8, 0, 3, 5, 6, 0},
	// {6, 0, 0, 0, 0, 0, 0, 0, 9},
	// {5, 0, 0, 0, 0, 7, 1, 3, 0},
	// {0, 6, 7, 0, 0, 5, 0, 8, 0},
	// {8, 0, 0, 2, 6, 0, 0, 0, 0}});
	//
	// s.printState();
	// s.printFixed();
	// }
}
