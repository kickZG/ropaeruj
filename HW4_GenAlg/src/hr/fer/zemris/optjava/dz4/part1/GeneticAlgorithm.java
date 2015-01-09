package hr.fer.zemris.optjava.dz4.part1;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This as an implementation of generation genetic algorithm on the
 * minimization of the function (...function...)
 * 
 * Pseudokod generacijskog genetskog algoritma:
 * P = stvori_pocetnu_populaciju (VEL_POP)
 * evaluiraj(P) --- sačuvaj elitu
 * ponavljaj_dok_nije_kraj
 * 		nova_populacija P' = (0)
 * 		ponavljaj_dok_je veličina(P') < VEL_POP
 * 			odaberi R1 i R2 iz P
 * 			{D1} = krizaj(R1, R2)
 * 			mutiraj D1
 * 		kraj
 * 		P = P'
 * kraj
 * 
 * @author kristijan
 *
 */

public class GeneticAlgorithm {

	private static int populationSize;
	private static double minError;
	private static int maxGenerations;
	private static String selectionType;
	private static double sigma;
	
	public static double[][] inputX = new double[20][5];
	public static double[] inputY = new double[20];
	private static double[][] population;
	
	//parameters
	private static int numberOfVariables = 6;
	private static int eliteSolutionNumber; //later
	private static int tournamentParam = 5;
	private static double mutationStrength = 10.d;
	
	public static void main(String[] args) throws IOException {
		FileInputStream fstream = new FileInputStream("02-zad-prijenosna.txt");
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		//parameters
		/*
				populationSize = Integer.parseInt(args[0]);
				minError = Double.parseDouble(args[1]);
				maxGenerations = Integer.parseInt(args[2]);
				selectionType = args[3];
				sigma = Integer.parseInt(args[4]);
		 */

		populationSize = 20;
		minError = 10.d;
		maxGenerations = 5;
		selectionType = "tournament";
		sigma = 1.d;

		eliteSolutionNumber = populationSize / 8;
		
		population = new double[populationSize][numberOfVariables];

		String strLine;
		int row = 0;
		
		//stvori početnu populaciju
		for(int j = 0; j < populationSize; j++) {
			for(int i = 0; i < numberOfVariables; i++) {
				population[j][i] = Math.random() * 20 - 10;
			}
		}

		while((strLine = br.readLine()) != null) {
			if(strLine.startsWith("#")) {
				continue;
			}
			else {
				strLine = strLine.replace("[", "");
				strLine = strLine.replace("]", "");
				strLine = strLine.replace("\\s+", "");
				String[] parsedString = strLine.split(",");
				System.out.println(strLine);
				for(int j = 0; j < 5; j++) {
					inputX[row][j] = Double.parseDouble(parsedString[j]);
				}
				inputY[row] = Double.parseDouble(parsedString[5]);
			}
			row++;
		}
		fstream.close();
		
		//---------------------------------------------------------------

		//useful objects
		IFunction function = new Function();
		EliteEvaluation evaluator = new EliteEvaluation(eliteSolutionNumber);
		ISelection roulette = new RouletteWheel();
		ISelection tournament = new Tournament(tournamentParam);
		ICrossOperator crossOperator = new BLXCrossOperator(sigma);
		IDoubleMutation mutator = new UnifDoubleMutation(mutationStrength);
		
		int generationCounter = 0;
		
		//genetic algorithm
		while(generationCounter < maxGenerations) {	//outer loop counter
			double[][] newPopulation = new double[populationSize][numberOfVariables];
			double[][] elite = getSolutionsFromIndexes(evaluator.getEliteMembers
					(getPopulationFitness(population), numberOfVariables), population);
			//add elite members to the new population
			for(int i = 0; i < elite.length; i++) {
				newPopulation[i] = elite[i];
				System.out.println("ELITE: " + printSolution(elite[i]));
			}
			//set the length of the population to the number of elite members
			int newPopulationSize = elite.length;
			double[] populationFitness = getPopulationFitness(population);
			/*
			for(int i = 0; i < populationSize; i++) {
				System.out.println(printSolution(population[i]));
				System.out.println(populationFitness[i]);
			}
			*/
			
			while(newPopulationSize < populationSize) {
				double[][] parents = new double[2][numberOfVariables];
				if(selectionType.equals("roulette")) {
					parents = getSolutionsFromIndexes(getParents(roulette, population, populationFitness), population);
				} else {
					parents = getSolutionsFromIndexes(getParents(tournament, population, populationFitness), population);
				}
				double[] child = crossOperator.cross(parents[0], parents[1]);
				child = mutator.mutate(child);
				if(evaluator.isGoodEnough(minError, populationFitness)) {
					System.out.println("GOOD ENOUGH SOLUTION FOUND: " + printSolution(child));
					break;
				}
				newPopulation[newPopulationSize++] = child;
			}
			int indexOfBest = evaluator.getBest(populationFitness);
			System.out.println("Best of the " + generationCounter + 
					". generation" + printSolution(population[indexOfBest]));
			System.out.println("Best fitness in " + (generationCounter++) + 
					". generation: " + populationFitness[indexOfBest]);
			population = newPopulation;
		}
	}
	
	//----------------------------------------------------------------------------
	
	/**
	 * Calculates the fitness of every solution in the given population.
	 * 
	 * @param population
	 * @return fitness
	 */
	
	private static double[] getPopulationFitness(double[][] population) {
		double[] fitness = new double[populationSize];
		IFunction function = new Function();
		
		for(int i = 0; i < populationSize; i++) {
			fitness[i] = function.valueAt(population[i]);
		}
		
		return fitness;
	}
	
	/**
	 * Returns two parents. At least two times the method does roulette wheel spin
	 * or tournament selection, depending on argument selector.
	 * 
	 * @param selector
	 * @param population
	 * @param fitness
	 * @return
	 */
	
	private static int[] getParents(ISelection selector, double[][] population, double[] fitness) {
		int[] index = new int[2];
		double[][] parents = new double[2][numberOfVariables];
		
		index[0] = selector.choose(fitness);
		index[1] = selector.choose(fitness);
		while(index[1] == index[0]) {
			index[1] = selector.choose(fitness);
		}
		return index;
	}
	
	/**
	 * Returns number of solutions for given index array.
	 * 
	 * @param index
	 * @return
	 */
	
	private static double[][] getSolutionsFromIndexes(int[] index, double[][] population) {
		double[][] solutions = new double[index.length][numberOfVariables];
		for(int i = 0; i < index.length; i++) {
			solutions[i] = population[index[i]];
		}
		return solutions;
	}
	
	private static String printSolution(double[] child) {
		String s = "[";
		
		for(int i = 0; i < child.length; i++) {
			s += child[i];
			if(i < child.length -1) {
				s += ", ";
			}
		}
		return s + "]";
	}

}
