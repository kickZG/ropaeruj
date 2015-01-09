package hr.fer.zemris.optjava.dz8;

public class HyperboloidTangens implements ITransferFunction {

	@Override
	public double getValue(double net) {
		return 2 * (1 / (1 + Math.exp(-net))) - 1;
	}

}
