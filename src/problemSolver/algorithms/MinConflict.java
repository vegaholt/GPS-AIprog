package problemSolver.algorithms;

import problemSolver.statemanager.StateManager;

public class MinConflict extends SearchAlgorithm {

	public int iterationCount;
	public double acceptanceValue;

	public MinConflict(StateManager P, int iterationCount,
			double acceptanceValue) {
		super(P);
		this.iterationCount = iterationCount;
		this.acceptanceValue = acceptanceValue;
	}

	public void solve() {
		System.out.println("Min conflicts");
		// Counter
		int counter = 0;
		// Get conflicts
		double conflicts = P.getStateValue();

		while (conflicts < acceptanceValue && counter < iterationCount) {
			// Alter state
			P.swap();
			// Update conditions
			conflicts = P.getStateValue();
			counter++;
		}
		// Display iterations needed to solve the problem
		System.out.println("Solution found after " + counter + " iterations");
		
		//Adding stats to be printed
		addStats("Max iterations", iterationCount, "");
		addStats("Used iterations",counter, "");
		addStats("Wanted score",(float)acceptanceValue*100,"%");
		addStats("Got score",  (float)conflicts*100,"%");
		
	}
}
