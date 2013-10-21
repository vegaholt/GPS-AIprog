package problemSolver;

import problemSolver.algorithms.MinConflict;
import problemSolver.algorithms.SimulatedAnnealing;
import problemSolver.statemanager.GraphColorStateManager;
import problemSolver.statemanager.KQueenStateManager;
import problemSolver.statemanager.SudokuStateManager;

public class Main {

	public static int[][] sudokuPuzzle1 = {{4, 3, 0, 7, 0, 0, 0, 9, 0},
									       {8, 0, 1, 0, 0, 0, 0, 0, 4},
									       {0, 0, 5, 2, 4, 8, 3, 0, 0},
									       {6, 0, 0, 5, 0, 0, 9, 7, 0},
									       {0, 8, 4, 0, 0, 0, 6, 3, 0},
									       {0, 9, 7, 0, 0, 3, 0, 0, 5},
									       {0, 0, 8, 6, 2, 9, 1, 0, 0},
									       {1, 0, 0, 0, 0, 0, 5, 0, 9},
									       {0, 2, 0, 0, 0, 1, 0, 6, 8}};
	
	public static int[][] sudokuPuzzle2 = {{0, 0, 0, 0, 4, 2, 0, 0, 8},
									       {0, 4, 0, 3, 0, 0, 2, 7, 0},
									       {0, 5, 6, 7, 0, 0, 0, 0, 3},
									       {3, 0, 0, 0, 0, 0, 0, 0, 1},
									       {0, 9, 1, 8, 0, 3, 5, 6, 0},
							   		       {6, 0, 0, 0, 0, 0, 0, 0, 9},
							   		       {5, 0, 0, 0, 0, 7, 1, 3, 0},
							   		       {0, 6, 7, 0, 0, 5, 0, 8, 0},
							   		       {8, 0, 0, 2, 6, 0, 0, 0, 0}};
		
	public static int[][] sudokuPuzzle3 = {{0, 0, 0, 0, 1, 0, 0, 9, 4},
										   {0, 6, 0, 7, 0, 0, 5, 0, 0},
										   {0, 2, 0, 0, 0, 0, 0, 3, 0},
										   {5, 9, 0, 0, 0, 2, 0, 0, 0},
										   {0, 0, 0, 6, 0, 7, 0, 0, 0},
										   {0, 0, 0, 8, 0, 0, 0, 7, 2},
										   {0, 8, 0, 0, 0, 0, 0, 5, 0},
										   {0, 0, 4, 0, 0, 3, 0, 8, 0},
										   {6, 1, 0, 0, 8, 0, 0, 0, 0}};

	public static void main(String[] args) {

		//KQueens SA
		SimulatedAnnealing sa = new SimulatedAnnealing(new KQueenStateManager(8), 10000, 0.01, 1.0, 30);
		sa.run();
		sa = new SimulatedAnnealing(new KQueenStateManager(25), 10000, 0.01, 1.0, 30);
		sa.run();
		sa = new SimulatedAnnealing(new KQueenStateManager(1000), 10000, 0.01, 1.0, 30);
		sa.run();
		
		//KQueens MC
		MinConflict mc = new MinConflict(new KQueenStateManager(8),10000, 1.0);
		mc.run();
		mc = new MinConflict(new KQueenStateManager(25),10000, 1.0);
		mc.run();
		mc = new MinConflict(new KQueenStateManager(1000),10000, 1.0);
		mc.run();
		
		//GraphColoring SA
		sa = new SimulatedAnnealing(new GraphColorStateManager(1), 10000, 0.01, 1.0, 30);
		sa.run();
		sa = new SimulatedAnnealing(new GraphColorStateManager(2), 10000, 0.01, 1.0, 30);
		sa.run();
		sa = new SimulatedAnnealing(new GraphColorStateManager(3), 10000, 0.01, 1.0, 30);
		sa.run();
		
		//GraphColoring MC
		mc = new MinConflict(new GraphColorStateManager(1), 10000, 1.0);
		mc.run();
		mc = new MinConflict(new GraphColorStateManager(2), 10000, 1.0);
		mc.run();
		mc = new MinConflict(new GraphColorStateManager(3), 10000, 1.0);
		mc.run();
		
		//Sudoku SA
		sa = new SimulatedAnnealing(new SudokuStateManager(sudokuPuzzle1), 10000, 0.01, 1.0, 30);
		sa.run();
		sa = new SimulatedAnnealing(new SudokuStateManager(sudokuPuzzle2), 10000, 0.01, 1.0, 30);
		sa.run();
		sa = new SimulatedAnnealing(new SudokuStateManager(sudokuPuzzle3), 10000, 0.01, 1.0, 30);
		sa.run();
		
		//Sudoku MC
		mc = new MinConflict(new SudokuStateManager(sudokuPuzzle1), 10000, 1.0);
		mc.run();
		mc = new MinConflict(new SudokuStateManager(sudokuPuzzle2), 10000, 1.0);
		mc.run();
		mc = new MinConflict(new SudokuStateManager(sudokuPuzzle3), 10000, 1.0);
		mc.run();
			
	}
}
