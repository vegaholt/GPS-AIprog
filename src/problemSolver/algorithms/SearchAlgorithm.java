package problemSolver.algorithms;

import java.util.ArrayList;
import java.util.HashMap;

import problemSolver.RunStats;
import problemSolver.statemanager.StateManager;

public abstract class SearchAlgorithm {
	public final StateManager P;
	public final ArrayList<RunStats> stats;

	public SearchAlgorithm(StateManager P) {
		this.P = P;
		this.stats = new ArrayList<RunStats>();
	}

	public ArrayList<RunStats> run() {
		stats.clear();
		// Start timer
		long time = System.currentTimeMillis();

		// Initiate state
		P.makeStatePermanent();
		P.initState();

		// Display initial state
		System.out.println("---Initial state---");
		P.printState();

		// Run algorithm
		this.solve();

		// Display solution
		System.out.println("---Final solution---");
		P.printState();
		
		time = System.currentTimeMillis() - time;
		this.addStats("Time used", time, "ms");
		// Display runtime
		System.out.println("Runtime: "+ time + " milliseconds");
		System.out.println();

		return this.stats;
	}
	
	protected void addStats(String label, float value, String extendsion){
		stats.add(new RunStats(label, value, extendsion));
	}
	
	protected abstract void solve();
}
