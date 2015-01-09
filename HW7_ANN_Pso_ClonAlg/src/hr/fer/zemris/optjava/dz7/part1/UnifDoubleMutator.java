package hr.fer.zemris.optjava.dz7.part1;

public class UnifDoubleMutator implements IMutateOperator {

	private double interval;
	
	public UnifDoubleMutator(double interval) {
		this.interval = interval;
	}
	
	@Override
	public double[][] hypermutate(double[][] p) {
		for(int i = 0; i < p.length; i++) {
			for(int j = 0; j < p[0].length; j++) {
				p[i][j] += Math.random() * interval / (double)p.length * i;
			}
		}
		return p;
	}

}
