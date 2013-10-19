package problemSolver.algorithms;

import problemSolver.LocalSearch;
import problemSolver.statemanager.GraphColorStateManager;
import problemSolver.statemanager.KQueenStateManager;
import problemSolver.statemanager.StateManager;

public class SimulatedAnnealing extends SearchAlgorithm {
	public double temperature;
	public double coolingRate;
	public double acceptanceValue;
	public int neighbourCount;

	public SimulatedAnnealing(StateManager state, double temperature, double coolingRate,
			double acceptanceValue, int neighbourCount) {
		super(state);
		
		this.temperature = temperature;
		this.coolingRate = coolingRate;
		this.acceptanceValue = acceptanceValue;
		this.neighbourCount = neighbourCount;
	}

	public void solve() {
		double best = P.getStateValue();
		
		float generateN = 0;
		float other = 0;
		int n= 1;
		
		while (temperature > 1) { // Return solution if acceptable
			if (best >= acceptanceValue)
				break;
			long t = System.nanoTime();
			double bestNeighbour = P.getBestNeighbour(neighbourCount, acceptanceValue);
			generateN = getAverage(n, t, generateN);
			
			
			t = System.nanoTime();
			
			double q = (bestNeighbour - best) /best;
			double p = Math.min(0, Math.exp(-q / temperature));
			double x = Math.random();
			
			if (x > p) {
				P.makeStatePermanent();
				best = bestNeighbour;
			} else {
				P.setRandomNeighbour();
				//P.revertStory();
			}
			other = getAverage(n, t, other);
			
			temperature *= 1-coolingRate;
			n++;
		}
		
		P.revertToBest();
		System.out.printf("Average generate-neighbours: %f, Average other:%f \n", generateN/1000000f, other/1000000f);
	}
	
	private float getAverage(int n, float time, float oldAverage){
		
		return (oldAverage*(n-1) + (System.nanoTime()-time))/n;
	}
	
	public static void main(String[] args){
		SimulatedAnnealing sa = new SimulatedAnnealing(new KQueenStateManager(500), 10000, 0.01, 1.0, 30);
		sa.run();
	}
}