package problemSolver.statemanager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class GraphColorStateManager extends StateManager {
	ArrayList<Integer[]> neighbours;
	double[][] nodePositions;
	int[] conflicts;
	int fileId = 1;

	public GraphColorStateManager(int fileId) {
		super();
		this.fileId = fileId;
		this.neighbours = new ArrayList<Integer[]>();
	}

	@Override
	public void initState() {
		String file = "graph-color-" + fileId + ".txt";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			String[] split = line.split(" ");

			int listLength = Integer.parseInt(split[0]);
			setValuesSize(listLength);
			//Value constrains 0-3 because 4 colors
			setValueConstrains(0, 3);
			
			//int edgesLength = Integer.parseInt(split[1]);
			values = new int[listLength];
			conflicts = new int[listLength];
			nodePositions = new double[listLength][2]; 
			
			int lineNumber = 1, currentColor = 0;
			ArrayList<ArrayList<Integer>> tmpNeightbours = new ArrayList<ArrayList<Integer>>();
			
			while ((line = br.readLine()) != null) {
				//System.out.println(line+" parsingNodes:"+(lineNumber <= listLength));
				split = line.split(" ");
				int index = Integer.parseInt(split[0]);
				
				if(lineNumber <= listLength){
					values[index] = (int)(Math.random()*3);
					nodePositions[index][0] = Double.parseDouble(split[1]);
					nodePositions[index][0] = Double.parseDouble(split[2]);
					tmpNeightbours.add(new ArrayList<Integer>());
					neighbours.add(null);
				}else{
					int index2 = Integer.parseInt(split[1]);
					tmpNeightbours.get(index).add(index2);
				}
				
				lineNumber++;
				if(currentColor == 3){
					currentColor = 0;
				}else{
					currentColor ++;
				}
			}
			
			for (int i = 0; i < tmpNeightbours.size(); i++) {
				neighbours.set(i, tmpNeightbours.get(i).toArray(new Integer[0]));
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
	public void printState() {
		System.out.println("Graph Coloring file:"+fileId);
		System.out.println("Number of conflicts:"+getConflicts());
		System.out.println("Graph score:"+getStateValue());
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
		return 1f -((float)getConflicts() / values.length);
	}

	@Override
	public void swap() {
		
	}

}
