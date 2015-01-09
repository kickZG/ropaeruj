package hr.fer.zemris.optjava.dz3;

public abstract class BitVectorDecoder implements IDecoder<BitVectorSolution> {

	protected double[] mins;
	protected double[] maxs;
	protected int[] bits;
	protected int n;
	protected int totalBits;
	
	public BitVectorDecoder(double[] mins, double[] maxs, int[] bits, int n) {
		this.mins = mins;
		this.maxs = maxs;
		this.bits = bits;
		this.n = n;
	}
	
	public BitVectorDecoder(double min, double max, int bits, int n) {
		this.mins = new double[n];
		this.maxs = new double[n];
		this.bits = new int[n];
		for(int i = 0; i < n; i++) {
			mins[i] = min;
			maxs[i] = max;
			this.bits[i] = bits;
		}
	}
	
	public int getTotalBits() {
		return this.totalBits;
	}
	
	public int getDimensions() {
		return this.n;
	}

	@Override
	public abstract double[] decode(BitVectorSolution bvs);

	@Override
	public abstract void decode(BitVectorSolution bvs, double[] solution);

}
