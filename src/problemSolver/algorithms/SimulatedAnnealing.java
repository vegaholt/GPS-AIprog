package problemSolver.algorithms;

import problemSolver.statemanager.StateManager;

public class SimulatedAnnealing extends SearchAlgorithm {
	//Initial temp
	public final double temperature;
	public final double coolingRate;
	public final double acceptanceValue;
	public final int neighbourCount;

	public SimulatedAnnealing(StateManager state, double temperature,
			double coolingRate, double acceptanceValue, int neighbourCount) {
		super(state);

		this.temperature = temperature;
		this.coolingRate = coolingRate;
		this.acceptanceValue = acceptanceValue;
		this.neighbourCount = neighbourCount;
	}

	public void solve() {
		System.out.println("Simulated anealing");
		
		//Sets the current and best score to the current board score
		double best = stateManager.getStateValue(), current = best;
		//Sets temperature to initial temp
		double temperature = this.temperature;
		int n = 0;
		//Returns if the board is already has accepted score
		if (best >= acceptanceValue)
			return;
		
		//Iterates to problem has accepted score or temp < 1
		while (temperature > 1) {
			//Ask statemanager to generate neighbourCount neighbours and return the score of the best
			double bestNeighbour = stateManager.getBestNeighbour(neighbourCount,
					acceptanceValue);

			//CHeck if this is the best score.
			if (bestNeighbour > best) {
				best = bestNeighbour;
				//Tells statemanager to remove all previous history and make this state final for now.
				stateManager.makeStatePermanent();
				//If best is accepted Score break;
				if (best >= acceptanceValue)
					break;
			}

			//Checks if the new score is acceptable compared to the previous accepted score score
			//If so set previous accepted score to new score
			//Else revert board to the previous state
			if (Math.exp((bestNeighbour - current) / temperature) > Math
					.random()) {
				current = bestNeighbour;
			} else {
				stateManager.revertLast();
			}
			
			//Cool down
			temperature *= 1 - coolingRate;
			n++;
		}
		//Revert the board to the finale state made by calling makeStatePermanent()
		stateManager.revertToBest();

		// Adding stats to be printed
		addStats("Start temp", (float) this.temperature, "*C");
		addStats("Reached temp", (float) temperature, "*C");
		addStats("Wanted score", (float) acceptanceValue * 100, "%");
		addStats("Got Score", (float) best * 100, "%");
		addStats("Iterations", n, "n");
	}

}