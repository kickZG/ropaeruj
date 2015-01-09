package hr.fer.zemris.optjava.dz3;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SimulatedMain {

	public static double[][] inputX = new double[20][5];
	public static double[] inputY = new double[20];

	private static int numberOfVariables = 6;
	private static double[] koef = new double[numberOfVariables];

	public static void main(String[] args) throws NumberFormatException, IOException {
		//zadatak 4
		FileInputStream fstream = new FileInputStream("02-zad-prijenosna.txt");
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String strLine;
		int row = 0;
		for(int i = 0; i < 6; i++) {
			koef[i] = Math.random() * 20 - 10;
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
					System.out.println(parsedString[j]);
					inputX[row][j] = Double.parseDouble(parsedString[j]);
				}
				inputY[row] = Double.parseDouble(parsedString[5]);
			}
			row++;
		}
		fstream.close();

		//TODO - Algorithm parameters
		double[] mins = new double[numberOfVariables];
		double[] maxs = new double[numberOfVariables];
		double[] deltas = new double[numberOfVariables];
		int[] bits = new int[numberOfVariables];
		double alpha = 0.8;
		double tInitial = 700.d;
		int innerLimit = 30;
		int outerLimit = 1000;

		//BitVector solution objects
		IDecoder<BitVectorSolution> decoder = new NaturalBinaryDecoder(mins, maxs, bits, numberOfVariables);
		INeighborhood<DoubleArraySolution> neighborhood = new DoubleArrayUnifNeighborhood(deltas);
		BitVectorSolution startWith = new BitVectorSolution(numberOfVariables);
		IFunction function = new Function();
		ITempSchedule tempSchedule = new GeometricTempSchedule(alpha, tInitial, innerLimit, outerLimit);
		boolean minimize = true;

		//BitVector greedy algorithm
		IOptAlgorithm<BitVectorSolution> bitVectorGreedy = new GreedyAlgorithm(decoder, neighborhood, startWith, function, minimize);
		bitVectorGreedy.run();

		//Bitvector simulated annealing algorithm
		IOptAlgorithm<BitVectorSolution> bitVectorSimAnn = new SimulatedAnnealing(decoder, neighborhood, startWith, function, tempSchedule, minimize);
		bitVectorSimAnn.run();

		//DoubleArray greedy algorithm
		IOptAlgorithm<DoubleArraySolution> doubleArrayGreedy = new GreedyAlgorithm(decoder, neighborhood, startWith, function, minimize);
		doubleArrayGreedy.run();

		//DoubleArray simulated annealing algorithm
		IOptAlgorithm<DoubleArraySolution> doubleArraySimAnn = new SimulatedAnnealing(decoder, neighborhood, startWith, function, tempSchedule, minimize);
		doubleArraySimAnn.run();

	}
}
