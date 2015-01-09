package hr.fer.zemris.optjava.dz6;

public class RouletteWheel {

	public int chooseCity(double alpha, double[][] pheromoneValues, double[][] heurValues, int[] citiesList) {
		int numberOfSolutions = 0;
		double[] connectionValue = new double[citiesList.length];
		double sum = 0.d;
		
		for(int i = 0, j = 0; i < citiesList.length-1; i++) {
			if(citiesList[i] != -1) {
				connectionValue[j++] = Math.pow(pheromoneValues[citiesList[i]][citiesList[i+1]], alpha) *
						heurValues[citiesList[i]][citiesList[i+1]]; //heurValues are already power beta
				sum += connectionValue[j];
				numberOfSolutions++;
			}
		}
		double[] p = new double[numberOfSolutions];
		
		for(int i = 0; i < numberOfSolutions; i++) {
			p[i] = connectionValue[i] / sum;
		}
		
		int index = 0;
		double r = Math.random();
		double rwSum = 0.d;
		for(int i = 0; i < numberOfSolutions; i++) {
			if((rwSum += p[i]) >= r) {
				index = i;
				break;
			}
		}
		
		return index;
	}
	
}
