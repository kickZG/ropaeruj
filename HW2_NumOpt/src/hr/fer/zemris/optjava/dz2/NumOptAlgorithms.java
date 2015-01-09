package hr.fer.zemris.optjava.dz2;

import Jama.*;

public class NumOptAlgorithms {
	
	public static double[] gradientDescent(IHFunction function, int maxIter, double[] currentPoint) {
		for(int i = 0; i < maxIter; i++) {
			System.out.println(i + ".) " + function.getFunctionValue(currentPoint));
			double[] gradient = function.getGradientValue(currentPoint);
			for(int j = 0; j < function.getNumberOfVariables(); j++) {
				currentPoint[j] -= Jednostavno.lambda * gradient[j];
			}
		}
		return currentPoint;
	}
	
	public static double[] newtonMethod(IHFunction function, int maxIter) {
		double[] currentPoint = new double[function.getNumberOfVariables()];
		
		//random starting point
		for(int i = 0; i < function.getNumberOfVariables(); i++) {
			currentPoint[i] = Math.random() * 10 - 5;
		}
		
		Matrix hessian = new Matrix(function.getHessian(currentPoint));
		hessian = hessian.inverse();
		double[][] hessianInverse = hessian.getArrayCopy();
		double[] tau = new double[function.getNumberOfVariables()];
		
		for(int i = 0; i < function.getNumberOfVariables(); i++) {
			tau[i] = 0;
			double[] gradient = function.getGradientValue(currentPoint);
			for(int j = 0; j < function.getNumberOfVariables(); j++) {
				tau[i] += hessianInverse[i][j] * gradient[j];
			}
		}
		for(int i = 0; i < function.getNumberOfVariables(); i++) {
			currentPoint[i] += tau[i];
		}
		return currentPoint;
	}
	
}
