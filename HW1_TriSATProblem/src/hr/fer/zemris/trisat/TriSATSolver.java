package hr.fer.zemris.trisat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class TriSATSolver {
	
	private static int maxIter = 100000;
	private static int numberOfBest = 2;
	private static int percentageUnitAmount = 20;

	
	public static void main(String[] args) throws IOException {
		FileInputStream fstream = new FileInputStream(args[3]);
		short algorithmNumber = (short) Integer.parseInt(args[2]);

		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String strLine;
		int numberOfVariables = 0;
		int numberOfClauses = 0;
		int j = 0;

		List<Integer> clauseValues = new ArrayList<Integer>();
		Clause[] clauses = null;

		//Parsing the input
		while ((strLine = br.readLine()) != null) {
			if(strLine.startsWith("c")) {
				continue;
			}
			else if(strLine.startsWith("p")) {
				String[] splitString = strLine.split("\\s+");
				numberOfVariables = Integer.parseInt(splitString[2]);
				numberOfClauses = Integer.parseInt(splitString[3]);
				clauses = new Clause[numberOfClauses];
			}
			else if (strLine.startsWith("%")) {
				break;
			} else {
				String[] splitString = strLine.split("\\s+");
				clauseValues.clear();

				for (int i = 0; i < splitString.length; i++) {
					if (splitString[i].equals("")) {
						continue;
					} else if (Integer.parseInt(splitString[i].trim()) != 0) {
						clauseValues.add(Integer.parseInt(splitString[i].trim()));
					}
				}
				
				for (int k = 0; k < clauseValues.size(); k++) {
					if (clauseValues.get(k) == 0) {
						clauseValues.remove(k);
					}
				}
				clauses[j++] = new Clause(convertToIntArray(clauseValues.toArray()));
			}
		}
		in.close();
		
		// Initialization
		BitVector bitVector = new BitVector(generateZeroInitialSolution(numberOfVariables));
		MutableBitVector currentSolution = bitVector.copy();
		BitVectorNGenerator bitVectorNGenerator = new BitVectorNGenerator(
				new BitVector(generateZeroInitialSolution(numberOfVariables)));
		SATFormula formula = new SATFormula(numberOfVariables, clauses);
		Iterator<MutableBitVector> iterator = bitVectorNGenerator.iterator();
		
		if(algorithmNumber == 1) {
			System.out.println("Algorithm 1\n----------------------");

			// Algorithm 1
			while (iterator.hasNext()) {
				if (formula.isSatisfied(currentSolution)) {
					System.out.println(currentSolution.toString());
				}

				currentSolution = iterator.next();
			}

			System.out.println("\n----------------------");

		}
		
		// Initialization 2
		boolean[] randomSolution = generateRandomInitialSolution(numberOfVariables);
		bitVectorNGenerator = new BitVectorNGenerator(
				new BitVector(randomSolution));
		SATFormulaStats formulaStats = new SATFormulaStats(formula);
		formulaStats.setAssignment(new BitVector(randomSolution), false);
		
		boolean end = false;
		int numIter = 0;
		
		//if(algorithmNumber == 2) {

			System.out.println("Algorithm 2\n----------------------");

			int maxSatisfied = formulaStats.getNumberOfSatisfied(new BitVector(randomSolution));
			// Algorithm 2
			while(numIter++ < maxIter && !end) {
				List<MutableBitVector> bestSolutions = new ArrayList<MutableBitVector>();
				MutableBitVector[] neighborhood = bitVectorNGenerator.createNeighborhood();

				//find max
				boolean isBetter = false;
				for(int i = 0; i < neighborhood.length; i++) {
					formulaStats.setAssignment(neighborhood[i], false);
					if(formulaStats.getNumberOfSatisfied(neighborhood[i]) >= maxSatisfied) {
						maxSatisfied = formulaStats.getNumberOfSatisfied(neighborhood[i]);
						isBetter = true;
					} 
					if(formulaStats.getNumberOfSatisfied(neighborhood[i]) == numberOfClauses) {
						System.out.println(neighborhood[i].toString());
						end = true;
						break;
					}
				}
				int numOfMax = 0;
				for(int i = 0; i < neighborhood.length; i++) {
					formulaStats.setAssignment(neighborhood[i], false);
					if(formulaStats.getNumberOfSatisfied(neighborhood[i]) == maxSatisfied) {
						bestSolutions.add(neighborhood[i]);
						numOfMax++;
					}
				}

				int randNum = (int)Math.floor(Math.random() * numOfMax);
				if(!isBetter) {
					System.out.println("Worse solution (local optimum)! Abandoning the process...");
					end = true;
					break;
				}
				bitVectorNGenerator = new BitVectorNGenerator(bestSolutions.get(randNum));
			}

			if(!end) {
				System.out.println("Maximum number of iterations exceeded!");
			}
			System.out.println("\n----------------------");

		//}

		//if(algorithmNumber == 3) {

			// Initialization 3
			formulaStats.reset();
			formulaStats.setAssignment(new BitVector(randomSolution), true);
			System.out.println("Algorithm 3\n----------------------");
			numIter = 0;
			end = false;

			// Algorithm 3
			while(numIter++ < maxIter && !end) {
				MutableBitVector[] neighborhood = bitVectorNGenerator.createNeighborhood();

				double[] Z = new double[neighborhood.length];
				for(int i = 0; i < neighborhood.length; i++) {
					formulaStats.setAssignment(neighborhood[i], true);
					Z[i] = formulaStats.getNumberOfSatisfied(neighborhood[i]);
					if(formulaStats.getNumberOfSatisfied(neighborhood[i]) == numberOfClauses) {
						System.out.println(neighborhood[i].toString());
						end = true;
						break;
					}
					if(clauses[i].isSatisfied(neighborhood[i]))
						Z[i] += percentageUnitAmount * (1 - formulaStats.getPost(i));
					else 
						Z[i] -= percentageUnitAmount * (1 - formulaStats.getPost(i));
				}

				for(int i = 0; i < numberOfBest; i++) {
					for(int k = 1; k < neighborhood.length; k++) {
						if(Z[i] < Z[k]) {
							MutableBitVector temp = neighborhood[i];
							neighborhood[i] = neighborhood[k];
							neighborhood[k] = temp;
						}
					}
				}

				int randNum = (int)Math.floor(Math.random() * numberOfBest);
				bitVectorNGenerator = new BitVectorNGenerator(neighborhood[randNum]);
			}

			if(!end) {
				System.out.println("Maximum number of iterations exceeded!");
			}
		//}
	}
	
	/**
	 * Converts Object array to primitive int[] array.
	 * 
	 * @param values
	 * @return array int[]
	 */
	private static int[] convertToIntArray(Object[] values) {
		int[] array = new int[values.length];

		for (int i = 0; i < values.length; i++) {
			array[i] = (int) values[i];
		}
		return array;
	}
	
	/**
	 * Generates initial solution containing only false elements.
	 * 
	 * @param n Number of variables and length of the solution.
	 * @return solution boolean[]
	 */

	private static boolean[] generateZeroInitialSolution(int n) {
		boolean[] solution = new boolean[n];

		for (int i = 0; i < n; i++) {
			solution[i] = false;
		}

		return solution;
	}
	
	/**
	 * Generates random initial solution.
	 * 
	 * @param n Number of variables and length of the solution.
	 * @return
	 */
	
	private static boolean[] generateRandomInitialSolution(int n) {
		boolean[] solution = new boolean[n];
		Random r = new Random();
		
		for (int i = 0; i < n; i++) {
			solution[i] = r.nextBoolean();
		}
		
		return solution;
	}

}
