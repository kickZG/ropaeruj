package hr.fer.zemris.optjava.dz3;

public class Function implements IFunction {

	public Function() {
	}
	
	@Override
	public double valueAt(double[] solution) {
		double[] f = new double[20];
		
		for(int i = 0; i < 20; i++) {
			f[i] = solution[0] * SimulatedMain.inputX[i][0] + solution[1] * 
					Math.pow(SimulatedMain.inputX[i][0], 3) * SimulatedMain.inputX[i][1] +
					solution[2] * Math.exp(solution[3] * SimulatedMain.inputX[i][2]) * 
					(1 + Math.cos(solution[4] * SimulatedMain.inputX[i][3])) +
					solution[5] * SimulatedMain.inputX[i][3] * Math.pow(SimulatedMain.inputX[i][4], 2);
		}
		
		double functionValue = 0.d;

		for(int i = 0; i < 10; i++) {
			functionValue += Math.pow(f[i] - SimulatedMain.inputY[i], 2);
		}
		return functionValue;
	}
	/*
	public int getNumberOfVariables() {
		return 6;
	}
	*/

}
