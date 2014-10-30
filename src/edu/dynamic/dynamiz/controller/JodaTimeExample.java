package edu.dynamic.dynamiz.controller;

import org.joda.time.DateTime;

import edu.dynamic.dynamiz.structure.MyDate;

public class JodaTimeExample {
    public static void main(String[] args){
	DateTime dt = new DateTime();
	DateTime dt2 = dt.minusDays(3);
	System.out.println(dt.toString());
	MyDate dt4 = new MyDate(dt.getDayOfMonth(), dt.getMonthOfYear(), dt.getYear());
	MyDate dt3 = new MyDate(dt2.getDayOfMonth(), dt2.getMonthOfYear(), dt2.getYear());
	System.out.println(dt3);
	System.out.println(dt4.compareTo(dt3));
    }
}
