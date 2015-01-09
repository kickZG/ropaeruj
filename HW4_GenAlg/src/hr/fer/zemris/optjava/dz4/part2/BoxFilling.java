package hr.fer.zemris.optjava.dz4.part2;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class BoxFilling {
	
	public static int inputX[];

	public static void main(String[] args) throws IOException {
		FileInputStream fstream = new FileInputStream(args[0]);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		String strLine = "";
		strLine = br.readLine();
		
		strLine = strLine.replace("[", "");
		strLine = strLine.replace("]", "");
		strLine = strLine.replace("\\s+", "");
		String[] parsedString = strLine.split(",");
		System.out.println(strLine);
		for(int j = 0; j < parsedString.length; j++) {
			System.out.println(parsedString[j]);
			inputX[j] = Integer.parseInt(parsedString[j]);
		}
		
	}

}
