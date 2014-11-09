//@author A0114573J
package edu.dynamic.dynamiz.parser;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Test program to validate Util.
 * 
 */
public class UtilTest {
	Util util = new Util();
	private List<String> expectedList;
	private List<String> testList;
	String expectedString;
	String testString;
	private String[] expectedArray;
	private String[] testArray;
	private final int N = 10;

	@SuppressWarnings("static-access")
	@Test
	public final void testRemoveEmptyStringsInList() {

		expectedList = new ArrayList<String>();
		testList = new ArrayList<String>();
		// Normal Case
		testList.add("test string");
		expectedList.add("test string");
		assertEquals(expectedList, util.removeEmptyStringsInList(testList));

		// Empty String Case
		testList.add("");
		testList.add("");
		assertEquals(expectedList, util.removeEmptyStringsInList(testList));
	}

	@SuppressWarnings("static-access")
	@Test
	public final void testRemoveEmptyStringsInArray() {
		expectedArray = new String[N];
		testArray = new String[N];

		testArray[0] = "test string";
		expectedArray[0] = "test string";

		for (int i = 1; i < N; i++) {
			testArray[i] = "";
		}
		assertEquals(expectedArray, util.removeEmptyStringsInArray(testArray));

		// Empty String Case
		testList.add("");
		testList.add("");
		assertEquals(expectedArray, util.removeEmptyStringsInArray(testArray));
	}

	@Test
	public final void testStripFirstWordString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testStripFirstWordStringString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetFirstWordString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetFirstWordStringString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testStripLeadingHyphens() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testStripLeadingAndTrailingQuotes() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testIsNumber() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testIsInteger() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testIsValidNumberRange() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetNumberListFromRange() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testEscapeString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testAddEscapeCapacityToRegex() {
		fail("Not yet implemented"); // TODO		
	}

	@Test
	public final void testToIntArray() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testConvertStringToMyDate() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testConvertJodaToMyDate() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testConvertJodaToMyDateTime() {
		fail("Not yet implemented"); // TODO
	}

}
