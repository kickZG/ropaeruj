package hr.fer.zemris.optjava.dz7.part1;

import java.io.IOException;

/**
 * Not exactly the JUnit test, because of the args that we easily only in the main method.
 * Usage: testing the configuration of the neural network.
 * 
 * @author kristijan
 *
 */

public class ANNTester {
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		DataParser parser = new DataParser(args[0]);
		System.out.println("Imamo " + parser.getNumberOfSamples() + " uzoraka za ucenje.");
		ANN ann = new ANN(
				new int[] {4,3,3},
				new ITransferFunction[] {
						new SigmoidTransferFunction(),
						new SigmoidTransferFunction(),
						new SigmoidTransferFunction()
				});
		ErrorFunction function = new ErrorFunction(ann, parser);
		
		//sample test
		double[] weights = new double[ann.getWeightsCount()];
		//initial weight values
		double weightValue = -0.2;
		//initialize the solution
		for(int d = 0; d < ann.getWeightsCount(); d++) {
			weights[d] = weightValue;
		}
		
		System.out.println(function.getValueAndEvaluate(weights));
	}

}
