package hr.fer.zemris.optjava.dz7.part1;

public final class Constants {

	public static double xmin = -1.d;
	public static double xmax = 1.d;
	
	//PSO constants
	public static int IRIS_TYPES = 3;
	public static int VEL_POP = 50;
	public static double vmin = xmin / 10;
	public static double vmax = xmax / 10;
	public static int iter = 150;
	public static double c1 = 2.02d;	//individuality factor
	public static double c2 = 2.d;		//social component
	public static double minError = (xmax - xmin) / 10;
	
	//CLONALG constants
	public static int n = 100;
	public static int d = 10;
	public static double beta = 4.0d;	//TODO - initialize
	
}
