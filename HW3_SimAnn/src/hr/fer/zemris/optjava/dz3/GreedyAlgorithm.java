package hr.fer.zemris.optjava.dz3;

public class GreedyAlgorithm<T extends SingleObjectiveSolution> implements IOptAlgorithm<T> {

	private IDecoder<T> decoder;
	private INeighborhood<T> neighborhood;
	private T startWith;
	private IFunction function;
	private boolean minimize;
	
	public GreedyAlgorithm(IDecoder<T> decoder, INeighborhood<T> neighborhood, T startWith,
			IFunction function, boolean minimize) {
		this.decoder = decoder;
		this.neighborhood = neighborhood;
		this.startWith = startWith;
		this.function = function;
		this.minimize = minimize;
	}
	
	/**
	 * 
	 * Generiraj početno rješenje Omega (element) OMEGA
	 * Izračunaj vrijednost funkcije f(Omega) te dobrotu rješenja fitness(Omega)
	 * Ponavljaj dok nije zadovoljen uvjet zadustavljanja
	 * 		Generiraj susjedno rješenje Omega' (element) N(Omega)
	 * 		Izračunaj vrijednost funkcije f(Omega') te dobrotu rješenja fitness(Omega')
	 * 		Ako je fitness(Omega') > fitness(Omega) prihvati Omega', tj. postavi Omega <- Omega'
	 * Kraj ponavljanja
	 * Vrati Omega kao rješenje
	 * 
	 */
	
	@Override
	public void run() {
		T currentSolution = this.startWith; //početno rješenje je dano sa startWith
		currentSolution.setValue(this.function.valueAt(decoder.decode(currentSolution)));	//izračunaj vrijednost funkcije f(Omega)
		//currentSolution.setFitness(currentSoltion); --> imam definiran fitness kao razliku dvaju rješenja, pa ga tu nema
		int numberOfIterations = 2000;
		while(numberOfIterations-- > 0) {	// ponavljaj dok nije zadovoljen uvjet zaustavljanja
			T neighbor = this.neighborhood.randomNeighbor(currentSolution);	//generiraj slučajno rješenje Omega'
			neighbor.setValue(this.function.valueAt(decoder.decode(neighbor)));	//izračunaj vrijednost funkcije f(Omega')
			neighbor.setFitness(neighbor.getValue() - currentSolution.getValue());	//izračunaj dobrotu susjeda
			if(neighbor.getFitness() > 0) {
				currentSolution = neighbor;	//ako je susjed bolji, postavi njega kao novo rješenje
			}
		}
		System.out.println(currentSolution.toString());	// vrati Omega kao rješenje
	}
	
}
