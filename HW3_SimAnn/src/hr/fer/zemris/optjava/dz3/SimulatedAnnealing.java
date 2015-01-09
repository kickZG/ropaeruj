package hr.fer.zemris.optjava.dz3;

public class SimulatedAnnealing<T extends SingleObjectiveSolution> implements IOptAlgorithm<T> {

	private IDecoder<T> decoder;
	private INeighborhood<T> neighborhood;
	private T startWith;
	private IFunction function;
	private ITempSchedule tempSchedule;
	private boolean minimize;
	
	public SimulatedAnnealing(IDecoder<T> decoder, INeighborhood<T> neighborhood, T startWith,
			IFunction function, ITempSchedule tempSchedule, boolean minimize) {
		this.decoder = decoder;
		this.neighborhood = neighborhood;
		this.startWith = startWith;
		this.function = function;
		this.tempSchedule = tempSchedule;
		this.minimize = minimize;
	}
	
	/**
	 * 
	 * Pseudokod simuliranog kaljenja:
	 * Generiraj početno rješenje Omega (element) OMEGA
	 * Postavi brojač za promjenu temperature na k=0
	 * Odaberi plan hlađenja tk
	 * Odaberi početnu temperaturu temp
	 * Odredi plan za Mk - broj ponavljanja petlje pri temperaturi tk
	 * Ponavljaj dok nije zadovoljen uvjet zaustavljanja
	 * 		Ponavljaj za m je od 1 do Mk
	 * 			Generiraj susjedno rješenje Omega' (element) N(Omega)
	 * 			Izračunaj DELTA(Omega, Omega') = f(Omega') - f(Omega)
	 * 			Ako je DELTA(Omega, Omega') <= 0, prihvati Omega', tj. postavi Omega <- Omega'
	 * 			Inače ako je DELTA(Omega, Omega') > 0, postavi Omega <- Omega' s vjerojatnošću exp(-DELTA(...) / tk)
	 * 		Kraj ponavljanja
	 * Kraj ponavljanja
	 * Vrati Omega kao rješenje
	 * 
	 */
	
	@Override
	public void run() {
		T currentSolution = this.startWith; //generiraj početno rješenje -> dobivam startWith izvana --- OK
		currentSolution.setValue(function.valueAt(decoder.decode(currentSolution)));	//postavi value početnog rješenja
		//brojač za promjenu temperature ??
		//odaberi plan hlađenja -> dobivam tempSchedule izvana --- OK
		//početna temperatura --> get
		//broj ponavljanja petlje pri temperaturi tk --> inner loop counter u planu hlađenja
		//broj ponavljanja vanjske --> outer loop counter u planu hlađenja
		while(tempSchedule.getOuterLoopCounter() > 0) {	//uvjet zaustavljanja vanjske petlje - TODO
			for(int i = 0; i < tempSchedule.getInnerLoopCounter(); i++) {	//uvjet zaustavljanja unutarnje petlje --- fiksno --- OK
				T neighbor = this.neighborhood.randomNeighbor(currentSolution);	//generiraj susjedno rješenje Omega' (element) N(Omega)
				neighbor.setValue(function.valueAt(decoder.decode(neighbor)));
				neighbor.setFitness(neighbor.getValue() - currentSolution.getValue());	//DELTA(Omega, Omega')
				boolean isNewSolution = false;	//flag za provjeru je li odabran susjed ili ne
				if(neighbor.getFitness() <= 0) {	//ako je DELTA(Omega, Omega') > 0
					currentSolution = neighbor;	//postavi Omega <- Omega'
					isNewSolution = true;
				} else {	//ako je DELTA(Omega, Omega') <= 0
					if(Math.random() < Math.exp(-(neighbor.getFitness() / tempSchedule.getCurrentTemperature()))) { //vjerojatnost exp(-DELTA(...) / temp)
						currentSolution = neighbor;
						isNewSolution = true;
					}
				}
				if(isNewSolution) {	//ako je odabran susjed kao novo rješenje, postavi mu vrijednost
					currentSolution.setValue(function.valueAt(decoder.decode(currentSolution)));
				}
			}
		}
		System.out.println(currentSolution.toString());	//"vrati" Omega kao rješenje
	}
	
}
