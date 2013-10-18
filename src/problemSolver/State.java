package problemSolver;
public interface State {

	public void initState();

	public void printState();

	public int getConflicts();

	public double getStateValue();

	public void generateNeighbours(int n);

	public State getBestNeighbour();

	public State getRandomNeighbour();
}