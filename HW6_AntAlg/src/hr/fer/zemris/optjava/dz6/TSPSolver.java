package hr.fer.zemris.optjava.dz6;

import java.io.IOException;

public class TSPSolver {

	private static int iter;
	private static int m;
	private static double ro = 0.2;
	private static double alpha = 1.d;
	private static double beta = 2.5;
	private static double a;
	private static int k;
	
	private static double upperPheromoneBound = 2.d;
	private static double lowerPheromoneBound = 0.5d;

	private static double[][] pheromoneValues;
	private static double[][] heurValues; 
	
	
	public static void main(String[] args) throws IOException {
		double[][] euclidPositions = DataParser.getEuclidPositions(args[0]);
		k = Integer.valueOf(args[1]);
		m = Integer.valueOf(args[2]);
		iter = Integer.valueOf(args[3]);
		
		int numberOfCities = euclidPositions[0].length;
		a = (double)(numberOfCities / k);
		
		double[][] distances = calculateDistances(euclidPositions);
		heurValues = calculateHeuristicValues(distances);
		pheromoneValues = initializePheromoneValues(distances);
		int[][] nearestCitiesLists = calculateNearestCities(distances);
		
		RouletteWheel rouletteWheel = new RouletteWheel();
		
		while(iter-- > 0) {
			//repeat for every ant
			double globalShortestPath = calculateNearestNeighborDistance(numberOfCities, distances) * 100;
			double[] antsDistances = new double[m];
			int[][] currentDirections = new int[m][numberOfCities];
			int[] bestDirections = new int[numberOfCities];
			int stagnationCounter = 0;
			
			for(int i = 0; i < m; i++) {
				//create solution
				int currentCity = (int)Math.round(Math.random() * numberOfCities);
				
				int[] localNearestCitiesList = nearestCitiesLists[i];
				localNearestCitiesList[currentCity] = -1;
				int[] otherCitiesList = getOtherCitiesIndexes(localNearestCitiesList, numberOfCities);
				
				int kCounter = k;
				int numberOfVisited = 0;
				
				//TODO - fix this while and for (merge into one loop)
				while(kCounter-- > 0) {
					//choose from the list of k nearest cities
					int nextCity = rouletteWheel.chooseCity(alpha, pheromoneValues, heurValues, localNearestCitiesList);
					
					currentDirections[i][numberOfVisited++] = nextCity;
					antsDistances[i] += distances[currentCity][nextCity];
					currentCity = nextCity;
					localNearestCitiesList[nextCity] = -1;	//mark as visited
				}
				//visit the rest of the cities
				for(int j = 0; j < numberOfCities - k; j++) {
					int nextCity = rouletteWheel.chooseCity(alpha, pheromoneValues, heurValues, otherCitiesList);
					
					currentDirections[i][numberOfVisited++] = nextCity;
					antsDistances[i] += distances[currentCity][nextCity];
					currentCity = nextCity;
				}
			}
			//decrease pheromone trail
			decreasePheromoneTrail(ro);
			
			//update pheromone trail ("use" only the best ant)
			double currentShortestPath = antsDistances[0];
			int bestAnt = 0;
			for(int i = 0; i < m; i++) {
				if(antsDistances[i] < currentShortestPath) {
					currentShortestPath = antsDistances[i];
					bestAnt = i;
				}
			}
			
			if(currentShortestPath < globalShortestPath) {
				globalShortestPath = currentShortestPath;
				bestDirections = currentDirections[bestAnt];
				stagnationCounter = 0;
			} else {
				if(stagnationCounter++ >= 50) {
					stagnationCounter = 0;
					upperPheromoneBound = 1 / (ro * globalShortestPath);
					lowerPheromoneBound /= a;
				}
			}
			
			//TODO - modify this strategy
			if(Math.random() > 0.5) {
				for (int i = 0; i < numberOfCities - 1; i++) {
					if((pheromoneValues[currentDirections[bestAnt][i]][currentDirections[bestAnt][i+1]] += 
							1 / distances[currentDirections[bestAnt][i]][currentDirections[bestAnt][i+1]]) > upperPheromoneBound) {
						pheromoneValues[currentDirections[bestAnt][i]][currentDirections[bestAnt][i+1]] = upperPheromoneBound;
					}
				}
			} else {
				for (int i = 0; i < numberOfCities - 1; i++) {
					if((pheromoneValues[bestDirections[i]][bestDirections[i+1]] += 
							1 / distances[bestDirections[i]][bestDirections[i+1]]) > upperPheromoneBound) {
						pheromoneValues[bestDirections[i]][bestDirections[i+1]] = upperPheromoneBound;
					} 
				}
			}
		}
	}
	
	
	private static double[][] initializePheromoneValues(double[][] distances) {
		int numberOfCities = distances.length;
		double[][] pheromoneValues = new double[numberOfCities][numberOfCities];
		
		double[] nn = new double[numberOfCities];
		for(int i = 0; i < numberOfCities; i++) {
			if(i == 0) nn[i] = distances[i][1];
			else nn[i] = distances[i][0];
			for(int j = 0; j < numberOfCities; j++) {
				if(i != j && distances[i][j] < nn[i]) {
					nn[i] = distances[i][j];
				}
			}
		}
		
		for(int i = 0; i < numberOfCities; i++) {
			for(int j = 0; j < numberOfCities; j++) {
				if(i == j) {
					pheromoneValues[i][j] = 0;
				} else {
					pheromoneValues[i][j] = m / calculateNearestNeighborDistance(numberOfCities, distances);
				}
			}
		}
		return pheromoneValues;
	}
	
