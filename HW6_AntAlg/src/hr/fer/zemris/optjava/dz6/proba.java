package hr.fer.zemris.optjava.dz6;

public class proba {

	public static void main(String[] args) {
		int probaArray[][] = new int[3][5];
		for(int i = 0; i < probaArray.length; i++) {
			for(int j = 0; j < probaArray[0].length; j++) {
				probaArray[i][j] = i * j + j;
			}
		}
		int[] newProbaArray = probaArray[0].clone();
		for(int i = 0; i < newProbaArray.length; i++) {
			System.out.println(newProbaArray[i]);
		}
	}

}
