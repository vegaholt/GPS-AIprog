package problemSolver.statemanager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.FileHandler;

public class GraphColorStateManager extends StateManager {
	public ArrayList<Integer[]> neighbours;
	public float[][] nodePositions;
	public int[] conflicts;

	public GraphColorStateManager(int fileId) {
		super();

		try {
			FileInputStream file = new FileInputStream("graph-color-" + fileId
					+ ".txt");
			parseFile(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public GraphColorStateManager(InputStream file) {
		parseFile(file);
	}

	private void parseFile(InputStream file) {
		BufferedReader br = null;
		this.neighbours = new ArrayList<Integer[]>();
		try {
			br = new BufferedReader(new InputStreamReader(file));
			String line = br.readLine();
			String[] split = line.split(" ");

			int listLength = Integer.parseInt(split[0]);
			setValuesSize(listLength);
			// Value constrains 0-3 because 4 colors
			setValueConstrains(0, 3);

			// int edgesLength = Integer.parseInt(split[1]);
			values = new int[listLength];
			conflicts = new int[listLength];
			nodePositions = new float[listLength][2];

			int lineNumber = 1, currentColor = 0;
			ArrayList<ArrayList<Integer>> tmpNeightbours = new ArrayList<ArrayList<Integer>>();

			while ((line = br.readLine()) != null) {
				// System.out.println(line+" parsingNodes:"+(lineNumber <=
				// listLength));
				split = line.split(" ");
				int index = Integer.parseInt(split[0]);

				if (lineNumber <= listLength) {
					nodePositions[index][0] = Float.parseFloat(split[1]);
					nodePositions[index][1] = Float.parseFloat(split[2]);
					tmpNeightbours.add(new ArrayList<Integer>());
					neighbours.add(null);
				} else {
					int index2 = Integer.parseInt(split[1]);
					tmpNeightbours.get(index).add(index2);
				}

				lineNumber++;
				// if (currentColor == 3) {
				// currentColor = 0;
				// } else {
				// currentColor++;
				// }
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

	@Override
	public void initState() {
		for (int i = 0; i < values.length; i++) {
			values[i] = getRandomConstrained();

		}
	}

	@Override
	public void printState() {
		System.out.println("Graph Coloring file: unknown");
		System.out.println("Number of conflicts:" + getConflicts());
		System.out.println("Graph score:" + getStateValue());
	}

	private int getConflicts() {
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
					conflicts[neighbours.get(i)[j]]++;
				}
			}
		}
		return sumCon;
	}

	@Override
	public double getStateValue() {
		return 1f - ((float) getConflicts() / values.length);
	}

	@Override
	public void swap() {

		// Find node who is involved in a conflict
		int nodeId = 0;
		do {
			nodeId = (int) (Math.random() * values.length);
		} while (conflicts[nodeId] == 0);

		int oldColor = values[nodeId], bestConflict = conflicts[nodeId], bestColor = oldColor;
		for (int i = 0; i < 4; i++) {
			if (i == oldColor)
				continue;
			values[nodeId] = i;
			getConflicts();
			int newConflict = conflicts[nodeId];
			if (newConflict < bestConflict) {
				bestConflict = newConflict;
				bestColor = i;
			}
		}

		values[nodeId] = bestColor;

	}

}
