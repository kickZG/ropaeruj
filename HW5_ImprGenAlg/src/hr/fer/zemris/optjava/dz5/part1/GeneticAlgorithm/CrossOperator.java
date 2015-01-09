package hr.fer.zemris.optjava.dz5.part1.GeneticAlgorithm;

import java.util.Arrays;

public class CrossOperator {

	private int numOfCuts;
	
	public CrossOperator(int numOfCuts) {
		this.numOfCuts = numOfCuts;
	}
	
	public boolean[] nCutCross(boolean[][] parents) {
		boolean[] child = new boolean[parents[0].length];
		boolean[][] children = new boolean[2][parents[0].length];
		
		//generate cut places
		int[] randomNumbers = new int[this.numOfCuts];
		int j = 0;
		do {
			boolean taken = false;
			int index = (int) Math.round(Math.random() * parents[0].length - 1) + 1;
			for(int i = 0; i < numOfCuts; i++) {
				//if that index is already taken
				if(index == randomNumbers[i]) {
					taken = true;
					break;
				}
			}
			if(!taken) j++;
		} while(j < numOfCuts);
		
		//sort the indexes
		Arrays.sort(randomNumbers);
		
		//first block
		for(int i = 0; i < randomNumbers[0]; i++) {
			children[0][i] = parents[0][i];
			children[1][i] = parents[1][i];
		}
		
		for (int i = 0; i < numOfCuts - 1; i++) {
			for (int k = randomNumbers[i]; k < randomNumbers[i + 1]; k++) {
				if (i % 2 == 0) {
					children[0][k] = parents[0][k];
					children[1][k] = parents[1][k];
				} else {
					children[1][k] = parents[0][k];
					children[0][k] = parents[1][k];
				}
			}
		}
		//last block
		for(int i = randomNumbers[randomNumbers.length-1]; i < parents[0].length; i++) {
			if(i % 2 == 0) {
				children[1][i] = parents[0][i];
				children[0][i] = parents[1][i];
			} else {
				children[0][i] = parents[0][i];
				children[1][i] = parents[1][i];
			}
		}
		child = getBetterChild(children);
		
		return child;
	}
	
	public boolean[] uniformCross(boolean[][] parents) {
		boolean[] child = new boolean[parents[0].length];
		boolean[][] children = new boolean[2][parents[0].length];
		
		//generate random vector R
		boolean[] R = new boolean[parents[0].length];
		for(int i = 0; i < parents[0].length; i++) {
			if(Math.random() > 0.5) {
				R[i] = true;
			} else {
				R[i] = false;
			}
		}
		
		for(int i = 0; i < parents[0].length; i++) {
			if(parents[0][i] && parents[1][i] || (R[i] && (parents[0][i] != parents[1][i]))) {
				children[0][i] = true;
			} else {
				children[0][i] = false;
			}
			if(parents[0][i] && parents[1][i] || (!R[i] && (parents[0][i] != parents[1][i]))) {
				children[1][i] = true;
			} else {
				children[1][i] = false;
			}
		}
		child = getBetterChild(children);
		
		return child;
	}
	
	private boolean[] getBetterChild(boolean[][] children) {
		FitnessFunction function = new FitnessFunction();
		if(function.getFitness(children[0]) > function.getFitness(children[1])) {
			return children[0];
		} else {
			return children[1];
		}
	}
	
}
