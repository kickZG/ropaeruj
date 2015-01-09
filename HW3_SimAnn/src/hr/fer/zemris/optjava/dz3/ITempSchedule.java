package hr.fer.zemris.optjava.dz3;

public interface ITempSchedule {

	public double getNextTemperature();
	public double getCurrentTemperature();
	public int getInnerLoopCounter();
	public int getOuterLoopCounter();
	
}
