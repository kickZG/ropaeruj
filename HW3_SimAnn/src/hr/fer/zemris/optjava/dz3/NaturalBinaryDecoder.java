package hr.fer.zemris.optjava.dz3;

public class NaturalBinaryDecoder extends BitVectorDecoder {

	public NaturalBinaryDecoder(double[] mins, double[] maxs, int[] bits, int n) {
		super(mins, maxs, bits, n);
	}
	
	public NaturalBinaryDecoder(double min, double max, int bits, int n) {
		super(min, max, bits, n);
	}

	@Override
	public double[] decode(BitVectorSolution bvs) {
		//TODO - refactoring -> bvs is a complete n-dimensional solution, need to separate it in chunks
		double[] solution = new double[this.n];
		byte[] b = bvs.getBits();
		
		System.out.println(bvs.toString());

		for(int j = 0; j < this.n; j++) {
			int decimal = 0;
			for(int i = 0; i < b.length; i++) {
				decimal += b[i] * Math.pow(128, i);
			}
			if(decimal > Math.pow(2, this.bits[j])) {
				throw new IllegalArgumentException("Number of bits larger than available size.");
			} else {
				solution[j] = this.mins[j] + (decimal / Math.pow(2, this.bits[j])) * (this.maxs[j] - this.mins[j]);
			}
		}
		return solution;
	}

	@Override
	public void decode(BitVectorSolution bvs, double[] values) {
		
	}

}
