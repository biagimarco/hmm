package it.unifi.hmm;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import it.unifi.hmm.analyzer.ViterbiAnalyzer;
import it.unifi.hmm.generator.Generator;

/**
 * @author Marco Biagi
 */
public class HMMTest{
	
	@Test
	public void viterbiTest() {

		//Define HMM structure
		HMM hmm = new HMM();
		Observation o1 = new Observation("O1");
		Observation o2 = new Observation("O2");
		Observation o3 = new Observation("O3");
		State A = new State("A", 1, ImmutableMap.of(o1, 0.0, o2, 0.5, o3, 0.5));
		State B = new State("B", 0, ImmutableMap.of(o1, 0.5, o2, 0.5, o3, 0.0));
		State C = new State("C", 0, ImmutableMap.of(o1, 0.2, o2, 0.2, o3, 0.6));
		Transition tA_B = new Transition(A, B, 0.5);
		Transition tA_C = new Transition(A, C, 0.5);
		Transition tB_C = new Transition(B, C, 1.0);
		Transition tC_B = new Transition(C, B, 0.2);
		Transition tC_A = new Transition(C, A, 0.8);
		
		hmm.addObservation(o1);
		hmm.addObservation(o2);
		hmm.addObservation(o3);
		hmm.addState(A);
		hmm.addState(B);
		hmm.addState(C);
		hmm.addTransition(tA_B);
		hmm.addTransition(tA_C);
		hmm.addTransition(tB_C);
		hmm.addTransition(tC_B);
		hmm.addTransition(tC_A);
		
		//Generate random hidden states and observations sequence
		Generator generator = new Generator(hmm);
		List<State> randomStateSequence = generator.generateRandomStateSequence(5);
		List<Observation> observedSequence = generator.generateRandomObservationSequence(randomStateSequence);
		
		System.out.println("State sequence ground truth");
		for (State state : randomStateSequence) {
			System.out.print(state.getName());
			System.out.print(";");
		}
		System.out.println("");
		System.out.println("Observed sequence");
		for (Observation observation : observedSequence) {
			System.out.print(observation.getValue());
			System.out.print(";");
		}
		
		//Launch Viterbi analyzer
		System.out.println("");
		System.out.println("Launching Viterbi analyzer");
		ViterbiAnalyzer va = new ViterbiAnalyzer(hmm, observedSequence);
		va.analyze();
		
		System.out.println("Results:");
		System.out.println("Optimal path probability: " + va.getOptimalPathProbability());
		System.out.println("Optimal path:");
		for (State state : va.getOptimalPath()) {
			System.out.print(state.getName());
			System.out.print(";");
		}
	}
}
