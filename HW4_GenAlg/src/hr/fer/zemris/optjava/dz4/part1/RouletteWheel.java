package hr.fer.zemris.optjava.dz4.part1;

/**
 * Roulette wheel selection implementation class.
 * 
 * @author kristijan
 *
 */

public class RouletteWheel implements ISelection {

	/**
	 * default constructor
	 * 
	 */
	
	public RouletteWheel() {
	}
	
	/**
	 * Returns two indexes of chosen solutions.
	 * 
	 * @param population
	 * @param fitness
	 * @return index Two-element integer array of indexes.
	 */
	
	public int choose(double[] fitness) {
		int index[] = new int[fitness.length];
		double worstFitness = fitness[0];
		double bestFitness = fitness[0];
		
		//get best and worst fitness
		for(int i = 1; i < fitness.length; i++) {
			if(fitness[i] > worstFitness) {
				worstFitness = fitness[i];
			} else if(fitness[i] < bestFitness) {
				bestFitness = fitness[i];
			}
		}
		double[] relativeFitness = {0.d};
		double relativeSum = 0.d;
		
		//relative fitness -> razlika u odnosu na najgore rješenje
		for(int i = 0; i < fitness.length; i++) {
			relativeFitness[i] = -(fitness[i] - worstFitness);
			relativeSum += worstFitness - fitness[i];
		}
		
		//initialize index array
		for(int i = 0; i < fitness.length; i++) {
			index[i] = i;
		}

		double[] rouletteCoef = { 0.d };
		
		//if there is a population of solutions with similar fitness
		if(bestFitness - worstFitness < 0.1 * worstFitness) {
			//sort depending on relative fitness
			for(int i = 0; i < fitness.length -1; i++) {
				for(int j = 1; j < fitness.length; j++) {
					if(relativeFitness[i] < relativeFitness[j]) {
						int temp = index[i];
						index[i] = index[j];
						index[j] = temp;
					}
				}
			}
			//coef -> starting from best solution(length)
			int sumForSize = fitness.length * (fitness.length+1) / 2;
			//create roulette wheel
			for(int i = 0; i < fitness.length; i++) {
				rouletteCoef[i] = (fitness.length - i) / (double)sumForSize;
			}
		} else {
			//create roulette wheel
			for(int i = 0; i < fitness.length; i++) {
				rouletteCoef[i] = relativeFitness[i] / relativeSum;
			}
		}
		
		//TODO - riješit sranje s intervalima
		
		double rand = Math.random();
		if(rand < rouletteCoef[index[0]]) {
			//index
		}
		for(int i = 1; i < fitness.length-1; i++) {
			//if(rand )
		}
		
		int chosenIndex = -1;
		return chosenIndex;
	}
	
}
