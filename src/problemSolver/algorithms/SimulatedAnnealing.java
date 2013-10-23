package problemSolver.algorithms;


import java.util.HashMap;

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

	public HashMap<String, String> solve() {
		System.out.println("Simulated anealing");
		double best = P.getStateValue(), current = best;
		double temperature = this.temperature;
		float generateN = 0;
		float other = 0;
		int n = 1;
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
		HashMap<String, String> stats = new HashMap<String, String>();
		stats.put("Start temp", String.valueOf(this.temperature)+"*C");
		stats.put("Reached temp", String.format("%.1f", temperature)+"*C");
		stats.put("Iterations", String.valueOf(n));
		stats.put("Wanted score", String.valueOf(acceptanceValue*100)+"%");
		stats.put("Got score",  String.format("%.2f", best*100)+"%");
		return stats;
		
		
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