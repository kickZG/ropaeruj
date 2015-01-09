package hr.fer.zemris.optjava.dz5.part2.GeneticAlgorithm;

import java.io.IOException;
import java.util.Random;

public class Sasegasa {

	//algorithm parameters
	public static int populationSize;
	public static int numOfVillages;
	public static int subpopulationSize;
	public static int permSize;
	public static int maxGenerations = 200;
	
	public static double successRatio = 1.0;
	public static double maxSelPressure = 10.0;
	public static double lowerBound = 0.2;
	public static double upperBound = 0.9;
	public static double compFactor = lowerBound;
	
	//data
	public static int[][] D;
	public static int[][] C;
	
	public static void main(String[] args) throws IOException {
		populationSize = Integer.valueOf(args[1]);
		numOfVillages = Integer.valueOf(args[2]);
		subpopulationSize = populationSize / numOfVillages;
		
		DataParser parser = new DataParser(args[0], Integer.valueOf(args[1]), Integer.valueOf(args[2]));
		permSize = parser.getPermSize();
		D = parser.getDistances();
		C = parser.getQuantities();
		
		//generate subpopulations
		int[][][] subpopulations = generateInitSubpopulations();
		
		int totalNumOfGenerations = 0;
		int currentNumOfGenerations = 0;
		
		SelectionOperator selectionOperator = new SelectionOperator();
		CrossOperator crossOperator = new CrossOperator();
		MutateOperator mutateOperator = new MutateOperator();
		
		while(numOfVillages > 1) {
			while(currentNumOfGenerations++ < maxGenerations || isConverged()) {
				int[][][] nextSubpopulations = new int[numOfVillages][subpopulationSize][permSize];
				int[][] localPool = new int[subpopulationSize][permSize];
				int newSubpopSize = 0;
				int poolSize = 0;
				
				for(int i = 0; i < numOfVillages; i++) {
					//generate next generation
					//offspring selection
					while(newSubpopSize < subpopulationSize * successRatio && newSubpopSize + poolSize < subpopulationSize * maxSelPressure) {
						//generate a child from the members of POPi based on their fitness values  using crossover and mutation
						int[][] parents = new int[2][permSize];
						parents = selectionOperator.choose();
						int[] child = new int[permSize];
						child = crossOperator.cross();
						child = mutateOperator.mutate(child);
						
						//calculate fitness for children and parents
						int[] fParents = calculateFitness(parents);
						int fChild = calculateFitness(child)[0];
						
						int worseFitness;
						if(fParents[0] > fParents[1]) {
							worseFitness = fParents[1];
						} else {
							worseFitness = fParents[0];
						}
						
						//compare fitness and decide whether or not the child is going to the pool
						if(fChild <= worseFitness + Math.abs(fParents[0] - fParents[1]) * compFactor) {
							localPool[poolSize++] = child;
						}
						else {
							if(!uniqueChild(child, nextSubpopulations[i])) {
								localPool[poolSize++] = child;
							} else {
								nextSubpopulations[i][newSubpopSize++] = child;
							}
						}
					}
				}
				totalNumOfGenerations++;
				currentNumOfGenerations++;
				
				if(compFactor < upperBound) {
					compFactor += 0.02;
				}
			}
			numOfVillages--;
			subpopulationSize = populationSize / numOfVillages;
			currentNumOfGenerations = 0;
			
			//Adjust subpopulations
		}
		
	}
	
	private static boolean isConverged() {
		return false;
	}
	
	private static int[][][] generateInitSubpopulations() {
		int[][][] subpopulations = new int[numOfVillages][subpopulationSize][permSize];
		for(int i = 0; i < numOfVillages; i++) {
			for(int j = 0; j < subpopulationSize; j++) {
				for(int k = 0; k < permSize; k++) {
					subpopulations[i][j][k] = k;
				}
				shuffleArray(subpopulations[i][j]);
			}
		}
		return subpopulations;
	}
	
	static void shuffleArray(int[] ar) {
	    Random rnd = new Random();
	    for (int i = ar.length - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      // Simple swap
	      int a = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    }
	}
	
	private static int[] calculateFitness(int[] ... permutations) {
		FitnessFunction function = new FitnessFunction();
		
		int[] fitness = new int[permutations.length];
		for(int i = 0; i < permutations.length; i++) {
			fitness[i] = function.getValue(permutations[i]);
		}
		return fitness;
	}
	
	private static boolean uniqueChild(int[] child, int[][] newPop) {
		boolean isUnique = true;
		
		for(int i = 0; i < newPop.length && isUnique; i++) {
			boolean isDifferent = false;
			for(int j = 0; j < child.length; j++) {
				if(child[j] != newPop[i][j]) {
					isDifferent = true;
					break;
				}
			}
			if(!isDifferent) {
				isUnique = false;
			}
		}
		return isUnique;
	}

}
