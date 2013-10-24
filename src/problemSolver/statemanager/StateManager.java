package problemSolver.statemanager;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class StateManager {
	//Linked list for the history of state changes
	final protected LinkedList<StateManager.State> history;
	//int array for state values and state conflicts
	public int[] values, conflicts;
	//array telling which indexes that can not be changed
	public boolean[] constrainedIndexes = null;
	//value constraints, only values
	private int minValue, maxValue;
	//Arraylist with values from minValue to maxValue, used to speed
	//Also fix for sudoku using diffrent swapValues when calling getBestSwap()  
	private final ArrayList<Integer> legalValues = new ArrayList<Integer>(), 
	valueSwap = new ArrayList<Integer>();
	
	public StateManager() {
		this.history = new LinkedList<StateManager.State>();
	}

	/**
	 * Set constrains for what values arrays value can be
	 * @param minValue
	 * @param maxValue
	 */
	public void setValueConstrains(int minValue, int maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
		if(maxValue-minValue < 10000){
			for(int i = 0; i < maxValue-minValue+1; i++){
				legalValues.add(minValue+i);
			}
		}
	}

	/**
	 * Generates random int between minValue and maxValue
	 * @return random int between min and max
	 */
	public int getRandomConstrained(int index) {
		return minValue
				+ (int) Math.round(Math.random() * (maxValue - minValue));
	}

	/**
	 * Initiates the value array with n size Value array is used to store value
	 * about the puzzel
	 * 
	 * @param n
	 */
	public void setValuesSize(int n) {
		this.values = new int[n];
		this.conflicts = new int[n];
	}

	/**
	 * Add and backup old value of a the change to the value array
	 * 
	 * @param index
	 * @param value
	 */
	public void addChange(int index, int value) {
		int oldValue = values[index];
		values[index] = value;
		history.addFirst(new State(index, oldValue));
	}

	/**
	 * Clear history of changes, making the values permanent If you know your
	 * not going back in history further than this point!
	 */
	public void makeStatePermanent() {
		history.clear();
	}

	/**
	 * Reverst last state change by polling last history change
	 */
	public void revertLast() {
		State s = history.poll();
		if (s != null)
			values[s.index] = s.value;

	}

	/**
	 * Revert the values array to the last permanent state made by poping the
	 * history and reverting change
	 */
	public void revertToBest() {

		State state;
		// Polls element from list and revert their state change to the values
		// list

		while ((state = history.poll()) != null) {
			values[state.index] = state.value;
		}
	}
	
	/**
	 * Checks if given index is constrained of value change
	 */
	public boolean isConstrainedIndex(int index){
		return constrainedIndexes != null && constrainedIndexes[index];
	}

	/**
	 * Generates generateNNeigbours neighbours by changing one value.
	 * Finds the best neighbour and applies the change
	 * @param generateNNeigbours number of neighbours to generate
	 * @param acceptedValue breaks the generate neighbours if score >= acceptedValue
	 * @return the new score of the state
	 */
	public double getBestNeighbour(int generateNNeigbours, double acceptedValue) {
		int bestValue = 0, bestIndex = 0, oldValue = 0;
		double bestScore = 0, newScore = 0;
		for (int i = 0; i < generateNNeigbours; i++) {
//			//Gets random  with conflict and index that is not constrained
			int index = getRandomWithConflict();
			//Gets random value 
			int value = getRandomConstrained(index);
			//Stores old value
			oldValue = values[index];
			//Sets new value
			values[index] = value;
			//Calculates new score
			newScore = getStateValue();
			// Revert change
			values[index] = oldValue;
			//If newScore is better than best store the change
			if (newScore > bestScore) {
				bestScore = newScore;
				bestIndex = index;
				bestValue = value;
				//Check if this is acceptedvalue
				if (newScore >= acceptedValue)
					break;
			}

		}
		
		//Apply the best change found
		addChange(bestIndex, bestValue);
		return bestScore;
	}
	
	/**
	 * Swaps random node with conflict 
	 * with the value that give the node min conflicts
	 */
	public void swap() {
		getStateValue();
		// Find node who is involved in a conflict
		int index = getRandomWithConflict();
		//Gets best swap for this index
		values[index] = getBestSwap(index,legalValues);
	}
	
	/**
	 * Checks which values of the swapvalues that give the node-index min conflicts
	 * @param index of node
	 * @param swapValues: values that can be test for node
	 * @return
	 */
	public int getBestSwap(int index, ArrayList<Integer> swapValues){
		valueSwap.clear();
		int oldValue = values[index], bestConflict = conflicts[index];
		valueSwap.add(oldValue);
		for (Integer i: swapValues) {
			//Skip if same as old value
			if (i == oldValue) continue;
			
			values[index] = i;
			getStateValue();
			int newConflict = conflicts[index];
			if (newConflict <= bestConflict) {
				if(newConflict < bestConflict) valueSwap.clear();
				bestConflict = newConflict;
				valueSwap.add(i);
			}
		}
		
		return valueSwap.get((int)(Math.random()*valueSwap.size()));
	}
	
	/**
	 * Returns a random not constrained index that has conflicts
	 * @return
	 */
	public int getRandomWithConflict(){
		int index;
		do {
			index = (int) (Math.random() * values.length);
		} while (conflicts[index] == 0 || isConstrainedIndex(index));	
		
		return index;
	}
	

	/**
	 * Initiates the state
	 */
	public abstract void initState();
	/**
	 * Returns a value between 0 or 1 for the for the current state
	 * 1 meaning that the problem is solved
	 * 0 meaning that the problem is in the worst state possible
	 * @return
	 */
	public abstract double getStateValue();

	/**
	 * Prints state info for puzzel
	 */
	public abstract void printState();

	
	/***
	 * A History state
	 */
	class State {
		public State(int index, int value) {
			this.index = index;
			this.value = value;
		}

		public int index;
		public int value;
	}

}