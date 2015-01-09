package hr.fer.zemris.optjava.dz3;

public interface IDecoder<T> {

	public double[] decode(T object);
	public void decode(T object, double[] array);
	
}
