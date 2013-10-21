package problemSolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class GraphColorState implements State {
	ArrayList<Integer[]> neighbours;
	double[][] nodePositions;
	int[] values;
	int[] conflicts;
	int fileId = 1;

	public GraphColorState(int fileId) {
		this.fileId = fileId;
		this.neighbours = new ArrayList<Integer[]>();
	}

	public void initState() {
		String file = "graph-color-" + fileId + ".txt";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			String[] split = line.split(" ");

			int listLength = Integer.parseInt(split[0]);
			int edgesLength = Integer.parseInt(split[1]);
			values = new int[listLength];
			conflicts = new int[listLength];
			nodePositions = new double[listLength][2];

			int lineNumber = 1, currentColor = 0;
			ArrayList<ArrayList<Integer>> tmpNeightbours = new ArrayList<ArrayList<Integer>>();

			while ((line = br.readLine()) != null) {
				System.out.println(line + " parsingNodes:"
						+ (lineNumber <= listLength));
				split = line.split(" ");
				int index = Integer.parseInt(split[0]);

				if (lineNumber <= listLength) {
					values[index] = currentColor;
					nodePositions[index][0] = Double.parseDouble(split[1]);
					nodePositions[index][0] = Double.parseDouble(split[2]);
					tmpNeightbours.add(new ArrayList<Integer>());
					neighbours.add(null);
				} else {
					int index2 = Integer.parseInt(split[1]);
					tmpNeightbours.get(index).add(index2);
				}

				lineNumber++;
				if (currentColor == 3) {
					currentColor = 0;
				} else {
					currentColor++;
				}
			}

			for (int i = 0; i < tmpNeightbours.size(); i++) {
				neighbours
						.set(i, tmpNeightbours.get(i).toArray(new Integer[0]));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void printState() {
		// TODO
	}

	public int getConflicts() {
		// Reseter conflicts oversikt
		for (int i = 0; i < conflicts.length; i++) {
			conflicts[i] = 0;
		}

		int sumCon = 0;
		for (int i = 0; i < neighbours.size(); i++) {
			for (int j = 0; j < neighbours.get(i).length; j++) {
				if (values[i] == values[neighbours.get(i)[j]]) {
					sumCon++;
					conflicts[i]++;
				}
			}
		}
		return sumCon;
	}

	public double getStateValue() {
		return 1 - getConflicts() / values.length;
	}

	public void generateNeighbours(int n) {
		// TODO State manager fikser
	}

	public State getRandomNeighbour() {
		// TODO Auto-generated method stub
		return null;
	}

	private void calculateConflicts() {
		// TODO Auto-generated method stub

	}

	private void calculateStateValue() {
		// TODO Auto-generated method stub

	}

	public void alterConflicted() {
		// TODO Auto-generated method stub

	}

	public void swap() {
		int node =
		do{
			
		}while();

	}

	public State getBestNeighbour() {
		// TODO Auto-generated method stub
		return null;
	}

}