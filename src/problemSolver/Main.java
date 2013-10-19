package problemSolver;
public class Main {

	public static void main(String[] args) {
		SA sa_kq = new SA(new KQueenState(100), 1000, 0.3, 1.0, 50);
		sa_kq.run();
		
		sa_kq = new SA(new CopyOfKQueenState(100), 1000, 0.3, 1.0, 50);
		sa_kq.run();
	}
}

