package hr.fer.zemris.optjava.dz3;

public class GreyBinaryDecoder extends BitVectorDecoder {
	
	public GreyBinaryDecoder(double[] mins, double[] maxs, int[] bits, int n) {
		super(mins, maxs, bits, n);
	}
	
	public GreyBinaryDecoder(double min, double max, int bits, int n) {
		super(min, max, bits, n);	
	}
	
	@Override
	public double[] decode(BitVectorSolution bvs) {
		
		return null;
	}

	@Override
	public void decode(BitVectorSolution bvs, double[] values) {
		
	}

}
