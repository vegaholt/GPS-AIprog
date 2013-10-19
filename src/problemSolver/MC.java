package problemSolver;

public class MC extends LocalSearch {

	public State P;
	public int iterationCount;

	public MC(State P, int iterationCount) {
		this.P = P;
		this.iterationCount = iterationCount;
	}

	public void run() {

		// Start timer
		long time = System.nanoTime();

		// Counter
		int counter = 0;

		// Initiate state
		P.initState();

		// Print init solution
		System.out.println("Initial solution");
		P.printState();
		
		
		// Get conflicts
		int conflicts = P.getConflicts();

		while (conflicts > 0 && counter < iterationCount) {

			// Alter state
			P.swap();

			// Update conditions
			conflicts = P.getConflicts();
			counter++;
		}

		// Print final solution
		System.out.println("Final solution");
		P.printState();

		// Display iterations
		if (counter < iterationCount) {
			System.out.println("Solution found after " + counter
					+ " iterations");
		}

		// Display runtime
		System.out.println("Runtime: " + (System.nanoTime() - time)
				+ " nanoseconds");	
		
	}
}
