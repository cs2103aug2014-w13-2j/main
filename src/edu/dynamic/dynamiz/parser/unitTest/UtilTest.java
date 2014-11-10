//@author A0114573J
package edu.dynamic.dynamiz.parser.unitTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

import edu.dynamic.dynamiz.parser.Util;
import edu.dynamic.dynamiz.structure.MyDate;

/**
 * Test program to validate Util.
 * 
 */
public class UtilTest {
	Util util = new Util();
	private List<String> expectedList;
	private List<String> testList;
	private List<Integer> expectedIntList;
	private List<Integer> testIntList;
	String expectedString;
	String testString;
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
		expectedList = new ArrayList<String>();
		testArray = new String[N];

		testArray[0] = "test string";
		expectedList.add("test string");

		for (int i = 1; i < N; i++) {
			testArray[i] = "";
		}
		assertEquals(expectedList, util.removeEmptyStringsInArray(testArray));
	}

	@SuppressWarnings("static-access")
	@Test
	public final void testStripFirstWordString() {
		String str = "add hello world";
		assertEquals("hello world",util.stripFirstWord(str));
		str = "";
		assertEquals("",util.stripFirstWord(str));
	}

	@SuppressWarnings("static-access")
	@Test
	public final void testStripFirstWordStringString() {
		String str = "add hello -world";
		String deliminator = "-";
		assertEquals("-world",util.stripFirstWord(str, deliminator));
		str = "";
		assertEquals("",util.stripFirstWord(str, deliminator));
	}

	@SuppressWarnings("static-access")
	@Test
	public final void testGetFirstWordString() {
		String str = "add hello world";
		assertEquals("add",util.getFirstWord(str));
		str = "";
		assertEquals("",util.getFirstWord(str));
	}

	@SuppressWarnings("static-access")
	@Test
	public final void testGetFirstWordStringString() {
		String str = "add hello -world";
		String deliminator = "-";
		assertEquals("add hello ",util.getFirstWord(str, deliminator));
		str = "";
		assertEquals("",util.getFirstWord(str, deliminator));
	}

	@SuppressWarnings("static-access")
	@Test
	public final void testIsNumber() {
		String str = "12312";
		assertTrue(util.isNumber(str));
		str = "12asds";
		assertFalse(util.isNumber(str));
		str = "12.2";
		assertTrue(util.isNumber(str));
	}

	@SuppressWarnings("static-access")
	@Test
	public final void testIsInteger() {
		String str = "12312";
		assertTrue(util.isInteger(str));
		str = "12asds";
		assertFalse(util.isInteger(str));
		str = "12.2";
		assertFalse(util.isInteger(str));
	}

	@SuppressWarnings("static-access")
	@Test
	public final void testIsValidNumberRange() {
		String range = "12-13";
		assertTrue(util.isValidNumberRange(range));
		range = "12-12";
		assertTrue(util.isValidNumberRange(range));
		range = "12313-2";
		assertFalse(util.isValidNumberRange(range));
	}

	@SuppressWarnings("static-access")
	@Test(expected = IllegalArgumentException.class)
	public final void testGetNumberListFromRange() {
		expectedIntList = new ArrayList<Integer>();
		String range = "12-13";
		expectedIntList.add(12);
		expectedIntList.add(13);
		assertEquals(expectedIntList,util.getNumberListFromRange(range));
		
		range = "12-12";
		expectedIntList.clear();
		expectedIntList.add(12);		
		assertEquals(expectedIntList,util.getNumberListFromRange(range));
		
		range = "12313-2";
		expectedIntList.clear();
		assertEquals(expectedIntList,util.getNumberListFromRange(range));
	}

	@SuppressWarnings("static-access")
	@Test
	public final void testEscapeString() {
		String str = "hello;world";
		assertEquals("helloworld",util.escapeString(str));
		str = "hello world";
		assertEquals("hello world",util.escapeString(str));
	}

	@SuppressWarnings("static-access")
	@Test
	public final void testAddEscapeCapacityToRegex() {
		String str = "hello world";
		assertEquals("(?<!;)hello world",util.addEscapeCapacityToRegex(str));
	}

	@SuppressWarnings("static-access")
	@Test
	public final void testToIntArray() {
		int[] expectedArray = {1,1,1};
		testIntList = new ArrayList<Integer>();
		testIntList.add(1);
		testIntList.add(1);
		testIntList.add(1);

		int[] outputArray = util.toIntArray(testIntList);
		for (int i=0;i<3;i++){
			assertEquals(expectedArray[i],outputArray[i]);
		}
	}

	@SuppressWarnings("static-access")
	@Test
	public final void testConvertStringToMyDate() {
		String str = "11/12/2014";
		MyDate date = util.convertStringToMyDate(str);
		assertEquals(11,date.getDayOfMonth());
		assertEquals(12,date.getMonth());
		assertEquals(2014,date.getYear());
			
	}

	@Test
	public final void testConvertJodaToMyDate() {
		DateTime dt = new DateTime(2011,11,11,8,00);
		assertEquals("11/11/2011", Util.convertJodaToMyDate(dt).toString());
	}

	@Test
	public final void testConvertJodaToMyDateTime() {
		DateTime dt = new DateTime(2011,11,11,8,00);
		assertEquals("11/11/2011 8:00", Util.convertJodaToMyDateTime(dt).toString());		
	}

}
