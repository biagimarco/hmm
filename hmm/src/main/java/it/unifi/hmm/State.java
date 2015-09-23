package it.unifi.hmm;

import java.util.Map;

/**
 * @author Marco Biagi
 */
public class State {

	private String name;
	private double initialProbability;
	private Map<Observation, Double> observationsProbabilities;
	
	public State(String name, double initialProbability, Map<Observation, Double> observationsProbabilities) {
		this.name = name;
		this.initialProbability = initialProbability;
		this.observationsProbabilities = observationsProbabilities;
	}

	public String getName() {
		return name;
	}

	public double getInitialProbability() {
		return initialProbability;
	}

	public Map<Observation, Double> getObservationsProbabilities() {
		return observationsProbabilities;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
	
	
}
