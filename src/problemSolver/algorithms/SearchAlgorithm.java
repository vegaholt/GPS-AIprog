package problemSolver.algorithms;

import java.util.HashMap;

import problemSolver.statemanager.StateManager;

public abstract class SearchAlgorithm {
	public final StateManager P;

	public SearchAlgorithm(StateManager P) {
		this.P = P;
	}

	public HashMap<String, String> run() {
		// Start timer
		long time = System.currentTimeMillis();

		// Initiate state
		P.makeStatePermanent();
		P.initState();

		// Display initial state
		System.out.println("---Initial state---");
		P.printState();

		// Run algorithm
		HashMap<String, String> stats = this.solve();

		// Display solution
		System.out.println("---Final solution---");
		P.printState();
		
		time = System.currentTimeMillis() - time;
		stats.put("Time used", String.valueOf(time)+"ms");
		// Display runtime
		System.out.println("Runtime: "+ time + " milliseconds");
		System.out.println();

		return stats;
	}

	protected abstract HashMap<String, String> solve();
}
