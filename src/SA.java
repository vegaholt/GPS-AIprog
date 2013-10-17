
public class SA extends LocalSearch{

	public double temperature;
	public double coolingRate;
	public double acceptanceValue;
	public int neighbourCount;
	public State P;
	
	public SA(State state, double temperature, double coolingRate, double acceptanceValue, int neighbourCount) {
		this.temperature = temperature;
		this.coolingRate = coolingRate;
		this.acceptanceValue = acceptanceValue;
		this.neighbourCount = neighbourCount;
		this.P = state;
	}
	
	public void run() {

		P.initState();
		System.out.println("Initial solution");
		P.printState();
		
		double best = P.getStateValue();
		
		while (temperature > 1) {
			//Return solution if acceptable
			if(best>acceptanceValue) break;
			
			//Generate neighbours
			P.generateNeighbours(neighbourCount);
			
			//Collect best neighbour
			State bestNeighbour = P.getBestNeighbour();
			
			double q = (bestNeighbour.getStateValue()-best)/best;
			double p = Math.min(0, Math.exp(-q/temperature));
			double x = Math.random();
			
			if (x > p) {
				P = bestNeighbour;
			}else {
				P = P.getRandomNeighbour();
			}
			
			temperature -= coolingRate;
		}
		
		//Display solution
		System.out.println("Solution");
		P.printState();
	}
	
	//Forskjell for puzzles
	//Datarepresentasjon
	//Objective function
	//Nabogenererer, endre state
	
	//Skriv ut l¿sning
	
}