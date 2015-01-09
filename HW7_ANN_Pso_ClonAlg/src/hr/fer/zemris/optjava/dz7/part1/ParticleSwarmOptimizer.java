package hr.fer.zemris.optjava.dz7.part1;

public class ParticleSwarmOptimizer {
	
	public static void run(ErrorFunction function, ANN ann, String implType, int numberOfLocal) {
		
		double[][] x = new double[Constants.VEL_POP][ann.getWeightsCount()];
		double[][] v = new double[Constants.VEL_POP][ann.getWeightsCount()];
		
		double[][] pbest = new double[Constants.VEL_POP][ann.getWeightsCount()];
		double[] pbest_f = new double[Constants.VEL_POP];
		double[] gbest = new double[ann.getWeightsCount()];
		double gbest_f = 0.d;
		
		//initialize the population
		for(int i = 0; i < Constants.VEL_POP; i++) {
			for(int d = 0; d < ann.getWeightsCount(); d++) {
				x[i][d] = Math.random() * (Constants.xmax - Constants.xmin) + Constants.xmin;
				pbest[i][d] = x[i][d];
				v[i][d] = Math.random() * (Constants.vmax - Constants.vmin) + Constants.vmin;
			}
			
			pbest_f[i] = function.getValueAndEvaluate(pbest[i]);
			gbest = pbest[(int) Math.floor(Math.random()*Constants.VEL_POP)];
			gbest_f = function.getValueAndEvaluate(gbest);
		}
		
		while(Constants.iter-- > 0 && gbest_f > Constants.minError) {
			double[] error = new double[Constants.VEL_POP];
			
			for(int i = 0; i < Constants.VEL_POP; i++) {
				error[i] = function.getValueAndEvaluate(x[i]);
			}
			for(int i = 0; i < Constants.VEL_POP; i++) {
				if(error[i] < pbest_f[i]) {
					pbest_f[i] = error[i];
					pbest[i] = x[i];
				}
				if(error[i] < gbest_f) {
					gbest_f = error[i];
					gbest = x[i];
				}
			}
			System.out.println(Constants.iter);
			for(int i = 0; i < Constants.VEL_POP; i++) {
				for(int d = 0; d < ann.getWeightsCount(); d++) {
					v[i][d] += Constants.c1 * Math.random() * (pbest[i][d] - x[i][d])
							 + Constants.c2 * Math.random() * (gbest[d] - x[i][d]);
					x[i][d] += v[i][d];
				}
			}
		}
		
		System.out.println("Najbolje pronadeno rjesenje ima pogresku: " + function.getValueAndEvaluate(gbest));
		System.out.println("Broj ispravnih zakljucaka: " + function.getNumberOfSuccessful() + "/150");
	}
	
	public static void run(ErrorFunction function, ANN ann, String implType) {
		run(function, ann, implType, 0);
	}
	
}
