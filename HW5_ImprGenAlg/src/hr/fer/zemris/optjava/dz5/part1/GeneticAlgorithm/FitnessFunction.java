package hr.fer.zemris.optjava.dz5.part1.GeneticAlgorithm;

public class FitnessFunction {

	public double getFitness(boolean[] solution) {
		int k = 0;
		int n = solution.length;
		for(int i = 0; i < n; i++) {
			if(solution[i] == true) {
				k++;
			}
		}
		if(k <= 0.8 * n) {
			return (double)k/n;
		}
		else if(k <= 0.9 * n) {
			return 0.8;
		}
		else {
			return (double)((2 * k/n) -1);
		}
	}
	
}
