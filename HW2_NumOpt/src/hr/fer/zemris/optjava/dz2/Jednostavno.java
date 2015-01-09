package hr.fer.zemris.optjava.dz2;

import java.io.IOException;

public class Jednostavno {

	public static final double lambda = 0.025;
	public static final double[] x = { Math.random() * 10 - 5, Math.random() * 10 - 5 };
	
	public static void main(String[] args) throws IOException {
		
		IHFunction function1a = new IHFunction() {
			@Override
			public int getNumberOfVariables() {
				return 2;
			}

			@Override
			public double getFunctionValue(double[] x) {
				return (x[0] * x[0] + (x[1] - 1) * (x[1] - 1));
			}

			@Override
			public double[] getGradientValue(double[] x) {
				return new double[] {2 * x[0], 2 * x[1] - 2};
			}

			@Override
			public double[][] getHessian(double[] x) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		IHFunction function1b = new IHFunction() {
			@Override
			public int getNumberOfVariables() {
				return 2;
			}

			@Override
			public double getFunctionValue(double[] x) {
				return (x[0] - 1) * (x[0] - 1) + 10 * (x[1] - 2) * (x[1] - 2);
			}

			@Override
			public double[] getGradientValue(double[] x) {
				return new double[] {2 * x[0] - 2, 20 * x[1] - 40};
			}
			
			@Override
			public double[][] getHessian(double[] x) {
				return new double[][] {{2, 0}, {0, 2}};
			}
		};
		
		IHFunction function2a = new IHFunction() {
			@Override
			public int getNumberOfVariables() {
				return 2;
			}

			@Override
			public double getFunctionValue(double[] x) {
				return (x[0] - 1) * (x[0] - 1) + 10 * (x[1] - 2) * (x[1] - 2);
			}

			@Override
			public double[] getGradientValue(double[] x) {
				return new double[] {2 * x[0] - 2, 20 * x[1] - 40};
			}

			@Override
			public double[][] getHessian(double[] x) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		IHFunction function2b = new IHFunction() {
			@Override
			public int getNumberOfVariables() {
				return 2;
			}

			@Override
			public double getFunctionValue(double[] x) {
				return (x[0] - 1) * (x[0] - 1) + 10 * (x[1] - 2) * (x[1] - 2);
			}

			@Override
			public double[] getGradientValue(double[] x) {
				return new double[] {2 * x[0] - 2, 20 * x[1] - 40};
			}
			
			@Override
			public double[][] getHessian(double[] x) {
				return new double[][] {{2, 0}, {0, 2}};
			}
		};
		
		// zadatak 1
		double[] gdResult = NumOptAlgorithms.gradientDescent(function1a, 1000, x);
		double[] nmResult = NumOptAlgorithms.newtonMethod(function1b, 1);
		
		//TODO - draw trajectory
		System.out.println("Gradient descent result (1a): " + function1a.getFunctionValue(gdResult));
		System.out.println("Newton's Method result (1b): " + function1b.getFunctionValue(nmResult));
		
		System.out.println("\n------------------\n");
		
		// zadatak 2
		gdResult = NumOptAlgorithms.gradientDescent(function2a, 1000, x);
		nmResult = NumOptAlgorithms.newtonMethod(function2b, 1);
		
		//TODO - draw trajectory
		System.out.println("Gradient descent result (2a): " + function2a.getFunctionValue(gdResult));
		System.out.println("Newton's method result (2b): " + function2b.getFunctionValue(nmResult));
		
		System.out.println("\n------------------\n");
	}

}
