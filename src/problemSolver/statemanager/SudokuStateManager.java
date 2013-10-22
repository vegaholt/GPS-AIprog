package problemSolver.statemanager;

import java.util.Arrays;

public class SudokuStateManager extends StateManager {

	public int size;
	public int bulkSize;
	public int sumConflicts;
	public int[] valueFrequency;

	public SudokuStateManager(int[][] puzzle) {
		size = puzzle.length;
		bulkSize = (int) Math.sqrt(puzzle.length);
		setValueConstrains(1, size);
		setValuesSize(size * size);

		valueFrequency = new int[size+1];

		this.constrainedIndexes = new boolean[values.length];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				values[i * size + j] = puzzle[i][j];
				if (puzzle[i][j] != 0)
					constrainedIndexes[i * size + j] = true;
			}
		}

		// Initiate fields
		sumConflicts = 0;
	}

	public double getStateValue() {
		calculateConflicts();
		// System.out.println(sumConflicts*1.0 / (3 * size * size));
		return Math.max(0, 1.0 - 1.0 * sumConflicts / (3 * size * size));
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
/*
	@Override
	public int getRandomConstrained(int index) {

		// Nullstill valuesFrequency
		Arrays.fill(valueFrequency, 0);

		// Iterer rad
		int startPoint = (index / size) * size;

		for (int i = startPoint; i < startPoint + size; i++) {
			if (i != index)
				valueFrequency[values[i]]++;
		}

		// Iterer colonne
		startPoint = index % size;
		
		for (int i = startPoint; i < size * size; i += size) {
			if (i != index)
				valueFrequency[values[i]]++;
		}

		//Choose lowest value
		
		int bestIndex = 1;
		int bestValue = 9;
		
		for (int i = 1; i < valueFrequency.length; i++) {
			if(valueFrequency[i]<bestValue){
				bestIndex = i;
				bestValue = valueFrequency[i];
			}
		}
		
		return bestIndex ;
	}*/

	public void initState() {
		for (int i = 0; i < values.length; i++) {
			if (!constrainedIndexes[i])
				values[i] = getRandomConstrained(i);
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
}
