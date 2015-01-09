package hr.fer.zemris.optjava.dz3;

public class SingleObjectiveSolution{

	private double fitness;
	private double value;
	
	public SingleObjectiveSolution() {
		
	}
	
	public int compareTo(SingleObjectiveSolution sos) {
		if(sos.getValue() > this.value) {
			return 1;
		} else if(sos.getValue() < this.value) {
			return -1;
		} else {
			return 0;
		}
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public double getFitness() {
		return this.fitness;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public double getValue() {
		return this.value;
	}
	
}
