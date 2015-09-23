package it.unifi.hmm;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Marco Biagi
 */
public class HMM {

	private Set<State> states = new HashSet<State>();
	private Set<Transition> transitions = new HashSet<Transition>();
	private Set<Observation> observations = new HashSet<Observation>();

	public HMM(){}

	public Set<State> getStates() {
		return states;
	}

	public Set<Transition> getTransitions() {
		return transitions;
	}
	
	public Set<Transition> getTransitions(State state) {
		Set<Transition> transitionFromState = new HashSet<Transition>();
		for (Transition transition : transitions) {
			if(transition.getFrom().equals(state))
				transitionFromState.add(transition);
		}
		return transitionFromState;
	}
	
	public void addState(State state){
		states.add(state);
	}
	
	public void addTransition(Transition transition){
		transitions.add(transition);
	}
	
	public void addObservation(Observation observation){
		observations.add(observation);
	}
	
	public Transition getTransitionFromStateToState(State from, State to){
		for (Transition transition : transitions) {
			if(transition.getFrom().equals(from) && transition.getTo().equals(to))
				return transition;
		}
		return null;
	}
	
}
