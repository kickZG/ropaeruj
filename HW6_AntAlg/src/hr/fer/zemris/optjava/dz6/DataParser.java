package hr.fer.zemris.optjava.dz6;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataParser {
	
	public static double[][] getEuclidPositions(String fileName) throws IOException {
		FileInputStream fstream = new FileInputStream(fileName);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		int numberOfCities = getNumberOfCities(fstream, in, br);
		double[][] euclidPositions = new double[numberOfCities][2];
		
		String strLine;
		int i = 0;
		
		while(br.readLine() != null) {
			strLine = br.readLine();
			System.out.println(strLine);
			if(Character.isLetter(strLine.toCharArray()[0])) {
				continue;
			} else {
				String[] parsedString = strLine.split("\\s+");
				euclidPositions[i++][0] = Integer.valueOf(parsedString[1]);
				euclidPositions[i++][1] = Integer.valueOf(parsedString[2]);
			}
		}
		
		return euclidPositions;
	}
	
	private static int getNumberOfCities(FileInputStream fstream, DataInputStream in, BufferedReader br) throws IOException {
		int numberOfCities = 0;
		String strLine;
		
		while((strLine = br.readLine()) != null) {
			if(strLine.startsWith("EOF")) {
				break;
			} else {
				numberOfCities++;
			}
		}
		
		return numberOfCities;
	}
	
}
