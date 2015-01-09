package hr.fer.zemris.trisat;

import java.util.Random;

public class BitVector {

	private int size = 0;
	private boolean[] bitVector;
	
	public BitVector(Random rand, int numberOfBits) {
		this.size = numberOfBits;
		
		for(int i = 0; i < numberOfBits; i++) {
			this.bitVector[i] = rand.nextBoolean();
		}
	}
	
	public BitVector(boolean[] bits) {
		this.size = bits.length;
		this.bitVector = new boolean[size];
		
		for(int i = 0; i < size; i++) {
			this.bitVector[i] = bits[i];
		}
	}
	
	public BitVector(int n) {
		this.size = (int) (Math.log(n) / Math.log(2));
		
		for(int i = this.size-1; i >= 0; i--) {
			if(n%2 == 1) {
				bitVector[i] = true;
			} else {
				bitVector[i] = false;
			}
			n /= 2;
		}
	}
	
	// vraća vrijednost index-te varijable
	public boolean get(int index) {
		//TODO - fix this
		return bitVector[index];
	}
	
	public int getSize() {
		return this.size;
	}
	
	public boolean[] get() {
		return bitVector;
	}
	
	@Override
	public String toString() {
		String str = new String();
		
		for(int i = 0; i < this.size; i++) {
			if(bitVector[i]) {
				str += "1";
			} else {
				str += "0";
			}
		}
		
		return str;
		
	}
	
	public MutableBitVector copy() {
		return new MutableBitVector(bitVector);
	}
	
}
