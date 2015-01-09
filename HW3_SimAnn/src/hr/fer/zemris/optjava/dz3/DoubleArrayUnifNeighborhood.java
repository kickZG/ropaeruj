package hr.fer.zemris.optjava.dz3;

import java.util.Random;

public class DoubleArrayUnifNeighborhood implements INeighborhood<DoubleArraySolution> {

	private double[] deltas;
	Random rand;
	
	public DoubleArrayUnifNeighborhood(double[] deltas) {
		this.deltas = deltas;
	}
	
	@Override
	public DoubleArraySolution randomNeighbor(DoubleArraySolution das) {
		double[] values = das.getValues();
		for(int i = 0; i < deltas.length; i++) {
			values[i] = deltas[i];
		}
		das.setValues(values);
		return das;
	}

}
