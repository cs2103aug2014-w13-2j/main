//@author A0113855E
package edu.dynamic.dynamiz.parser.unitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.parser.ExpressionSubstitutor;

//@author A0113855E
public class ExpressionSubstitutorTest {

	@Test
	public void testSubCommonDateAbbreviation() {
		ExpressionSubstitutor substitutor = ExpressionSubstitutor.getInstance();
		
		// Test on common case
		String input = "td is a good day. tmr will be better. But yst was the best";
		assertEquals("today is a good day. tomorrow will be better. But yesterday was the best", 
						substitutor.subCommonDateAbbreviation(input));
		
		// Test on corner case where abbr is embedded
		input = "thistmr should not be subtdstuted.";
		assertEquals(input, substitutor.subCommonDateAbbreviation(input));
		
		// Test when the abbr is in uppercase
		input = "TD should be td. TmR should be tmr. yTd should be ytd";
		assertEquals("today should be today. tomorrow should be tomorrow. yesterday should be yesterday",
				substitutor.subCommonDateAbbreviation(input));
		
		// Test if the abbr is escaped
		input = ";tmr should still be ;tmr.";
		assertEquals(input, substitutor.subCommonDateAbbreviation(input));
		
		// Test other abbr.
		input = "nxt Friday";
		assertEquals("next Friday", substitutor.subCommonDateAbbreviation(input));
		
		input = "LST friday";
		assertEquals("last friday", substitutor.subCommonDateAbbreviation(input));
		
	}

}
