package hr.fer.zemris.optjava.dz5.part2.GeneticAlgorithm;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataParser {

	private FileInputStream fstream;
	private DataInputStream in;
	private BufferedReader br;
	private int permSize;

	public DataParser(String fileName, int populationSize, int numOfVillages) throws NumberFormatException, IOException {
		this.fstream = new FileInputStream(fileName);
		this.in = new DataInputStream(fstream);
		this.br = new BufferedReader(new InputStreamReader(in));

		this.permSize = Integer.valueOf(br.readLine());
	}
	
	public int[][] getDistances() throws NumberFormatException, IOException {
		int[][] distances = new int[permSize][permSize];
		int i = 0;
		String strLine = "";
		
		do {
			if((strLine = br.readLine()).split("\\s+").length == 1) {
				continue;
			} else {
				String[] splitString = strLine.split("\\s+");
				for(int j = 0; j < permSize; j++) {
					distances[i][j] = Integer.valueOf(splitString[j]);
				}
				i++;
			}
		} while(i < permSize);
		return distances;
	}
	
	public int[][] getQuantities() throws IOException {
		int[][] quantities = new int[permSize][permSize];
		int i = 0;
		String strLine = "";
		
		do {
			if((strLine = br.readLine()).split("\\s+").length == 1) {
				continue;
			} else {
				String[] splitString = strLine.split("\\s+");
				for(int j = 0; j < permSize; j++) {
					quantities[i][j] = Integer.valueOf(splitString[j]);
				}
				i++;
			}
		} while(i < permSize);
		
		return quantities;
	}
	
	public int getPermSize() {
		return this.permSize;
	}
	
}
