package hr.fer.zemris.optjava.dz2;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Prijenosna {
	
	private static double[][] inputX = new double[20][5];
	private static double[] inputY = new double[20];
	
	public static final double[] koef = new double[6];

	public static void main(String[] args) throws IOException {
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
		
		//String algorithm = args[1];
		//int maxIter = Integer.parseInt(args[2]);

		IHFunction function4 = new IHFunction() {

			@Override
			public int getNumberOfVariables() {
				return 5;
			}

			@Override
			public double getFunctionValue(double[] x) {
				double[] f = new double[20];
				
				for(int i = 0; i < 20; i++) {
					f[i] = koef[0] * inputX[i][0] + koef[1] * Math.pow(inputX[i][0], 3) * inputX[i][1] +
							koef[2] * Math.exp(koef[3] * inputX[i][2]) * (1 + Math.cos(koef[4] * inputX[i][3])) +
							koef[5] * inputX[i][3] * Math.pow(inputX[i][4], 2);
				}

				double functionValue = 0.d;

				for(int i = 0; i < 10; i++) {
					functionValue += Math.pow(f[i] - inputY[i], 2);
				}
				return functionValue;
			}

			@Override
			public double[] getGradientValue(double[] x) {
				double[] gradient = new double[5];

				for(int i = 0; i < 20; i++) {
					gradient[0] += 2 * (koef[0] + 3 * koef[1] * Math.pow(inputX[i][0], 2) * inputX[i][1]) * (1 - inputY[i]);
					gradient[1] += 2 * (koef[1] * Math.pow(inputX[i][0], 3)) * (1 - inputY[i]);
					gradient[2] += 2 * (koef[2] * koef[3] * Math.exp(koef[3] * inputX[i][2])) 
							* (1 + Math.cos(koef[4] * inputX[i][3])) * (1 - inputY[i]);
					gradient[3] += - 2 * (koef[2] * koef[4] * Math.exp(koef[3] * inputX[i][2]) *
							Math.sin(koef[4] * inputX[i][3]) + koef[5] * Math.pow(inputX[i][4], 2)) * (1 - inputY[i]);
					gradient[4] += 2 * (2 * koef[5] * inputX[i][3] * inputX[i][4]) * (1 - inputY[i]);
				}
				return gradient;
			}

			@Override
			public double[][] getHessian(double[] x) {
				// TODO Auto-generated method stub
				return null;
			}

		};

		double[] gdResult = NumOptAlgorithms.gradientDescent(function4, 1000, koef);

		if(gdResult[0] == koef[0]) System.out.println(gdResult[0] + " " + koef[0]);

		//TODO - draw trajectory
		System.out.println("Gradient descent result (4): " + function4.getFunctionValue(gdResult));

		System.out.println("\n------------------\n");
	}

}
