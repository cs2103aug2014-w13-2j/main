package edu.dynamic.dynamiz.structure;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test the correctness of comparing MyDate objects (including
 * MyDate and MyDateTime)
 * @author nhan
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
		
		
		assertTrue(dt1.compare(d1) > 0);
		assertTrue(dt1.compare(d2) < 0);
		assertTrue(dt1.compare(dt2) < 0);
		
		assertTrue(d1.compare(d2) < 0);
		assertTrue(d1.compare(dt1) < 0);
		assertTrue(d1.compare(dt2) < 0);
		
		assertTrue(d2.compare(d1) > 0);
		assertTrue(d2.compare(dt1) > 0);
		assertTrue(d2.compare(dt2) < 0);
		
		assertTrue(dt2.compare(d1) > 0);
		assertTrue(dt2.compare(d2) > 0);
		assertTrue(dt2.compare(dt1) > 0);
		
		assertTrue(dt1.compare(dt1) == 0);
		assertTrue(d1.compare(d1) == 0);
		
		assertTrue(dt1.compare(dt3) > 0);
	}

}
