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
		//Sets the value constrains to be beween 1 and the widht of the puzzel
		setValueConstrains(1, size);
		//Sets the size of the board to be k^2
		setValuesSize(size * size);

		//Inits the statemanagers constrainedIndexes
		this.constrainedIndexes = new boolean[values.length];
		
		//Converts the 2d puzzel to 1d list
		//Setting values != 0 to be constrained
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
	
	/**
	 * Inits the fix value constrains for each index in the sudoku
	 * 
	 */
	private void initFixedConstraints() {

		boolean hasChanged = true;
		//While the fixed constrains where changed 
		while (hasChanged) {
			hasChanged = false;
			//Loops over the fixed constrains for each index
			for (int i = 0; i < fixedConstrains.length; i++) {
				int radIndex = (int) Math.floor(i / size);
				int colIndex = i % size;
				
				//Removes the fixed values for the row from the possible values this index can be
				for (int k = radIndex * size, to = radIndex * size + size; k < to; k++) {
					if (i != k && isConstrainedIndex(k))
						fixedConstrains[i].removeValue(values[k]);
				}
				
				//Same for column
				for (int j = colIndex; j < values.length; j += size) {
					if (i != j && isConstrainedIndex(j))
						fixedConstrains[i].removeValue(values[j]);
				}
				
				//Same for bulk
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

				//If this index only can be one value set it to this value 
				// and make it a fixed value
				//Also set that the fixed constrains have changed
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
		for (int rowIndex = 0; rowIndex < values.length; rowIndex += size) {
			//Loops over the each element in row increasing the counter for the value
			for (int i = rowIndex; i < rowIndex + size; i++) {
				//Increases valuecounter
				valueCount[values[i]]++;
			}
			//Loops over each element setting their respective conflict given their value
			for (int i = rowIndex; i < rowIndex + size; i++) {
				conflicts[i] = Math.max(0, valueCount[values[i]] - 1);
			}
			//Sums up number of conflicts 
			sumConflicts += sumValueConflict();
		}
		
		//Does same as above for columns
		for (int col = 0; col < size; col++) {
			
			for (int i = col; i < values.length; i += size) {
				valueCount[values[i]]++;
			}
			
			for (int i = col; i < values.length; i += size) {
				conflicts[i] += Math.max(0, valueCount[values[i]] - 1);
			}
			sumConflicts += sumValueConflict();
		}

		//Does same as above for bulk
		for (int bulkRow = 0; bulkRow < bulkSize; bulkRow++) {
			for (int bulkCol = 0; bulkCol < bulkSize; bulkCol++) {
				
				for (int row = 0; row < bulkSize; row++) {
					int startIndex = bulkRow * bulkSize * size + row * size + bulkCol
							* bulkSize;
					int end = startIndex + bulkSize;
					for (int index = startIndex; index < end; index++) {
						valueCount[values[index]]++;
					}
				}

				for (int row = 0; row < bulkSize; row++) {
					int startIndex = bulkRow * bulkSize * size + row * size + bulkCol
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
	
	/**S
	 * Sums up valueCount array and resets it
	 * @return
	 */
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
		//Gets random node with conflict
		int nodeId = getRandomWithConflict();
		
		//Sets the value to the best value
		values[nodeId] = getBestSwap(nodeId, fixedConstrains[nodeId]);		

		//Changes all the values == value of nodeId in same row, col and bulk
		//to a diffrent value given their fixed constrains
		
		int startRow = (nodeId / size);
		int startCol = nodeId % size;
		
		//Loops over row
		for (int i = startRow * size, to = startRow * size + size; i < to; i++) {
			//Change if values is same as as node got
			if (i != nodeId && values[i] == values[nodeId]) {
				values[i] = fixedConstrains[i].getDifferentValue(values[nodeId]);
			}
		}

		//Loops over column
		for (int i = startCol; i < values.length; i += size) {
			if (i != nodeId && values[i] == values[nodeId]) {
				values[i] = fixedConstrains[i].getDifferentValue(values[nodeId]);
			}
		}

		//Loops over bulk
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

		/**
		 * Removes value from list
		 * @param value
		 */
		public void removeValue(int value) {
			for (int i = 0; i < this.size(); i++) {
				if (this.get(i) == value) {
					this.remove(i);
					return;
				}
			}
		}
		/**
		 * Gets random from the list
		 * @return
		 */
		public int getRandom() {
			return this.get((int) (Math.random() * this.size()));
		}
		/***
		 * Returns a int not equal to the value given and which exists in this list
		 * @param value
		 * @return
		 */
		public int getDifferentValue(int value) {

			int chosenValue = 0;
			do {
				chosenValue = getRandom();
			} while (chosenValue == value);

			return chosenValue;
		}
	}
}
