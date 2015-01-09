package hr.fer.zemris.optjava.dz5.part1.GeneticAlgorithm;

/**
 * RAPGA algorithm for solving modified Max-Ones problem.
 *        { k/n, for k <= 0.8*n
 * f(v) = { 0.8, for 0.8*n < k <= 0.9*n
 *        { 2*k/n - 1, for k > 0.9*n
 * 
 * @author kristijan
 *
 */

public class Rapga {

	//algorithm parameters
	public static int numOfIter = 10000;
	public static int i = 0;
	public static int initPopSize = 12;
	public static int maxPopSize = (int)Math.round(initPopSize * 1.2);
	public static int minPopSize = 5;
	public static double maxSelPressure = 10.0;
	public static double mutationProbality = 0.04;
	public static double lowerBound = 0.2;
	public static double upperBound = 0.9;
	public static double compFactor = lowerBound;
	public static double actSelPressure = 2.0;
	//public double succRatio;
	
	//additional parameters
	public static int kTournamentParam = 5;
	public static int crossParam = 8;
	
	/**
	 * RAPGA main method - implemented pseudo-code
	 * 
	 * @param args
	 */
	
	public static void main(String[] args) {	//static ?
		
		int n = Integer.valueOf(args[0]);
		boolean[][] currentPopulation = produceInitialPopulation(n);
		int currentPopSize = initPopSize;
		
		SelectionOperator tournament = new SelectionOperator(kTournamentParam);
		CrossOperator crossOperator = new CrossOperator(crossParam);
		MutateOperator mutateOperator = new MutateOperator(mutationProbality);
		FitnessFunction function = new FitnessFunction();

		while(i++ < numOfIter && actSelPressure < maxSelPressure && currentPopSize >= minPopSize) {
			//initialize next population
			boolean[][] nextPopulation = new boolean[maxPopSize][n];
			
			int newPopSize = 0;
			int poolSize = 0;
			while(newPopSize < maxPopSize && newPopSize + poolSize < currentPopSize * maxSelPressure) {
				//generate a child from the members of POPi based on their fitness values  using crossover and mutation
				
				//More selection operators
				//TODO - avoid this if in this function - just call the mutation method and let it do the rest
				int r = (int)Math.round(Math.random()*3);
				boolean[][] parents = new boolean[2][currentPopulation[0].length];
				if(r == 0) {
					parents = tournament.chooseBothTournament(currentPopulation);
				} 
				else if(r == 1) {
					parents = tournament.chooseOneRandom(currentPopulation);
				}
				else {
					parents = tournament.chooseBothRandom(currentPopulation);
				}
				
				//More cross operators
				//TODO - avoid this if in this function - just call the cross method and let it do the rest
				boolean[] child = new boolean[currentPopulation[0].length];
				if(function.getFitness(crossOperator.nCutCross(parents)) 
						> function.getFitness(crossOperator.uniformCross(parents))) {
					child = crossOperator.nCutCross(parents);
				} else {
					child = crossOperator.uniformCross(parents);
				}

				child = mutateOperator.mutate(child);
				
				//calculate fitness for children and parents
				double[] fParents = calculateFitness(parents);
				double fChild = calculateFitness(child)[0];
				
				double worseFitness;
				if(fParents[0] > fParents[1]) {
					worseFitness = fParents[1];
				} else {
					worseFitness = fParents[0];
				}
				
				//compare fitness and decide whether or not the child is going to the pool
				if(fChild <= worseFitness + Math.abs(fParents[0] - fParents[1]) * compFactor) {
					poolSize++;
				}
				else {
					if(!uniqueChild(child, nextPopulation)) {
						poolSize++;
					} else {
						nextPopulation[newPopSize++] = child;
					}
				}
			}
			actSelPressure = (newPopSize + poolSize) / newPopSize;
			
			//swap generations
			currentPopulation = nextPopulation;
			
			currentPopSize = newPopSize;
			newPopSize = 0;
			poolSize = 0;
			
			//adapt compFactor to the chosen strategy
			//TODO - any better strategy?
			if(compFactor < upperBound) {
				compFactor += 0.01;
			}
			
			//print
			System.out.println("\n" + i + ". generation:");
			for(int i = 0; i < currentPopSize; i++) {
				String vectorString = "";
				for(int j = 0; j < n; j++) {
					if(currentPopulation[i][j]) {
						vectorString += "1";
					} else {
						vectorString += "0";
					}
				}
				System.out.println(vectorString + " -> " + calculateFitness(currentPopulation[i])[0]);
			}
			System.out.println("Population size: " + currentPopSize);
		}
		
		if(i >= numOfIter) {
			System.out.println("Number of iterations exceeded! (" + numOfIter + ")");
		}
		
	}
	
	private static boolean[][] produceInitialPopulation(int n) {
		boolean[][] population = new boolean[initPopSize][n];
		for(int i = 0; i < initPopSize; i++) {
			for(int j = 0; j < n; j++) {
				if(Math.random() >= 0.5) {
					population[i][j] = true;
				} else {
					population[i][j] = false;
				}
			}
		}
		return population;
	}
	
	private static double[] calculateFitness(boolean[] ... solutions) {
		FitnessFunction function = new FitnessFunction();
		
		double[] fitness = new double[solutions.length];
		for(int i = 0; i < solutions.length; i++) {
			fitness[i] = function.getFitness(solutions[i]);
		}
		return fitness;
	}
	
	private static boolean uniqueChild(boolean[] child, boolean[][] newPop) {
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
