package hr.fer.zemris.optjava.dz7.part1;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataParser {
	
	private double[][] inputs = new double[150][4];
	private double[][] outputs = new double[150][3];
	
	public DataParser(String fileName) throws NumberFormatException, IOException {
		FileInputStream fstream = new FileInputStream(fileName);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		String strLine;
		int i = 0;
		while((strLine = br.readLine()) != null) {
			String[] parsedString = strLine.split(":");
			String[] parsedStringLeft = parsedString[0].replace("(", "").replace(")", "").split(",");
			String[] parsedStringRight = parsedString[1].replace("(", "").replace(")", "").split(",");
			
			for(int j = 0; j < parsedStringLeft.length; j++) {
				inputs[i][j] = Double.parseDouble(parsedStringLeft[j]);
			}
			for(int j = 0; j < parsedStringRight.length; j++) {
				outputs[i][j] = Double.parseDouble(parsedStringRight[j]);
			}
			i++;
		}
		br.close();
	}
	
	public int getNumberOfSamples() {
		return inputs.length;
	}
	
	public double[] getInputSample(int index) {
		return inputs[index];
	}
	
	public double[] getOutputSample(int index) {
		return outputs[index];
	}
	
}
