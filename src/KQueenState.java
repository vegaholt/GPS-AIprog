
public class KQueenState implements State {
	
	public int k;
	public String state;
	public State[] neighbours;
	
	//Construct state
	public KQueenState(int k){
		this.k = k;
	}
	
	//Construct state
	public KQueenState(int k, String state){
		this.k = k;
		this.state = state;
	}
	
	//Initiate state with random placement of Q on each row
	public void initState() {
		state = "";
		
		//Builds blank row of k-1 length
		String row = "";
		for (int i = 0; i < k-1; i++) {
			 row += "0";
		}
		
		//Builds k rows and insert "1" at a random position
		for (int i = 0; i < k; i++) {
			int random = (int)(Math.random()*k);
			state += row.substring(0, random) + "1" + row.substring(random);
		}
	}
	
	//Displays the state
	public void printState(){
		//Displays the grid
		if (k <= 50) {
			
			for(int i = 0; i < k; i++){
				String printRow = state.substring((i*k), ((i+1)*k));
				for (int j = 0; j < k; j++) {
					System.out.print(printRow.charAt(j) + " ");
				}
				System.out.println();
			}
		}
		//Displays the value
		System.out.println("Value: " + getStateValue());
		System.out.println("Conflicts: " + getConflicts());
		System.out.println();
	}
	
	//Returns number of conflicts
	public int getConflicts() {
		int conflicts = 0;
		int rowCount = 0;
		
		//Detects conflicts on each column
		for(int i = 0; i < k; i++){
			rowCount = 0;
			for(int j = i; j < state.length(); j+=k){
				if(state.charAt(j)=='1') rowCount++;
			}
			conflicts += Math.max((rowCount-1), 0);
		}
		
		//Iterates through each left diagonal
		int row = k-2;
		int column = 0;
		
		while(row >=0 && column <= k-2){
			
			conflicts += getDiagonalConflictsLeft(row, column);
			
			if(column==0) row--;
			if(row==0) column++;
		}
		conflicts += getDiagonalConflictsLeft(0, 0);
		
		//Iterates through each right diagonal
		row = 0;
		column = 1;
		
		while(row<=k-2 && column<=k-1){
			
			conflicts += getDiagonalConflictsRight(row, column);
			
			if(row==0) column++;
			if(column==k-1) row++;
		}
		//If(k>2) bug
		conflicts += getDiagonalConflictsRight(0, k-1);
		
		return conflicts;
	}
	
	//Returns a value for the entire state
	public double getStateValue(){ //Flyttes til subclass av SA

		//Calculates a value for the state
		double value = 1-(double)getConflicts()/(2*k-3);
		
		//System.out.println("conflicts: " + conflicts + "/" + (2*k-3) + " value: " + value);
		
		return Math.max(0,value);
	}
	
	//Detects conflicts on left diagonal
	private int getDiagonalConflictsLeft(int row, int column){
		int count = 0;
		
		while(row<=(k-1)&&column<=(k-1)){
			if(state.charAt(k*row + column)=='1') count++;
			row++;
			column++;
		}
		return Math.max((count-1),0);
	}
	
	//Detects conflicts on right diagonal
	public int getDiagonalConflictsRight(int row, int column){
		int count = 0;

		while(row<=(k-1)&&column>=0){
			if(state.charAt(k*row + column)=='1') count++;
			row++;
			column--;
		}
		
		return Math.max((count-1),0);
	}

	//Generate neighbours
	public void generateNeighbours(int n) {
		
		//Initiate array
		neighbours = new State[n];
		
		//Number of neighbours
		for (int i = 0; i < n; i++) {
			
			//Collect random row
			int rowNumber = (int)(Math.random()*(k));
			String row = state.substring((rowNumber*k), ((rowNumber+1)*k));
			
			//Find queen index
			int indexOfQ = 0;
			for (int j = 0; j < row.length(); j++) {
				if (row.charAt(j)=='1') {
					indexOfQ = j;
					break;
				}
			}
			
			//Move Queen +1 to a random direction
			if(indexOfQ==(k-1)){
				//Must go left
				row = row.substring(1) + "0";
			}
			else if(indexOfQ==0){
				//Must go right
				row = "0" + row.substring(0, row.length()-1);
			}
			else{
				//Random left or right
				int random = (int)(Math.random()*2);
				if (random==0) {
					//Move left
					row = row.substring(0, indexOfQ-1) + row.substring(indexOfQ) + "0";
				}
				else {
					//Move right
					row = "0" + row.substring(0, indexOfQ+1) + row.substring(indexOfQ+1, row.length()-1);
					
				}
			}
			
			//Rebuild state
			String newState = state.substring(0, rowNumber*k) + row + state.substring((rowNumber+1)*k);
			//System.out.println(newState + " row: " + rowNumber);
			
			//Create neighbour
			neighbours[i] = new KQueenState(k, newState);	
		}
	}

	//Returns the best neighbour
	public State getBestNeighbour() {
		if(neighbours.length==0) return null;
		
		double bestValue = 0;
		int bestValueIndex = 0;
		
		for (int i = 0; i < neighbours.length; i++) {
			if(neighbours[i].getStateValue()>bestValue){
				bestValueIndex = i;
				bestValue = neighbours[i].getStateValue();
			}
		}
		return neighbours[bestValueIndex];
	}
	
	//Returns a random neighbour
	public State getRandomNeighbour() {
		if(neighbours.length==0) return null;
		int index = (int)(Math.random()*neighbours.length);
		return neighbours[index];
	}
}
