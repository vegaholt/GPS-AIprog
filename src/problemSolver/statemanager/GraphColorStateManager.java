package problemSolver.statemanager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GraphColorStateManager extends StateManager {
	//Neighbour list for nodes
	public ArrayList<Integer[]> neighbours;
	//Cordinates for nodes, only used for UI
	public float[][] nodePositions;

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
	//Constructor used by ui because of other file reading method
	public GraphColorStateManager(InputStream file) {
		parseFile(file);
	}
	/**
	 * Parses the txt file we where supplied
	 * @param file
	 */
	private void parseFile(InputStream file) {
		BufferedReader br = null;
		//Inits a 
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
			nodePositions = new float[listLength][2];
			
			int lineNumber = 1;
			ArrayList<ArrayList<Integer>> tmpNeightbours = new ArrayList<ArrayList<Integer>>();
			//Loops over all the lines in the txt file
			while ((line = br.readLine()) != null) {
				split = line.split(" ");
				//Gets node index
				int index = Integer.parseInt(split[0]);
				
				//Check if this is node description 
				//else its edge description
				if (lineNumber <= listLength) {
					//Gets position of this node
					nodePositions[index][0] = Float.parseFloat(split[1]);
					nodePositions[index][1] = Float.parseFloat(split[2]);
					//Creates list to add neighbours in
					tmpNeightbours.add(new ArrayList<Integer>());
					//Adds empty element so the index exists when converting the complete tmpNeighbours to an int array
					neighbours.add(null);
				} else {
					int index2 = Integer.parseInt(split[1]);
					//Addds neighbour relations between node-index and node-index2
					tmpNeightbours.get(index).add(index2);
				}

				lineNumber++;
			}
			
			//Converts tempNeigbours list to int array list
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
		//Initiates board by giving all nodes same color
		for (int i = 0; i < values.length; i++) {
			values[i] = getRandomConstrained(i);

		}
	}

	@Override
	public void printState() {
		System.out.println("Graph Coloring file: unknown");
		System.out.println("Number of conflicts:" + calculateConflicts());
		System.out.println("Graph score:" + getStateValue());
	}
	
	@Override
	public double getStateValue() {
		//Max conflicts = all nodes same color = number of nodes
		//1-numberofConflicts/maxConflicts
		return 1f - ((float) calculateConflicts() / values.length);
	}

	private int calculateConflicts() {
		// Reset conflicts counter
		for (int i = 0; i < conflicts.length; i++) {
			conflicts[i] = 0;
		}

		int sumCon = 0;
		//Loops over all the nodes
		for (int i = 0; i < neighbours.size(); i++) {
			
			//Loops over node-i neighbours, and checks if they have same color
			//If same color then increas connflicts for both nodes and the total conflicts
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

}
