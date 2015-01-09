package hr.fer.zemris.optjava.dz4.part1;

import hr.fer.zemris.optjava.dz4.part1.GeneticAlgorithm;;

public class Function implements IFunction {

	public Function() {
	}
	
	@Override
	public double valueAt(double[] solution) {
		double[] f = new double[20];
		
		for(int i = 0; i < 20; i++) {
			f[i] = solution[0] * GeneticAlgorithm.inputX[i][0] + solution[1] * 
					Math.pow(GeneticAlgorithm.inputX[i][0], 3) * GeneticAlgorithm.inputX[i][1] +
					solution[2] * Math.exp(solution[3] * GeneticAlgorithm.inputX[i][2]) * 
					(1 + Math.cos(solution[4] * GeneticAlgorithm.inputX[i][3])) +
					solution[5] * GeneticAlgorithm.inputX[i][3] * Math.pow(GeneticAlgorithm.inputX[i][4], 2);
			//System.out.println(i + ". iteration: " + f[i]);
		}
		
		double functionValue = 0.d;

		for(int i = 0; i < 10; i++) {
			functionValue += Math.pow(f[i] - GeneticAlgorithm.inputY[i], 2);
			//System.out.println(i + ". iteration(2): " + f[i]);
		}
		return functionValue;
	}
	/*
	public int getNumberOfVariables() {
		return 6;
	}
	*/

}
