package problemSolver.algorithms;


import problemSolver.statemanager.StateManager;

public class SimulatedAnnealing extends SearchAlgorithm {
	public double temperature;
	public double coolingRate;
	public double acceptanceValue;
	public int neighbourCount;

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
		double best = P.getStateValue(), current = best;
		double temperature = this.temperature;
		float generateN = 0;
		float other = 0;
		int n = 1;
		if(best >= acceptanceValue) return;
		while (temperature > 1) { // Return solution if acceptable

			long t = System.nanoTime();
			double bestNeighbour = P.getBestNeighbour(neighbourCount,
					acceptanceValue);
			generateN = getAverage(n, t, generateN);

			t = System.nanoTime();

			if (bestNeighbour > best) {
				best = bestNeighbour;
				P.makeStatePermanent();
				if (best >= acceptanceValue)
					break;
			}

			if (acceptanceProbability(current, bestNeighbour, temperature) > Math
					.random()) {
				current = bestNeighbour;
			} else {
				P.revertLast();
			}

			other = getAverage(n, t, other);

			temperature *= 1 - coolingRate;
			n++;
		}

		P.revertToBest();
		System.out
				.printf("Average generate-neighbours: %f, Average other:%f iterations:%d\n",
						generateN / 1000000f, other / 1000000f, n);
		
		//Adding stats to be printed
		addStats("Start temp", (float)this.temperature, "*C");
		addStats("Reached temp",(float)temperature , "*C");
		addStats("Wanted score", (float)acceptanceValue*100 , "%");
		addStats("Got Score",(float)best*100 , "%");
		addStats("Iterations", n, "n");
		
	}

	private double acceptanceProbability(double current, double newScore,
			double temp) {
		// If better accept it
		if (newScore > current) {
			return 1.0;
		}
		// If the new solution is worse, calculate an acceptance probability
		return Math.exp((newScore - current) / temp);
	}

	private float getAverage(int n, float time, float oldAverage) {
		return (oldAverage * (n - 1) + (System.nanoTime() - time)) / n;
	}

}