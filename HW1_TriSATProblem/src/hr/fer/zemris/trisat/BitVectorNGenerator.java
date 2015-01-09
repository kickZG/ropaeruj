package hr.fer.zemris.trisat;

import java.util.Iterator;

public class BitVectorNGenerator implements Iterable<MutableBitVector> {

	private BitVector bitVector = null;
	private Iterator<MutableBitVector> it;

	public BitVectorNGenerator(BitVector assignment) {
		this.bitVector = assignment;
	}

	@Override
	public Iterator<MutableBitVector> iterator() {
		boolean[] localArray = this.bitVector.get();
		final BitVector localVector = new BitVector(localArray);
		
		it = new Iterator<MutableBitVector>() {

			@Override
			public boolean hasNext() {
				for (int i = 0; i < bitVector.getSize(); i++) {
					if (!bitVector.get(i)) {
						return true;
					}
				}
				return false;
			}

			@Override
			public MutableBitVector next() {
				int value = convertToInteger(localVector) + 1;
				BitVectorNGenerator.this.updateVector(localVector, value);
				return new BitVector(convertToBoolean(value)).copy();
			}

			@Override
			public void remove() {
			}
		};
		
		return it;
	}
	
	private void updateVector(BitVector localVector, int value) {
		this.bitVector = new BitVector(convertToBoolean(value));
	}

	private int convertToInteger(BitVector bits) {
		int value = 0;

		for (int i = 0; i < bitVector.getSize(); i++) {
			if (bitVector.get(i)) {
				value += Math.pow(2, (double) bitVector.getSize() - i - 1);
			}
		}
		
		return value;
	}

	private boolean[] convertToBoolean(int n) {
		boolean[] booleanArray = new boolean[bitVector.getSize()];

		for (int i = bitVector.getSize()-1; i >= 0; i--) {
			if (n % 2 == 1) {
				booleanArray[i] = true;
			} else {
				booleanArray[i] = false;
			}
			n /= 2;
		}
		
		return booleanArray;
	}

	public MutableBitVector[] createNeighborhood() {
		MutableBitVector[] listOfNeighbors = new MutableBitVector[this.bitVector.getSize()];
		boolean[] sampleArray = new boolean[this.bitVector.getSize()];
		
		for(int i = 0; i < this.bitVector.getSize(); i++) {
			sampleArray[i] = this.bitVector.get(i);
		}
		
		for(int i = 0; i < this.bitVector.getSize(); i++) {
			boolean[] localArray = new boolean[sampleArray.length];
			
			for(int j = 0; j < sampleArray.length; j++) {
				localArray[j] = sampleArray[j];
			}
			localArray[i] = !sampleArray[i];
			
			listOfNeighbors[i] = new MutableBitVector(localArray);
		}
		
		return listOfNeighbors;
	}

}
