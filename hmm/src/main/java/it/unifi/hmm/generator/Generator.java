package it.unifi.hmm.generator;

import java.util.ArrayList;
import java.util.List;

import it.unifi.hmm.HMM;
import it.unifi.hmm.Observation;
import it.unifi.hmm.State;
import it.unifi.hmm.Transition;
import it.unifi.hmm.utils.ProbabilityUtil;

/**
 * @author Marco Biagi
 */
public class Generator {

	private HMM hmm;
	
	public Generator(HMM hmm){
		this.hmm = hmm;
	}
	
	public List<State> generateRandomStateSequence(long sequenceLength){
		List<State> generatedSequence = new ArrayList<State>();
		List<State> accessibleStates = new ArrayList<State>();
		accessibleStates.addAll(hmm.getStates());
		//Init probabilities
		List<Double> probabilities = new ArrayList<Double>();
		for (State state : accessibleStates) {
			probabilities.add(state.getInitialProbability());
		}
		
		//Evaluate sequence
		for(int i = 0; i< sequenceLength; i++){
			//Choose state
			int choosenStateIndex = ProbabilityUtil.chooseRandomElement(probabilities);
			State choosenState = accessibleStates.get(choosenStateIndex);
			generatedSequence.add(choosenState);
			//Update probability array and casualOrderedStates
			List<Transition> transitions = new ArrayList<Transition>();
			transitions.addAll(hmm.getTransitions(choosenState));
			accessibleStates.clear();
			accessibleStates.addAll(extractTargetStatesFromTransitions(transitions));
			probabilities.clear();
			probabilities.addAll(extractProbabilityFromTransitions(transitions));
		}
		
		
		return generatedSequence;
	}

	public List<Observation> generateRandomObservationSequence(List<State> states){
		List<Observation> observervationSequence = new ArrayList<Observation>();
		
		
		for (State state : states) {
			//Construct ordered observation and probabilities array
			List<Observation> validObservations = new ArrayList<Observation>();
			List<Double> probabilities = new ArrayList<Double>();
			for (Observation observation : state.getObservationsProbabilities().keySet()) {
				validObservations.add(observation);
				probabilities.add(state.getObservationsProbabilities().get(observation));
			}
			//Choose observation
			int choosenObservationIndex = ProbabilityUtil.chooseRandomElement(probabilities);
			Observation choosenObservation = validObservations.get(choosenObservationIndex);
			observervationSequence.add(choosenObservation);
		}
		
		
		return observervationSequence;
	}
	
	
	private List<State> extractTargetStatesFromTransitions(List<Transition> transitions){
		List<State> states = new ArrayList<State>();
		for (Transition transition : transitions) {
			states.add(transition.getTo());
		}
		return states;
	}
	
	private List<Double> extractProbabilityFromTransitions(List<Transition> transitions){
		List<Double> probabilities = new ArrayList<Double>();
		for (Transition transition : transitions) {
			probabilities.add(transition.getProbability());
		}
		return probabilities;
	}

}
