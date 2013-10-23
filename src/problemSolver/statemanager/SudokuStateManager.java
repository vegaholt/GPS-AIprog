package problemSolver.statemanager;

import java.util.ArrayList;

public class SudokuStateManager extends StateManager {
	//Size of board
	public int size;
	//Size of bulk = sqrt(size)
	public int bulkSize;
	//Number of current conflicts on board
	private int sumConflicts;
	//Constrains for each of the sudoku squares
	private FixedConstrains[] fixedConstrains;
	//Counter used in checking conflicts
	private int[] valueCount;
	/**
	 * Sudoku constructor
	 * @param puzzle k*k array with 0 for not fixed value and 1-k for fixed
	 */
	public SudokuStateManager(int[][] puzzle) {
		size = puzzle.length;
		bulkSize = (int) Math.sqrt(puzzle.length);
		setValueConstrains(1, size);
		setValuesSize(size * size);

		this.constrainedIndexes = new boolean[values.length];
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				values[i * size + j] = puzzle[i][j];
				if (puzzle[i][j] != 0)
					constrainedIndexes[i * size + j] = true;
			}
		}
		valueCount = new int[size+1];
		fixedConstrains = new FixedConstrains[values.length];
		for (int i = 0; i < fixedConstrains.length; i++) {
			fixedConstrains[i] = new FixedConstrains(size);
		}
		initFixedConstraints();
	}
	
	/**
	 * Inits state with random constraind value for index
	 */
	public void initState() {
		for (int i = 0; i < values.length; i++) {
			if (!constrainedIndexes[i]) {
				values[i] = fixedConstrains[i].getRandom();
			}
		}
	}

	private void initFixedConstraints() {

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
	
	/**
	 * value = 1- currentConflicts / maxConflicts
	 * maxConflict = size * (max conflicts for row/col/bulk, size -1 ) * 3
	 */
	public double getStateValue() {
		calculateConflicts();
		return 1.0 - 1.0 * sumConflicts
				/ (size*(size-3)*3);
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
	/**
	 * Calulates conflicts for the whole board and each square
	 */
	private void calculateConflicts() {
		sumConflicts = 0;
		//Resets value counter
		for (int i = 0; i < valueCount.length; i++) {
			valueCount[i] = 0;
		}
		for (int i = 0; i < values.length; i += size) {
			//Loops over the each element in row increasing the counter for the value
			for (int j = i; j < i + size; j++) {
				//Increases valuecounter
				valueCount[values[j]]++;
			}
			//Loops over each element setting their respective conflict given their value
			for (int j = i; j < i + size; j++) {
				conflicts[j] = Math.max(0, valueCount[values[j]] - 1);
			}
			//Sums up number of conflicts 
			sumConflicts += sumValueConflict();
		}

		for (int i = 0; i < size; i++) {
			for (int j = i; j < values.length; j += size) {
				valueCount[values[j]]++;
			}
			for (int j = i; j < values.length; j += size) {
				conflicts[j] += Math.max(0, valueCount[values[j]] - 1);
			}
			sumConflicts += sumValueConflict();
		}

		for (int i = 0; i < bulkSize; i++) {
			for (int j = 0; j < bulkSize; j++) {
				for (int rad = 0; rad < bulkSize; rad++) {
					int startIndex = i * bulkSize * size + rad * size + j
							* bulkSize;
					int end = startIndex + bulkSize;
					for (int index = startIndex; index < end; index++) {
						valueCount[values[index]]++;
					}
				}

				for (int rad = 0; rad < bulkSize; rad++) {
					int startIndex = i * bulkSize * size + rad * size + j
							* bulkSize;
					int end = startIndex + bulkSize;
					for (int index = startIndex; index < end; index++) {
						conflicts[index] += Math.max(0,
								valueCount[values[index]] - 1);
					}
				}
				sumConflicts += sumValueConflict();
			}
		}
	}

	private int sumValueConflict() {
		int sum = 0;
		for (int i = 1; i < valueCount.length; i++) {
			sum += Math.max(0, valueCount[i] - 1);
			valueCount[i] = 0;
		}

		return sum;
	}
	
	@Override
	public void swap() {
		int nodeId = getRandomWithConflict();
		values[nodeId] = getBestSwap(nodeId, fixedConstrains[nodeId]);		

		// Iterer rad, col, sub section
		// System.out.println(nodeId);
		int startRow = (nodeId / size);
		int startCol = nodeId % size;

		for (int i = startRow * size, to = startRow * size + size; i < to; i++) {
			if (i != nodeId && values[i] == values[nodeId]) {
				values[i] = fixedConstrains[i].getDifferentValue(values[nodeId]);
			}
		}

		for (int i = startCol; i < values.length; i += size) {
			if (i != nodeId && values[i] == values[nodeId]) {
				values[i] = fixedConstrains[i].getDifferentValue(values[nodeId]);
			}
		}

		int index;
		int startIndex = nodeId - (startRow % bulkSize) * size - startCol
				% bulkSize, bulkLength = startIndex + size * (bulkSize - 1)
				+ bulkSize;
		for (int k = startIndex; k < bulkLength; k += size) {
			for (int k2 = 0; k2 < bulkSize; k2++) {
				index = k + k2;
				if (index != nodeId && values[index] == values[nodeId]) {
					values[index] = fixedConstrains[index]
							.getDifferentValue(values[nodeId]);
				}
			}
		}
	}

	@SuppressWarnings("serial")
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

		public int getDifferentValue(int value) {

			int chosenValue = 0;
			do {
				chosenValue = getRandom();
			} while (chosenValue == value);

			return chosenValue;
		}
	}
}