	private static double calculateNearestNeighborDistance(int numberOfCities, double[][] distances) {
		return distances[0][1] * numberOfCities;	//HAX -> real implementation is too expensive
	}
	
	private static void decreasePheromoneTrail(double ro) {
		for(int i = 0; i < pheromoneValues.length; i++) {
			for(int j = 0; j < pheromoneValues.length; j++) {
				if(i != j) {
					if((pheromoneValues[i][j] -= ro * pheromoneValues[i][j]) < lowerPheromoneBound) {
						pheromoneValues[i][j] = lowerPheromoneBound;
					}
				}
			}
		}
	}
	
	private static double[][] calculateDistances(double[][] p) {
		int length = p.length;
		double[][] distances = new double[length][length];
		
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < length; j++) {
				if(i == j) {
					distances[i][j] = 0;
				} else {
					distances[i][j] = Math.sqrt(Math.pow(p[i][0] - p[j][0], 2) + Math.pow(p[i][1] - p[j][1], 2));
				}
			}
		}
		return distances;
	}
	
	private static double[][] calculateHeuristicValues(double[][] d) {
		int length = d.length;
		double[][] heurValues = new double[length][length];
		
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < length; j++) {
				if(i == j) {
					heurValues[i][j] = 0;
				} else {
					heurValues[i][j] = Math.pow(1 / d[i][j], beta);
				}
			}
		}
		return heurValues;
	}

	private static int[][] calculateNearestCities(double[][] distances) {
		int[][] nearestCitiesLists = new int[distances.length][k];
		
		//do for every city
		for(int i = 0; i < distances.length; i++) {
			int[] indexes = new int[distances.length];
			double[] d = distances[i].clone();
			
			//initialize indexes of cities (0, 1, 2, 3, ...)
			for(int a = 0; a < d.length; a++) {
				indexes[a] = a;
			}
			//sort by distance
			for(int l = 0; l < d.length-1; l++) {
				for(int n = 1; n < d.length; n++) {
					if(d[l] > d[n]) {
						int temp = indexes[l];
						indexes[l] = indexes[n];
						indexes[n] = temp;
					}
				}
			}
			//choose k best distances (nearest cities)
			for(int j = 0; j < k; j++) {
				nearestCitiesLists[i][j] = indexes[j];
			}
			//sort the list
			for(int j = 0; j < k-1; j++) {
				for(int l = 1; l < k; l++) {
					if(nearestCitiesLists[i][j] > nearestCitiesLists[i][l]) {
						int temp = nearestCitiesLists[i][j];
						nearestCitiesLists[i][j] = nearestCitiesLists[i][l];
						nearestCitiesLists[i][l] = temp;
					}
				}
			}
		}
		return nearestCitiesLists;
	}
	
	private static int[] getOtherCitiesIndexes(int[] kCitiesList, int numberOfCities) {
		int[] otherCitiesList = new int[numberOfCities - k];
		
		//can this be better (this way some values are always not used)
		for(int i = 0, j = 0, l = 0; i < numberOfCities; i++) {
			if(i != kCitiesList[j]) {
				otherCitiesList[l++] = i;
			} else {
				j++;
			}
		}
		return otherCitiesList;
	}

}
