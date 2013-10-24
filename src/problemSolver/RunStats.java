package problemSolver;

public class RunStats{
	/**
	 * Stats used when running a search algorithm
	 * @param label
	 * @param value
	 * @param extendsion
	 */
	public RunStats(String label, float value, String extendsion) {
		super();
		this.label = label;
		this.value = value;
		this.extendsion = extendsion;
	}
	public String label;
	public float value;
	public String extendsion;
}