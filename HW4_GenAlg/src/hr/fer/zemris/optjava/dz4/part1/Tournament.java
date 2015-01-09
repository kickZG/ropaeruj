package hr.fer.zemris.optjava.dz4.part1;

public class Tournament implements ISelection {

	private int n;
	
	/**
	 * Tournament:n
	 * @param n
	 */
	
	public Tournament(int n) {
		this.n = n;
	}
	
	/**
	 * Tournament selection method (variant with possible repeating solutions).
	 * @param fitness Fitness array for every solution in population.
	 * @return index Chosen solution
	 */
	
	@Override
	public int choose(double[] fitness) {
		/*
		System.out.println("JSAM OVDJE");
		for(int i = 0; i < fitness.length; i++) {
			System.out.println("FITNESS: " + fitness[i]);
		}
		*/
		
		//first, randomly choose n solutions
		int rand = (int)(Math.random() * fitness.length);
		int index = rand;
		double bestFitness = fitness[rand];
		for(int i = 1; i < this.n; i++) {
			rand = (int)Math.random() * fitness.length;
			if(fitness[rand] < bestFitness) {
				index = rand;
				bestFitness = fitness[rand];
			}
		}
		
		return index;
	}

}
