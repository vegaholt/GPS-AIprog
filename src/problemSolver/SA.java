package problemSolver;

public class SA extends LocalSearch {
	public double temperature;
	public double coolingRate;
	public double acceptanceValue;
	public int neighbourCount;
	public State P;

	public SA(State state, double temperature, double coolingRate,
			double acceptanceValue, int neighbourCount) {
		this.temperature = temperature;
		this.coolingRate = coolingRate;
		this.acceptanceValue = acceptanceValue;
		this.neighbourCount = neighbourCount;
		this.P = state;
	}

	public void run() {
		//Start timer
		long time = System.nanoTime();
		
		//Initiate state
		P.initState();
		
		//Print init solution
		System.out.println("Initial solution");
		P.printState();

		double best = P.getStateValue();

		while (temperature > 1) { // Return solution if acceptable
			if (best > acceptanceValue)
				break;

			// Generate neighbours 
			P.generateNeighbours(neighbourCount);

			// Collect best neighbour 
			State bestNeighbour = P.getBestNeighbour();

			double q = (bestNeighbour.getStateValue() - best) / best;
			double p = Math.min(0, Math.exp(-q / temperature));
			double x = Math.random();

			if (x > p) {
				P = bestNeighbour;
			} else {
				P = P.getRandomNeighbour();
			}
			temperature -= coolingRate;

		}
		// Display solution
		System.out.println("Final solution");
		P.printState();
		
		//Display runtime
		System.out.println("Runtime: " + ((System.nanoTime()-time)/1000000f) + " milliseconds");
	}
}