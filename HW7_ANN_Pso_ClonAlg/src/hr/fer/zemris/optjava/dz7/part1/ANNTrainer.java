package hr.fer.zemris.optjava.dz7.part1;

import java.io.IOException;

public class ANNTrainer {

	public static void main(String[] args) throws NumberFormatException, IOException {
		DataParser parser = new DataParser(args[0]);
		System.out.println("Imamo " + parser.getNumberOfSamples() + " uzoraka za ucenje.");
		ANN ann = new ANN(
				new int[] {4,5,3,3},
				new ITransferFunction[] {
						new SigmoidTransferFunction(),
						new SigmoidTransferFunction(),
						new SigmoidTransferFunction()
				});
		ErrorFunction function = new ErrorFunction(ann, parser);
		
		if(args[1].equals("pso-a")) {
			ParticleSwarmOptimizer.run(function, 
					ann, 
					new String("a"));
		}
		else if(args[1].startsWith("pso-b")) {
			ParticleSwarmOptimizer.run(function, 
					ann, 
					new String("b"),
					Integer.valueOf(args[1].split("-")[2]));
		}
		else if(args[1].equals("clonalg")) {
			CloneAlgorithm.run(function, ann);
		}
	}
	
}
