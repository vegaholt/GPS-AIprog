package problemSolver.statemanager;

import java.util.LinkedList;

public abstract class StateManager {
	final protected LinkedList<StateManager.State> history;
	public int[] values;
	private int minValue, maxValue;

	public StateManager() {
		this.history = new LinkedList<StateManager.State>();
	}

	/**
	 * Set constrains for what values arrays value can be
	 * 
	 * @param minValue
	 * @param maxValue
	 */
	public void setValueConstrains(int minValue, int maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	/**
	 * Generates random int between minValue and maxValue
	 * 
	 * @return
	 */
	public int getRandomConstrained() {
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

	/***
	 * A History state
	 * 
	 * @author Nicolay
	 * 
	 */
	class State {
		public State(int index, int value) {
			this.index = index;
			this.value = value;
		}

		public int index;
		public int value;
	}

	// Get a random neighbour
	public void setRandomNeighbour() {
		addChange((int) (Math.random() * values.length), getRandomConstrained());
	}

	// Get the best neighbour
	public double getBestNeighbour(int generateNNeigbours, double acceptedValue) {
		int bestValue = 0, bestIndex = 0, oldValue = 0;
		double bestScore = 0, newScore = 0;
		for (int i = 0; i < generateNNeigbours; i++) {
			int index = (int) (Math.random() * values.length);
			int value = getRandomConstrained();
			oldValue = values[index];
			values[index] = value;
			newScore = getStateValue();
			// Revert change
			values[index] = oldValue;
			if (newScore > bestScore) {
				bestScore = newScore;
				bestIndex = index;
				bestValue = value;
				if (newScore >= acceptedValue)
					break;
			}

		}

		addChange(bestIndex, bestValue);
		return bestScore;
	}

	public abstract void swap();

	public abstract double getStateValue();

	public abstract void printState();

	public abstract void initState();

}