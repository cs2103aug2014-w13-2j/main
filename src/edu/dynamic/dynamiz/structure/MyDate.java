package edu.dynamic.dynamiz.structure;

import java.util.Calendar;
import java.util.Date;

/**
 * Defines the date in the format DD/MM/YYYY.
 * @author zixian
 */
public class MyDate implements Comparable<MyDate>{
    //Default date values.
    protected static final int DEFAULT_DAY = 1;
    protected static final int DEFAULT_MONTH = 1;
    protected static final int DEFAULT_YEAR = 1970;
    
    //String format for set date
    protected static final String FORMAT_DATE = "%1$d/%2$d/%3$d";
    public static final String REGEX_DATE = "[0-9]{1,2}/[0-9]{1,2}/[0-9]{1,4}";
    
    //The main data members of this class.
    private int year, month, date;
    
    /**
     * Creates and initialises this Date object to 1 January 1970.
     */
    public MyDate(){
	this(DEFAULT_DAY, DEFAULT_MONTH, DEFAULT_YEAR);
    }
    
    //Copy constructor
    public MyDate(MyDate date){
	this(date.getDayOfMonth(), date.getMonth(), date.getYear());
    }
    
    /**
     * Creates a Date object with the specified date, month and year.
     * @throws IllegalArgumentException if any of the values cause the date to be incorrect
     * 		on the actual calendar.
     */
    public MyDate(int date, int month, int year) throws IllegalArgumentException{
	if(!isValidDate(date, month, year)){
	    throw new IllegalArgumentException();
	}
	setDate(date, month, year);
    }
    
    public MyDate(Date date) {
    	assert date != null;
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	
    	int dd = calendar.get(Calendar.DAY_OF_MONTH);
    	int mm = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH starts from 0
     	int yyyy = calendar.get(Calendar.YEAR);
    	
    	setDate(dd, mm, yyyy);
	}

	/**
     * Creates a new Date instance from the given date string.
     * @param dateString The string representation of the date to create.
     * @return The Date instance who's toString() method equals dateString.
     */
    public static MyDate makeDate(String dateString){
	assert dateString.matches(REGEX_DATE);
	
	String[] temp = dateString.split("/");
	int day = Integer.parseInt(temp[0]);
	int month = Integer.parseInt(temp[1]);
	int year = Integer.parseInt(temp[2]);
	return new MyDate(day, month, year);
    }

    /**
     * Gets the day of the month of this date object.
     * @returns The day in the month of this Date object.
     */
    public int getDayOfMonth(){
	return date;
    }
    
    /**
     * Gets the month of this date object.
     * @return The month of this Date object.
     */
    public int getMonth(){
	return month;
    }
    
    /**
     * Gets the year of this date object.
     * @return The year of this Date object.
     */
    public int getYear(){
	return year;
    }
    
    /**
     * Checks if the given date is valid.
     * @return True if the date is valid in the calendar and false otherwise.
     */
    public static boolean isValidDate(int date, int month, int year){
	boolean isValidYear = year>=0;
	boolean isValidMonth = (month>0 && month<13);
	boolean isValidDate = date>0;
	if(month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12){
	    isValidDate&=(date<32);
	} else if(month==4 || month==6 || month==9 || month==11){
	    isValidDate&=(date<31);
	} else if(month==2){
	    boolean isLeapYear = (year%400==0 || (year%4==0 && year%100!=0));
	    if(isLeapYear){
		isValidDate&=(date<30);
	    } else{
		isValidDate&=(date<29);
	    }
	} else{
	    isValidDate = false;
	}
	return isValidYear && isValidMonth && isValidDate;
    }
    
    /**
     * Returns the String representation of this Date object for printing purpose.
     * Format is: DD/MM/YYYY.
     * @return String representation of this Date object.
     */
    public String toString(){
	String output = String.format(FORMAT_DATE, getDayOfMonth(), getMonth(), getYear());
	return output;
    }
    
    /**
     * Returns String representation of this Date for writing to file.
     * Format: 
     * @return String representation of this Date object for file IO.
     */
    public String toFileString(){
	return toString();
    }
    
    /**
     * Sets this to the given date without a specified time.
     * @throws IllegalArgumentException if any of the parameters cause the new date value to be incorrect
     * 		in the actual calendar.
     */
    public void setDate(int date, int month, int year){
	if(isValidDate(date, month, year)){
	    setDayOfMonth(date);
		setMonth(month);
		setYear(year);
	}
    }

    protected void setDayOfMonth(int date) {
	this.date = date;
    }
    
    protected void setMonth(int month){
	this.month = month;
    }
    
    protected void setYear(int year){
	this.year = year;
    }

    @Override
    /**
     * Compares which of the 2 Date objects comes later. If date2 is a subclass of Date,
     * date2's compareTo method will be used instead but the outcome is not reversed.
     * @return 0 if the 2 dates have same date, positive
     * 		integer if this come later than date2, negative integer
     * 		if this comes earlier than date2.
     */
    public int compareTo(MyDate date2){
	if(year!=date2.getYear()){
	    return year-date2.getYear();
	} else if(month!=date2.getMonth()){
	    return month-date2.getMonth();
	} else{
	    return date-date2.getDayOfMonth();
	}
    }
    
    @Override
    public boolean equals(Object obj){
	if(obj instanceof MyDate){
	    MyDate temp = (MyDate)obj;
	    return (date==temp.getDayOfMonth()) && (month==temp.getMonth()) && (year==temp.getYear());
	}
	return false;
    }
}
