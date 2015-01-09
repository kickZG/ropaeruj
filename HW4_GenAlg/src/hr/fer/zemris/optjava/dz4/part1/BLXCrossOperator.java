package hr.fer.zemris.optjava.dz4.part1;

/**
 * Class implements BLX-alpha cross operator
 * 
 * @author kristijan
 *
 */

public class BLXCrossOperator implements ICrossOperator {

	public double sigma;
	
	public BLXCrossOperator(double sigma) {
		this.sigma = sigma;
	}

	/**
	 * Mutate method takes two parents and does a BLX-alpha cross operation.
	 * @param parent1, parent2 Two parents.
	 * @return child
	 */
	
	@Override
	public double[] cross(double[] parent1, double[] parent2) {
		double[] child = new double[parent1.length];
		
		System.out.println(printSolution(parent1));
		System.out.println(printSolution(parent2));
		
		for(int i = 0; i < parent1.length; i++) {
			double min;
			double max;
			if(parent1[i] < parent2[i]) {
				min = parent1[i];
				max = parent2[i];
			} else {
				max = parent1[i];
				min = parent2[i];
			}
			double I = max - min;
//			System.out.println("Max-min: " + I);
			
			child[i] = Math.random() * ((min - I * this.sigma) + (max + I * this.sigma));
		}
		return child;
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
