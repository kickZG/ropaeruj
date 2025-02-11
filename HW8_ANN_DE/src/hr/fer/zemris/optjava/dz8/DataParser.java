package hr.fer.zemris.optjava.dz8;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataParser {

	private String fileName;
	private int[] data;
	
	public DataParser(String fileName) throws IOException {
		FileInputStream fstream = new FileInputStream(fileName);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		int numOfLines = getNumberOfLines(br);
		br = new BufferedReader(new InputStreamReader(in));
		data = new int[numOfLines];
		
		for(int i = 0; i < numOfLines; i++) {
			data[i] = Integer.valueOf(br.readLine()).intValue();
		}
		br.close();
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public int[] getData() {
		return this.data;
	}
	
	private int getNumberOfLines(BufferedReader br) throws IOException {
		int numOfLines = 0;
		while(br.readLine() != null) {
			numOfLines++;
		}
		br.close();
		
		return numOfLines;
	}
	
}
