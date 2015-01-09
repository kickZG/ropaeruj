package hr.fer.zemris.optjava.dz7.part1;

public class ANN {

	private int[] layers;
	private ITransferFunction[] functionList;
	
	/**
	 * Neural network "architecture" (weights array):
	 * w01, w11, w21, w31, w41, w02, w12, w22, w32, w42, ...
	 * where w(xy), x != 0, is the weight on connection from neuron x to neuron y
	 * (neuron x is, for example, from first layer and y from second) and
	 * w(0y) is a free free weight of a neuron y (from layer two in this example).
	 * 
	 * @param layers
	 * @param functionList
	 */
	
	public ANN(int[] layers, ITransferFunction[] functionList) {
		this.layers = layers;
		this.functionList = functionList;
	}
	
	public int getWeightsCount() {
		int weightsCount = 0;
		
		for(int i = 0; i < layers.length - 1; i++) {
			weightsCount += (layers[i] + 1) * layers[i+1];
		}
		return weightsCount;
	}
	
	public double[] calcOutputs(double[] inputs, double[] weights) {
		double[] localInput = new double[layers[0]+1];
		
		localInput[0] = 1.d;
		for(int j = 1; j < localInput.length; j++) {
			localInput[j] = inputs[j-1];
		}
		double[] localOutput = new double[layers[1]];
		
		for(int i = 0; i < layers.length -1; i++) {
			localOutput = new double[layers[i+1]];
			
			for(int j = 0; j < layers[i+1]; j++) {
				double net = 0.d;
				for(int k = 0; k < layers[i]+1; k++) {
					net += localInput[k] * weights[i*(layers[i]+1)*layers[i+1] + j*k + k];
				}
				localOutput[j] = 1 / (1 + Math.exp(-net));
			}
			
			localInput = new double[localOutput.length+1];
			localInput[0] = 1.d;
			for(int j = 1; j < localOutput.length+1; j++) {
				localInput[j] = localOutput[j-1];
			}
		}
		return localOutput;
	}
	
}
