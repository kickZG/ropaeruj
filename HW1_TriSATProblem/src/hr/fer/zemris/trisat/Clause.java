package hr.fer.zemris.trisat;

public class Clause {

	private int[] indexes;
	private int size;
	
	public Clause(int[] indexes) {
		this.indexes = indexes;
		this.size = indexes.length;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public int getLiteral(int index) {
		return this.indexes[index];
	}
	
	public boolean isSatisfied(BitVector assignment) {
		for(int i = 0; i < this.size; i++) {
			if((assignment.get(Math.abs(indexes[i])-1) && indexes[i] > 0)
					|| (!assignment.get(Math.abs(indexes[i])-1) && indexes[i] < 0)) {
				return true;
			}
		}
			
		return false;
	}

	@Override
	public String toString() {
		String str = "";
		
		for(int i = 0; i < this.indexes.length; i++) {
			str += indexes[i] + " ";
		}
		return str;
	}
	
}

