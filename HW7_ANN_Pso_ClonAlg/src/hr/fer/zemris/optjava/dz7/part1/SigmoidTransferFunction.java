package hr.fer.zemris.optjava.dz7.part1;

public class SigmoidTransferFunction implements ITransferFunction {

	@Override
	public double getValue(double net) {
		return 1 / (1 + Math.exp(-net));
	}
	
}
