
public class Main {
	
	public static void main(String[] args) {
		SA sa_kq = new SA(new KQueenState(1000), 1000, 0.3, 1.0, 20);
		sa_kq.run();
	}
}

/**
 * SA sa_gc = new SA(new GraphColorState(), 1000, 0.3); link to doc
 * MC mc_kq = new MC(new KQueenState(4)); 
 * MC mc_gc = new MC(new GraphColorState()); link to doc
 * **/