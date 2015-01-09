package hr.fer.zemris.optjava.dz3;

import java.util.Random;

public class BitVectorSolution extends SingleObjectiveSolution {

	private int n;
	private byte[] bits;
	
	public BitVectorSolution(int n) {
		this.n = n;
	}
	
	public BitVectorSolution newLikeThis() {
		BitVectorSolution bvs = new BitVectorSolution(n);
		bvs.setBits(bits);
		return bvs;
	}
	
	public BitVectorSolution duplicate() {
		return new BitVectorSolution(n);
	}
	
	public void randomize(Random r) {
		for(int i = 0; i < Math.ceil((double)(n * 10) / 8); i++) {
			byte b = -128;
			for(int j = 0; j < 8; j++) {
				if(Math.round(Math.random()) == 1) {
					b += Math.pow(2, j);
				}
			}
			bits[i] = b;
		}
	}

	public byte[] getBits() {
		return this.bits;
	}
	
	public void setBits(byte[] bits) {
		this.bits = bits;
	}

	@Override
	public String toString() {
		String s = "";
		byte[] localBits = this.bits.clone();

		for(int i = 0; i < Math.ceil((double)(n * 10) / 8); i++) {
			for(int j = 0; j < 8; j++) {
				if((localBits[i] / 2) == 1) {
					s += "1";
				} else {
					s += "0";
				}
				localBits[i] /= 2;
			}
		}
		return s;
	}

}
