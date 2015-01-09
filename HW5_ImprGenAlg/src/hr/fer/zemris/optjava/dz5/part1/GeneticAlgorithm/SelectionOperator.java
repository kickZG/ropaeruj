package hr.fer.zemris.optjava.dz5.part1.GeneticAlgorithm;

/**
 * This class implements k-tournament selection, but also provides
 * a method for random choosing the parents.
 * 
 * @author kristijan
 *
 */

public class SelectionOperator {

	private int k;
	
	/**
	 * The constructor takes parameter k which indicates the type
	 * of the tournament selection.
	 * 
	 * @param k
	 */
	
	public SelectionOperator(int k) {
		this.k = k;
	}
	
	/**
	 * Tournament selection method (without possible repeating solutions).
	 * @param population Population boolean array.
	 * @return parents Parents chosen from k-tournament
	 */
	
	public boolean[][] chooseBothTournament(boolean[][] population) {
		boolean[][] parents = new boolean[2][population[0].length];
		
		for(int i = 0; i < 2; i++) {
			int[] randomIndex = new int[this.k];
			int j = 0;
			do {
				boolean taken = false;
				int index = (int)Math.floor(Math.random() * population.length);
				for(int z = 0; z < randomIndex.length; z++) {
					//if that index is already taken
					if(index == randomIndex[z]) {
						taken = true;
						break;
					}
				}
				if(!taken) j++;
			} while(j < k);
			
			boolean[][] candidates = new boolean[this.k][population[0].length];
			for(int m = 0; m < this.k; m++) {
				candidates[m] = population[randomIndex[m]];
			}
			double[] candFitness = getSolutionsFitness(candidates);
			int maxFitnessIndex = 0;
			for(int m = 0; m < candFitness.length; m++) {
				if(candFitness[m] > maxFitnessIndex) {
					maxFitnessIndex = randomIndex[m];
				}
			}
			parents[i] = population[maxFitnessIndex];
		}
		return parents;
	}
	
	/**
	 * This method chooses first parent doing 
	 * tournament selection and the second one random.
	 * 
	 * @param population
	 * @return
	 */
	
	public boolean[][] chooseOneRandom(boolean[][] population) {
		boolean[][] parents = new boolean[2][population[0].length];
		
		int[] randomIndex = new int[this.k];
		int j = 0;
		do {
			boolean taken = false;
			int index = (int)Math.floor(Math.random() * population.length);
			for(int z = 0; z < k; z++) {
				//if that index is already taken
				if(index == randomIndex[z]) {
					taken = true;
					break;
				}
			}
			if(!taken) j++;
		} while(j < k);
		
		boolean[][] candidates = new boolean[this.k][population.length];
		for(int m = 0; m < this.k; m++) {
			candidates[m] = population[randomIndex[m]];
		}
		double[] candFitness = getSolutionsFitness(candidates);
		int maxFitnessIndex = 0;
		for(int m = 0; m < candFitness.length; m++) {
			if(candFitness[m] > maxFitnessIndex) {
				maxFitnessIndex = randomIndex[m];
			}
		}
		parents[0] = population[maxFitnessIndex];
		
		//prevent choosing two equal indexes
		int r;
		do {
			r = (int)Math.floor(Math.random() * population.length);
			parents[1] = population[r];
		} while(r == maxFitnessIndex);
		
		parents[1] = population[r];
		return parents;
	}
	
	/**
	 * This selection method chooses both 
	 * parents random by picking two not equal indexes.
	 * 
	 * @param population
	 * @return
	 */
	
	public boolean[][] chooseBothRandom(boolean[][] population) {
		boolean[][] parents = new boolean[2][population[0].length];
		int r1 = (int)Math.floor(Math.random() * population.length);
		parents[0] = population[r1];

		// prevent choosing two equal indexes
		int r2;
		do {
			r2 = (int) Math.floor(Math.random() * population.length);
			parents[1] = population[r2];
		} while (r2 == r1);
		
		parents[1] = population[r2];
		return parents;
	}
	
	private double[] getSolutionsFitness(boolean[][] solutions) {
		FitnessFunction function = new FitnessFunction();
		double[] fitness = new double[this.k];
		
		for(int i = 0; i < this.k; i++) {
			fitness[i] = function.getFitness(solutions[i]);
		}
		return fitness;
	}
	
}
