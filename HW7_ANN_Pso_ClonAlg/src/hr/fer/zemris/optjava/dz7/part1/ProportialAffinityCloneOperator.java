package hr.fer.zemris.optjava.dz7.part1;

public class ProportialAffinityCloneOperator implements ICloneOperator {

	private double beta;
	
	public ProportialAffinityCloneOperator(double beta) {
		this.beta = beta;
	}
	
	@Override
	public double[][] clone(double[][] p) {
		double[][] clones = new double[Constants.n * (int)Math.floor(Constants.n / 2.d)][p[0].length];
		
		for(int i = 1, k = 0; i < p.length+1; i++) {
			for(int j = (int)((beta * p.length) / (double)i); j > 0; j--, k++) {
				clones[k] = p[i-1].clone();
			}
		}
		return clones;
	}
	
}
