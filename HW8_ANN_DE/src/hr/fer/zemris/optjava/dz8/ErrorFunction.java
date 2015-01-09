package hr.fer.zemris.optjava.dz8;

public class ErrorFunction {
	
	private IANN ann;
	private DataParser data;

	private int numberOfSuccessful;
	
	public ErrorFunction(IANN ann, DataParser data) {
		this.ann = ann;
		this.data = data;
	}

	public double getValueAndEvaluate(double[] weights) {
		double errorValue = 0.d;
		numberOfSuccessful = 0;

		for(int i = 0; i < data.getNumberOfSamples(); i++) {
			double[] output = ann.calcOutputs(data.getInputSample(i), weights);
			boolean flag = true;
			
			for(int j = 0; j < Constants.IRIS_TYPES; j++) {
				errorValue += Math.pow(output[j] - data.getOutputSample(i)[j], 2.0);
				System.out.println(output[j] + " " + data.getOutputSample(i)[j]);
			
				if((output[j] < 0.5 && data.getOutputSample(i)[j] == 1) ||
						(output[j] >= 0.5 && data.getOutputSample(i)[j] == 0)) {
					flag = false;
				}
			}
			System.out.println("----------------");
			if(flag) {
				numberOfSuccessful++;
			}
		}
		return (1 / (double)data.getNumberOfSamples()) * errorValue;
	}
	
	public int getNumberOfSuccessful() {
		return numberOfSuccessful;
	}

}
