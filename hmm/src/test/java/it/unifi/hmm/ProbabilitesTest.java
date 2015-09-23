package it.unifi.hmm;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import it.unifi.hmm.utils.ProbabilityUtil;

/**
 * @author Marco Biagi
 */
public class ProbabilitesTest {

	
	@Test
	public void chooseRandomElementTest(){
		List<Double> probabilities = Arrays.asList(0.1,0.8,0.1);
		int chosedIndex = ProbabilityUtil.chooseRandomElement(probabilities);
		
		System.out.println(chosedIndex);
	}
}
