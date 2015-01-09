package hr.fer.zemris.optjava.dz4.part1;

/**
 * This class implements the type of evaluation where the best few
 * solutions are saved for the next generation. 
 * 
 * @author kristijan
 *
 */

public class EliteEvaluation {
	
	private int numberOfElite;
	
	/**
	 * The constructor gets number of elite members.
	 * @param numberOfElite
	 */
	
	public EliteEvaluation(int numberOfElite) {
		this.numberOfElite = numberOfElite;
	}
	
	/**
	 * This method returns n elite members to be saved for
	 * the next generation
	 * @param fitness numberOfVariables Fitness of the population.
	 * Number of variables for the problem.
	 * @return elite
	 */
	
	public int[] getEliteMembers(double[] fitness, int numberOfVariables) {
		int[] index = new int[fitness.length];
		//initialize the index array
		for(int i = 0; i < fitness.length; i++) {
			index[i] = i;
		}
		
		for(int i = 0; i < fitness.length -1; i++) {
			for(int j = 1; j < fitness.length; j++) {
				if(fitness[i] < fitness[j]) {
					int temp = index[i];
					index[i] = index[j];
					index[j] = temp;
				}
			}
		}
		int[] indexToReturn = new int[this.numberOfElite];
		for(int i = 0; i < this.numberOfElite; i++) {
			indexToReturn[i] = index[i];
		}
		return indexToReturn;
	}
	
	/**
	 * Method checks if the current solution is good enough to end the program.
	 */
	
	public boolean isGoodEnough(double minError, double[] fitness) {
		double bestFitness = fitness[0];
		for(int i = 0; i < fitness.length; i++) {
			if(fitness[i] > bestFitness) {
				bestFitness = fitness[i];
			}
		}
		return bestFitness < minError;
	}
	
	/**
	 * Get best solution for current generation.
	 * @param fitness
	 * @return
	 */
	
	public int getBest(double[] fitness) {
		double best = fitness[0];
		int index = 0;
		
		for(int i = 0; i < fitness.length; i++) {
			if(fitness[i] < best) {
				best = fitness[i];
				index = i;
			}
		}
		return index;
	}

}
