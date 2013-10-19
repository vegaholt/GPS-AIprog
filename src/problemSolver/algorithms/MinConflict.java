package problemSolver.algorithms;

import problemSolver.statemanager.KQueenStateManager;
import problemSolver.statemanager.StateManager;

public class MinConflict extends SearchAlgorithm {

	public int iterationCount;
	public double acceptanceValue;

	public MinConflict(StateManager P, int iterationCount,double acceptanceValue) {
		super(P);
		this.iterationCount = iterationCount;
		this.acceptanceValue = acceptanceValue;
	}

	public void solve() {


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

		System.out.println("Solution found after " + counter
				+ " iterations");
	}
	public static void main(String[] args){
		MinConflict m = new MinConflict(new KQueenStateManager(1000), 10000, 1.0);
		m.run();
		
		SimulatedAnnealing s = new SimulatedAnnealing(new KQueenStateManager(1000), 10000, 0.98, 1.0 , 50);
		s.run();
	}
}
