package problemSolver.algorithms;

import java.util.HashMap;

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

	public HashMap<String,String> solve() {
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
		HashMap<String, String> stats = new HashMap<String, String>();
		stats.put("Max iterations", String.valueOf(iterationCount));
		stats.put("Used iterations", String.valueOf(counter));
		stats.put("Wanted score", String.valueOf(acceptanceValue*100)+"%");
		stats.put("Got score",  String.format("%.2f", conflicts*100)+"%");
		
		return stats;
	}
}
