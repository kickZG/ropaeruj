package hr.fer.zemris.trisat;

public class SATFormulaStats {

	private static double percentageConstantUp = 0.01;
	private static double percentageConstantDown = 0.1;
	
	private SATFormula formula;
	private double[] post;
	private int numberOfSatisfied;
	
	public SATFormulaStats(SATFormula formula) {
		this.formula = formula;
		post = new double[formula.getNumberOfClauses()];
		for(int i = 0; i < formula.getNumberOfClauses(); i++) {
			post[i] = 0.d;
		}
		this.numberOfSatisfied = 0;
	}
	
	public void setAssignment(BitVector assignment, boolean updatePercentage) {
		this.numberOfSatisfied = 0;
		for(int i = 0; i < this.formula.getNumberOfClauses(); i++) {
			if(formula.getClause(i).isSatisfied(assignment)) {
				post[i] = (1 - post[i]) * percentageConstantUp;
				this.numberOfSatisfied++;
			} else {
				post[i] += (0 - post[i]) * percentageConstantDown;
			}
		}
	}
	
	public int getNumberOfSatisfied(BitVector assignment) {
		return this.numberOfSatisfied;
	}
	
	public boolean isSatisfied() {
		return numberOfSatisfied == formula.getNumberOfClauses();
	}
	
	public double getPercentage(int index) {
		return this.post[index];
	}
	
	public double getPost(int index) {
		return this.post[index];
	}
	
	public void reset() {
		post = new double[formula.getNumberOfClauses()];
		for(int i = 0; i < formula.getNumberOfClauses(); i++) {
			post[i] = 0.d;
		}
	}
	
}
