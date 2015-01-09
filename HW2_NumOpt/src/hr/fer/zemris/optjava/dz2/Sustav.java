package hr.fer.zemris.optjava.dz2;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Sustav {

	private static double[][] inputX = new double[10][10];
	private static double[] inputY = new double[10];
	
	public static final double[] x = new double[10];

	public static void main(String[] args) throws IOException {
		//zadatak 3
		FileInputStream fstream = new FileInputStream("02-zad-sustav.txt");	//TODO - args[]
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String strLine;
		int row = 0;
		for(int i = 0; i < 10; i++) {
			x[i] = Math.random() * 10 - 5;
		}

		while((strLine = br.readLine()) != null) {
			if(strLine.startsWith("#")) {
				continue;
			}
			else {
				strLine = strLine.replace("[", "");
				strLine = strLine.replace("]", "");
				//strLine = strLine.replace("\\s+", "");
				String[] parsedString = strLine.split(",");
				for(int j = 0; j < 10; j++) {
					parsedString[j] = parsedString[j].replace("\\s+", "");
					inputX[row][j] = Double.parseDouble(parsedString[j]);
				}
				inputY[row] = Double.parseDouble(parsedString[10]);
			}
			row++;
		}

		fstream.close();
		
		//String algorithm = args[1];
		//int maxIter = Integer.parseInt(args[2]);
		
		IHFunction function3 = new IHFunction() {
			
			@Override
			public int getNumberOfVariables() {
				return 10;
			}

			@Override
			public double getFunctionValue(double[] x) {
				double[] f = new double[10];
				
				for(int i = 0; i < 10; i++) {
					for(int j = 0; j < 10; j++) {
						f[i] = inputX[i][j] * x[j];
					}
				}
				double functionValue = 0.d;
				
				for(int i = 0; i < 10; i++) {
					functionValue += (f[i] - inputY[i]) * (f[i] - inputY[i]);
				}
				return functionValue;
			}

			@Override
			public double[] getGradientValue(double[] x) {
				double[] gradient = new double[10];
				Arrays.fill(gradient, 0);
				
				for(int i = 0; i < 10; i++) {
					for(int j = 0; j < 10; j++) {
						gradient[j] += 2 * inputX[i][j] * (1 - inputY[i]);
					}
				}
				return gradient;
			}

			@Override
			public double[][] getHessian(double[] x) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		double[] gdResult = NumOptAlgorithms.gradientDescent(function3, 1000, x);
		
		if(gdResult[0] == x[0]) System.out.println(gdResult[0] + " " + x[0]);
		//double[] nmResult = NumOptAlgorithms.newtonMethod(function3, 1);
		
		//TODO - draw trajectory
		System.out.println("Gradient descent result (3): " + function3.getFunctionValue(gdResult));
		//System.out.println("Newton's Method result (1b): " + function3.getFunctionValue(nmResult));
		
		System.out.println("\n------------------\n");
		
	}

}
