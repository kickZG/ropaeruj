package hr.fer.zemris.optjava.dz8;

import java.io.IOException;

/**
 * Main class for neurals nets "training"
 * 
 * @author kristijan
 *
 */

public class ANNTrainer {

	/**
	 * 
	 * @param args Arguments from command line:
	 * [0]		- fileName
	 * [1]		- net ("tdnn-[arh]" or "elman-[arh]")
	 * [2]			[arh] - for example 8x10x1 (net dimensions)
	 * [3]		- n (size of population)
	 * [4]		- merr (acceptable MSE)
	 * [5]		- maxiter (maximal number of iterations)
	 * @throws NumberFormatException
	 * @throws IOException
	 */

	public static void main(String[] args) throws IOException {
		DataParser dataParser = new DataParser(args[0]);
		
		
		IANN ann = null;
		if(args[1].startsWith("tdnn")) {
			ann = new TDNN();
		}
		else if(args[1].startsWith("elman")) {
			ann = new ElmanANN();
		}
	}

}
