package hr.fer.zemris.optjava.dz4.part1;

public class UnifDoubleMutation implements IDoubleMutation {

	private double interval;
	
	public UnifDoubleMutation(double interval) {
		this.interval = interval;
	}
	
	@Override
	public double[] mutate(double[] solution) {
		for(int i = 0; i < solution.length; i++) {
			solution[i] += Math.random() * interval + interval/2;
		}
		return solution;
	}

}
