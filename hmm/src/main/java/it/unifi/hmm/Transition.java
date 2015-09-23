package it.unifi.hmm;

/**
 * @author Marco Biagi
 */
public class Transition {

	private State from;
	private State to;
	private double probability;

	public Transition(State from, State to, double probability) {
		super();
		this.from = from;
		this.to = to;
		this.probability = probability;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public State getFrom() {
		return from;
	}

	public void setFrom(State from) {
		this.from = from;
	}

	public State getTo() {
		return to;
	}

	public void setTo(State to) {
		this.to = to;
	}
	
	
	
}
