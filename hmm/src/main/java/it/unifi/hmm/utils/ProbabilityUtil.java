package it.unifi.hmm.utils;

import java.util.List;

/**
 * @author Marco Biagi
 */
public class ProbabilityUtil {

	/**
	 * Allow to choose a random value weighted by its probabilities  
	 */
	public static int chooseRandomElement(List<Double> probabilities){
		if(probabilities.size() == 0)
			return -1;
		
		double cumulative = 0;
		
		double randomValue = Math.random();
		for(int i=0; i< probabilities.size() ; i ++){
			cumulative+= probabilities.get(i);
			if(randomValue < cumulative)
				return i;
		}
		return probabilities.size();
	}
	
}
