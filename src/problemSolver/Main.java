package problemSolver;

import problemSolver.algorithms.MinConflict;
import problemSolver.algorithms.SimulatedAnnealing;
import problemSolver.statemanager.GraphColorStateManager;
import problemSolver.statemanager.KQueenStateManager;
import problemSolver.statemanager.SudokuStateManager;

public class Main {

	
	public static int[][] sudokuPuzzle1 = {{0, 0, 0, 0, 4, 2, 0, 0, 8},
									       {0, 4, 0, 3, 0, 0, 2, 7, 0},
									       {0, 5, 6, 7, 0, 0, 0, 0, 3},
									       {3, 0, 0, 0, 0, 0, 0, 0, 1},
									       {0, 9, 1, 8, 0, 3, 5, 6, 0},
							   		       {6, 0, 0, 0, 0, 0, 0, 0, 9},
							   		       {5, 0, 0, 0, 0, 7, 1, 3, 0},
							   		       {0, 6, 7, 0, 0, 5, 0, 8, 0},
							   		       {8, 0, 0, 2, 6, 0, 0, 0, 0}};
		
	public static int[][] sudokuPuzzle5 = {{4, 12, 0, 0, 0, 0, 5, 10, 0, 0, 0, 16, 0, 0, 8, 0},
											{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 13, 0},
											{7, 0, 15, 0, 16, 3, 0, 9, 13, 5, 0, 0, 2, 0, 11, 0},
											{3, 0, 13, 10, 0, 0, 0, 11, 0, 0, 0, 6, 9, 0, 12, 0},
											{2, 0, 9, 4, 0, 0, 16, 0, 0, 0, 8, 0, 12, 0, 6, 0},
											{0, 0, 0, 12, 2, 0, 10, 8, 0, 0, 4, 0, 0, 0, 0, 16},
											{0, 0, 10, 0, 12, 0, 6, 7, 2, 0, 0, 15, 0, 8, 0, 0},
											{0, 15, 6, 8, 0, 0, 0, 14, 0, 0, 5, 3, 0, 0, 2, 0},
											{0, 5, 0, 0, 10, 12, 0, 0, 9, 0, 0, 0, 16, 4, 1, 0},
											{0, 0, 11, 0, 14, 0, 0, 5, 4, 12, 0, 10, 0, 9, 0, 0},
											{12, 0, 0, 0, 0, 7, 0, 0, 5, 3, 0, 11, 14, 0, 0, 0},
											{0, 10, 0, 9, 0, 16, 0, 0, 0, 15, 0, 0, 8, 12, 0, 7},
											{0, 1, 0, 11, 13, 0, 0, 0, 3, 0, 0, 0, 5, 14, 0, 9},
											{0, 14, 0, 13, 0, 0, 9, 4, 16, 0, 15, 1, 0, 2, 0, 6},
											{0, 9, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
											{0, 7, 0, 0, 11, 0, 0, 0, 6, 13, 0, 0, 0, 0, 4, 8}};
	

	public static int[][] sudokuPuzzle3 ={{0, 0, 0, 10, 15, 0, 0, 0, 0, 8, 9, 0, 0, 7, 0, 0},
										  {12, 5, 9, 0, 8, 0, 6, 0, 0, 4, 0, 10, 3, 0, 15, 0},
					 					  {6, 2, 0, 8, 0, 13, 0, 0, 5, 0, 0, 0, 14, 0, 10, 9},
					   					  {0, 7, 0, 0, 0, 12, 0, 0, 11, 0, 15, 14, 0, 4, 0, 6},
										  {0, 0, 4, 6, 0, 0, 13, 0, 0, 1, 10, 0, 16, 0, 0, 0},
										  {0, 10, 11, 0, 0, 0, 4, 0, 12, 0, 13, 0, 0, 0, 2, 0},
										  {0, 13, 0, 12, 11, 15, 3, 0, 0, 14, 0, 0, 10, 0, 0, 0},
										  {7, 0, 5, 0, 0, 0, 10, 0, 15, 0, 2, 0, 6, 0, 8, 0},
										  {0, 8, 0, 11, 0, 4, 0, 6, 0, 9, 0, 0, 0, 16, 0, 2},
									 	  {0, 0, 0, 4, 0, 0, 15, 0, 0, 6, 14, 13, 7, 0, 3, 0},
										  {0, 9, 0, 0, 0, 14, 0, 13, 0, 11, 0, 0, 0, 15, 4, 0},
										  {0, 0, 0, 7, 0, 10, 8, 0, 0, 12, 0, 0, 9, 14, 0, 0},
										  {9, 0, 14, 0, 13, 8, 0, 4, 0, 0, 11, 0, 0, 0, 1, 0},
										  {11, 6, 0, 2, 0, 0, 0, 9, 0, 0, 12, 0, 4, 0, 14, 8},
										  {0, 1, 0, 15, 6, 0, 11, 0, 0, 2, 0, 9, 0, 10, 12, 7},
										  {0, 0, 3, 0, 0, 1, 2, 0, 0, 0, 0, 5, 11, 0, 0, 0}};
	
	
	public static int[][] sudokuPuzzle2 = {{0, 0, 0, 0, 1, 0, 0, 9, 4},
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
		sa = new SimulatedAnnealing(new SudokuStateManager(sudokuPuzzle1), 10000, 0.01, 1.0, 100);
		sa.run();
		sa = new SimulatedAnnealing(new SudokuStateManager(sudokuPuzzle2), 10000, 0.01, 1.0, 100);
		sa.run();
		sa = new SimulatedAnnealing(new SudokuStateManager(sudokuPuzzle3), 10000, 0.01, 1.0, 100);
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
