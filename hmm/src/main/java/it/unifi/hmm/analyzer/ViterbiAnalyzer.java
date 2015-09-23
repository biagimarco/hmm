package it.unifi.hmm.analyzer;

import java.util.ArrayList;
import java.util.List;

import it.unifi.hmm.HMM;
import it.unifi.hmm.Observation;
import it.unifi.hmm.State;
import it.unifi.hmm.Transition;

/**
 * @author Marco Biagi
 */
public class ViterbiAnalyzer {

	//Input
	private HMM hmm;
	private List<Observation> observations;
	//Result data
	private List<State> optimalPath= new ArrayList<State>();
	private Double optimalPathProbability;
	
	public ViterbiAnalyzer(HMM hmm, List<Observation> observations){
		this.hmm = hmm;
		this.observations = observations;
	}
	
	public void analyze(){
		//Structure init
		double[][] localMaxMatrix = new double[observations.size()][hmm.getStates().size()];
		State[][] argumentMatrix = new State[observations.size()][hmm.getStates().size()];
		List<State> hmmOrderedStates = new ArrayList<State>();
		hmmOrderedStates.addAll(hmm.getStates());
		
		//Viterbi 1: initialization
		for(int i = 0 ; i< hmmOrderedStates.size(); i++){
			State currentState = hmmOrderedStates.get(i);
			localMaxMatrix[0][i] = currentState.getInitialProbability() *currentState.getObservationsProbabilities().get(observations.get(0)); 
		}
		for(int i = 0 ; i< hmmOrderedStates.size(); i++){
			argumentMatrix[0][i] = null; //This is not useful, but only for explanation purpose
		}
		//Viterbi 2: recursion
		for (int recursionStep = 1; recursionStep < observations.size(); recursionStep++){//for each observation
			for(int i = 0 ; i< hmmOrderedStates.size(); i++){//for each state
				State currentState = hmmOrderedStates.get(i);
				double maxValue = 0;
				State prevState = null;
				for(int j = 0; j < hmmOrderedStates.size(); j++){//for each possible previous state
					Transition t = hmm.getTransitionFromStateToState(hmmOrderedStates.get(j), hmmOrderedStates.get(i));
					if(t == null)
						continue;
					double value = localMaxMatrix[recursionStep -1][j] * t.getProbability();
					if(value > maxValue){
						maxValue = value;
						prevState = hmmOrderedStates.get(j);
					}
				}
				if(currentState != null && currentState.getObservationsProbabilities() != null && currentState.getObservationsProbabilities().get(observations.get(recursionStep)) != null)
					localMaxMatrix[recursionStep][i] = maxValue *currentState.getObservationsProbabilities().get(observations.get(recursionStep));
				argumentMatrix[recursionStep][i] = prevState; 

			}
		}
		
		//Viterbi 3: Termination
		this.optimalPathProbability = findMax(localMaxMatrix[observations.size() -1]);
		State lastStateOptimalPath = hmmOrderedStates.get(findMaxArg(localMaxMatrix[observations.size() -1]));
		this.optimalPath.add(0, lastStateOptimalPath);
		
		//Viterbi 4: backtracking
		for (int recursionStep = observations.size() -1; recursionStep > 0; recursionStep--){
			State previousOptimalState = this.optimalPath.get(0);
			State currentStateOptimalPath = argumentMatrix[recursionStep][findIndexFromArray(hmmOrderedStates, previousOptimalState)];
			this.optimalPath.add(0, currentStateOptimalPath);			
		}		
	}

	public List<State> getOptimalPath() {
		return optimalPath;
	}

	public Double getOptimalPathProbability() {
		return optimalPathProbability;
	}
	
	private double findMax(double[] values){
		double max = 0;
		for (Double value : values) {
			if(value > max)
				max = value;
		}
		return max;
	}
	
	private int findMaxArg(double[] values){
		double max = 0;
		int maxArg = 0;
		for (int i = 0; i < values.length; i++) {
			if(values[i] > max){
				max = values[i];
				maxArg = i;
			}
		}
		return maxArg;
	}
	
	private int findIndexFromArray(List<State> array, State state){
		for(int i = 0 ;i < array.size(); i ++){
			State stateV = array.get(i);
			if(stateV == null)
				continue;
			if(stateV.equals(state))
				return i;
		}
		throw new IllegalArgumentException("findIndexFromArray: element not found");
	}
	
	
	
}
