package hr.fer.zemris.optjava.dz5.part1.GeneticAlgorithm;

public class MutateOperator {

	private double probability;
	
	public MutateOperator(double probability) {
		this.probability = probability;
	}
	
	public boolean[] mutate(boolean[] child) {
		for(int i = 0; i < child.length; i++) {
			if(Math.random() < probability) {
				child[i] = !child[i];
			}
		}
		return child;
	}
	
}
