package problemSolver.algorithms;

import problemSolver.statemanager.StateManager;

public abstract class SearchAlgorithm {
	public final StateManager P;

	public SearchAlgorithm(StateManager P) {
		this.P = P;
	}

	public void run() {
		// Start timer
		long time = System.nanoTime();

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

		// Display runtime
		System.out.println("Runtime: "
				+ ((System.nanoTime() - time) / 1000000f) + " milliseconds");
		System.out.println();

	}

	protected abstract void solve();
}
