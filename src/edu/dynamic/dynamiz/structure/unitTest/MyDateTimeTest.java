package edu.dynamic.dynamiz.structure.unitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.dynamic.dynamiz.structure.MyDate;
import edu.dynamic.dynamiz.structure.MyDateTime;

//@author A0113855E
/**
 * Test the correctness of comparing MyDate objects (including
 * MyDate and MyDateTime)
 *
 */
public class MyDateTimeTest {

	@Test
	public void test() {
		MyDate d1 = new MyDate(1,1,2000);
		MyDate d2 = new MyDate(2,1,2000);
		MyDate dt1 = new MyDateTime(1,1,2000,12,00);
		MyDate dt2 = new MyDateTime(2,1,2000,12,00);
		MyDate dt3 = new MyDateTime(1,1,2000,11,00);
		
		
		assertTrue(dt1.compareIncludingTime(d1) > 0);
		assertTrue(dt1.compareIncludingTime(d2) < 0);
		assertTrue(dt1.compareIncludingTime(dt2) < 0);
		
		assertTrue(d1.compareIncludingTime(d2) < 0);
		assertTrue(d1.compareIncludingTime(dt1) < 0);
		assertTrue(d1.compareIncludingTime(dt2) < 0);
		
		assertTrue(d2.compareIncludingTime(d1) > 0);
		assertTrue(d2.compareIncludingTime(dt1) > 0);
		assertTrue(d2.compareIncludingTime(dt2) < 0);
		
		assertTrue(dt2.compareIncludingTime(d1) > 0);
		assertTrue(dt2.compareIncludingTime(d2) > 0);
		assertTrue(dt2.compareIncludingTime(dt1) > 0);
		
		assertTrue(dt1.compareIncludingTime(dt1) == 0);
		assertTrue(d1.compareIncludingTime(d1) == 0);
		
		assertTrue(dt1.compareIncludingTime(dt3) > 0);
	}

}
