package hr.fer.zemris.optjava.dz7.part1;

public class CloneAlgorithm {

	/**
	 * CLONALG(errorFunction, n, d, beta) where:
	 * 		- errorFunction is the function that is minimized
	 * 		- n is the size of the population
	 * 		- d is the size of solutions inserted after every iteration
	 * 		- beta is the parameter that controls the number of clones
	 * 
	 * @param function
	 * @param ann
	 */
	
	public static void run(ErrorFunction function, ANN ann) {
		double[][] p = new double[Constants.n][ann.getWeightsCount()];
		double[] p_f = new double[Constants.n];
		double[][] pc = new double[Constants.n * (int)Math.floor(Constants.n)][ann.getWeightsCount()];
		double[] pc_f = new double[Constants.n * (int)Math.floor(Constants.n)];
		
		double[][] p_new = new double[Constants.d][ann.getWeightsCount()];
		
		double gbest = 0.d;
		
		ICloneOperator cloneOperator = new ProportialAffinityCloneOperator(Constants.beta);
		IMutateOperator mutateOperator = new UnifDoubleMutator(Constants.xmax - Constants.xmin);
		
		// initialize and evaluate the population
		for (int i = 0; i < Constants.n; i++) {
			for (int j = 0; j < ann.getWeightsCount(); j++) {
				p[i][j] = Math.random() * (Constants.xmax - Constants.xmin) + Constants.xmin;
			}
			gbest = function.getValueAndEvaluate(p[(int) Math.floor(Math.random() * Constants.n)]);
		}

		while (Constants.iter-- > 0 && gbest > Constants.minError) {
			double sol;
			for (int i = 0; i < Constants.n; i++) {
				if ((sol = function.getValueAndEvaluate(p[i])) < Constants.minError) {
					System.out.println("Najbolje pronadeno rjesenje ima pogresku: " + sol);
					System.out.println("Broj ispravnih zakljucaka: " + function.getNumberOfSuccessful() + "/150");
				}
			}
			p_f = evaluateSolutions(p, function);
			p = sortByFitness(p, p_f);
			pc = cloneOperator.clone(p);
			pc = mutateOperator.hypermutate(pc);
			pc_f = evaluateSolutions(pc, function);
			pc = sortByFitness(pc, pc_f);
			p = chooseBestForNextPopulation(pc);
			
			//initialize the substitutions
			for (int i = 0; i < Constants.d; i++) {
				for (int j = 0; j < ann.getWeightsCount(); j++) {
					p_new[i][j] = Math.random() * (Constants.xmax - Constants.xmin) + Constants.xmin;
				}
			}
			p = replaceWorst(p, p_new);
		}
	}
	
	
	private static double[][] sortByFitness(double[][] p, double[] p_f) {
		double[][] pop = p.clone();
		
		for(int i = 0; i < p_f.length-1; i++) {
			for(int j = i+1; j < p_f.length; j++) {
				if(p_f[i] < p_f[j]) {
					double[] temp = pop[i];
					pop[i] = pop[j];
					pop[j] = temp;
				}
			}
		}
		return pop;
	}
	
	private static double[] evaluateSolutions(double[][] pc, ErrorFunction function) {
		double[] pc_f = new double[pc.length];
		
		for(int i = 0; i < pc.length; i++) {
			pc_f[i] = function.getValueAndEvaluate(pc[i]);
		}
		return pc_f;
	}
	
	private static double[][] chooseBestForNextPopulation(double[][] pc) {
		double[][] p = new double[Constants.n][pc[0].length];
		
		for(int i = 0; i < Constants.n; i++) {
			p[i] = pc[i];
		}
		return p;
	}
	
	private static double[][] replaceWorst(double[][] p, double[][] p_new) {
		double[][] pop = p.clone();
		
		for(int i = p.length-1, j = 0; i >= p.length - p_new.length; i--, j++) {
			pop[i] = p_new[j];
		}
		return pop;
	}

}
