package hr.fer.zemris.trisat;

public class SATFormula {
	
	private int numberOfVariables;
	private Clause[] clauses;

	public SATFormula(int numberOfVariables, Clause[] clauses) {
		this.numberOfVariables = numberOfVariables;
		this.clauses = clauses;
	}
	
	public int getNumberOfVariables() {
		return this.numberOfVariables;
	}
	
	public int getNumberOfClauses() {
		return this.clauses.length;
	}
	
	public Clause getClause(int index) {
		return this.clauses[index];
	}
	
	public boolean isSatisfied(BitVector assignment) {
		boolean isValid = true;
		
		for (Clause c : clauses) {
			if (!c.isSatisfied(assignment)) {
				isValid = false;
				break;
			}
		}
		
		return isValid;
	}
	
	@Override
	public String toString() {
		String str = "f(";
		
		for(int i = 0; i < numberOfVariables; i++) {
			str += "x" + i;
			if(i != numberOfVariables-1) {
				str += ",";
			}
		}
		str += ")=";
		
		for(int i = 0; i < clauses.length; i++) {
			str += "(";
			for(int j = 0; j < numberOfVariables; j++) {
				if(clauses[i].getLiteral(j) > 0 && j != 0) {
					str += "+";
				} 
				if(clauses[i].getLiteral(j) > 0) {
					str += "x" + clauses[i].getLiteral(j);
				}
				if(clauses[i].getLiteral(j) < 0) {
					str += "-";
				}
			}
			str += ")";
		}
		return str;
	}
	
}
