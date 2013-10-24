package problemSolver.algorithms;

import java.util.ArrayList;

import problemSolver.RunStats;
import problemSolver.statemanager.StateManager;

public abstract class SearchAlgorithm {
	public final StateManager stateManager;
	public final ArrayList<RunStats> stats;

	public SearchAlgorithm(StateManager stateManager) {
		this.stateManager = stateManager;
		this.stats = new ArrayList<RunStats>();
	}

	public ArrayList<RunStats> run() {
		stats.clear();
		// Start timer
		long time = System.currentTimeMillis();
		
		//Empties history of statemanager
		stateManager.makeStatePermanent();
		// Initiate state
		stateManager.initState();

		// Display initial state
		System.out.println("---Initial state---");
		stateManager.printState();

		// Run algorithm
		this.solve();

		// Display solution
		System.out.println("---Final solution---");
		stateManager.printState();
		
		time = System.currentTimeMillis() - time;
		this.addStats("Time used", time, "ms");
		// Display runtime
		System.out.println("Runtime: "+ time + " milliseconds");
		System.out.println();

		return this.stats;
	}
	/**
	 * Adds running stats to stats list
	 * @param label
	 * @param value
	 * @param extendsion
	 */
	protected void addStats(String label, float value, String extendsion){
		stats.add(new RunStats(label, value, extendsion));
	}
	
	/**
	 * Tries to solve the problem given by the statemanger
	 */
	protected abstract void solve();
}
