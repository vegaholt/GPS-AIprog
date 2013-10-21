package problemSolver;

import problemSolver.algorithms.MinConflict;
import problemSolver.algorithms.SimulatedAnnealing;
import problemSolver.statemanager.GraphColorStateManager;
import problemSolver.statemanager.KQueenStateManager;

public class Main {

	public static void main(String[] args) {
		
		/*
		//KQueens MC
		MinConflict mc = new MinConflict(new KQueenStateManager(8),10000, 1.0);
		mc.run();
		mc = new MinConflict(new KQueenStateManager(25),10000, 1.0);
		mc.run();
		mc = new MinConflict(new KQueenStateManager(1000),10000, 1.0);
		mc.run();
		
		//KQueens SA
		SimulatedAnnealing sa = new SimulatedAnnealing(new KQueenStateManager(8), 10000, 0.01, 1.0, 30);
		sa.run();
		
		sa = new SimulatedAnnealing(new KQueenStateManager(25), 10000, 0.01, 1.0, 30);
		sa.run();
		
		sa = new SimulatedAnnealing(new KQueenStateManager(1000), 10000, 0.01, 1.0, 30);
		sa.run();
		
		
		//Graphcoloring
		sa = new SimulatedAnnealing(new GraphColorStateManager(1), 10000, 0.01, 1.0, 30);
		sa.run();
		
		sa = new SimulatedAnnealing(new GraphColorStateManager(2), 10000, 0.01, 1.0, 30);
		sa.run();
		
		sa = new SimulatedAnnealing(new GraphColorStateManager(3), 10000, 0.01, 1.0, 30);
		sa.run();
		*/
		
		MinConflict mc = new MinConflict(new GraphColorStateManager(2), 10000, 1.0);
		mc.run();
		
//		SA sa_kq_8 = new SA(new KQueenState2(8), 1000, 0.3, 1.0, 20);
//		sa_kq_8.run();
//		SA sa_kq_25 = new SA(new KQueenState2(25), 1000, 0.3, 1.0, 20);
//		sa_kq_25.run();
//		SA sa_kq_1000 = new SA(new KQueenState2(1000), 1000, 0.3, 1.0, 20);
//		sa_kq_1000.run();
//				
//		MC mc_kq_8 = new MC(new KQueenState2(8), 10000);
//		mc_kq_8.run();
//		MC mc_kq_25 = new MC(new KQueenState2(25), 10000);
//		mc_kq_25.run();
//		MC mc_kq_1000 = new MC(new KQueenState2(1000), 10000);
//		mc_kq_1000.run();
//
////		sa_kq = new SA(new CopyOfKQueenState(100), 1000, 0.3, 1.0, 50);
////		sa_kq.run();
//		
//		SimulatedAnnealing newSa = new SimulatedAnnealing(new KQueenStateManager(100), 10000, 0.98, 1.0, 100);
//		newSa.run();
	}
}

