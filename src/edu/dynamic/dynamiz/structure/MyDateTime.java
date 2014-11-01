package edu.dynamic.dynamiz.structure;

import java.util.Calendar;
import java.util.Date;

/**
 * Defines an object containing both date and time. Time is defined using 24-hour clock.
 * 
 * Constructor
 * MyDateTime()	//Creates a new instance of MyDateTime using the default date and time values.
 * MyDateTime(int date, int month, int year)	//Creates a new date instance.
 * MyDateTime(int date, int month, int year, int hour, int minute)	//Creates a new date and time instance.
 * MyDateTime(MyDate date)	//Downcasting constructor for MyDate objects.
 * MyDateTime(Date date)	//Creates a MyDateTime instance from the given Date object for storage purpose.
 * 
 * Public Methods
 * int getHour()	//Gets the hour field of this date time object.
 * int getMinute()	//Gets the minute field of this date time object.
 * static boolean isValidTime(int hour, int minute)	//Checks if the given time is valid.
 * static MyDateTime makeDateTime(String dateString)	//Creates a new MydateTime object from the given date and time string.
 * void setDateTime(int date, int month, int year, int hour, int minute)	//Sets the new value of date and time.
 * void setTime(int hour, int minute)	//Sets the time of this date time object.
 * String toString()	//Gets the String rerpesentation of this date time object.
 * 
 * @author zixian
 */
public class MyDateTime extends MyDate {
    //The default time values
    private static final int DEFAULT_HOUR = 0;
    private static final int DEFAULT_MINUTE = 0;
    
    //The string format for time.
    private static final String FORMAT_TIME = "%1$d:%2$02d";
    public static final String REGEX_DATETIME = "[0-9]{1,2}/[0-9]{1,2}/[0-9]{1,4} [0-9]{1,2}:[0-9]{1,2}";
    
    //Error message
    private static final String MSG_INVALIDDATETIME = "Invalid date/time.";
    private static final String MSG_INVALIDTIME = "Invalid time.";
    
    //The main additional data members
    private int hour, minute;
    
    /**
     * Creates a new DateTime object with the default date and time.
     */
    public MyDateTime(){
	this(DEFAULT_DAY, DEFAULT_MONTH, DEFAULT_YEAR, DEFAULT_HOUR, DEFAULT_MINUTE);
    }
    
    /**
     * Downcast constructor for Date object and copy constructor for DateTime object.
     * @param date The MyDate object to be downcasted.
     */
    public MyDateTime(MyDate date){
	this(date.getDayOfMonth(), date.getMonth(), date.getYear());
	if(date instanceof MyDateTime){
	    MyDateTime temp = (MyDateTime)date;
	    setTime(temp.getHour(), temp.getMinute());
	}
    }
    
    /**
     * Creates a new DateTime object with the specified date, using the default time.
     * @param date The day of the month.
     * @param The month of the year.
     * @param year The year.
     * @throws IllegalArgumentException if the given date is invalid on the calendar.
     */
    public MyDateTime(int date, int month, int year) throws IllegalArgumentException{
	this(date, month, year, DEFAULT_HOUR, DEFAULT_MINUTE);
    }
    
    /**
     * Creates a new DateTime object with the specified date and time details.
     * @param date The day of the month.
     * @param month The month of the year.
     * @param year The year.
     * @param hour The hour of the day in 24-hour clock.
     * @param minute The minute.
     * @throws IllegalArgumentException if the date and time is invalid on the calendar of 24-hour clock.
     */
    public MyDateTime(int date, int month, int year, int hour, int minute) throws IllegalArgumentException{
	setDateTime(date, month, year, hour, minute);
    }
    
    /**
     * Creates a new DateTime object with the given java.util.Date
     * @param date the given Date to be converted into MyDateTime
     */
    public MyDateTime(Date date) {
    	assert date != null;
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	
    	int dd = calendar.get(Calendar.DAY_OF_MONTH);
    	int mm = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH starts from 0
     	int yyyy = calendar.get(Calendar.YEAR);
    	int hour = calendar.get(Calendar.HOUR_OF_DAY);
    	int min = calendar.get(Calendar.MINUTE);
    	
    	setDateTime(dd, mm, yyyy, hour, min);
    }
    
    /**
     * Creates a DateTime instance from the given date string.
     * @param dateString The string representation of the DateTiem object to make.
     * @return A DateTime instance such that calling toString().equals(dateString) returns true.
     * @throws IllegalArgumentException if either the date and/or the time is invalid.
     */
    public static MyDateTime makeDateTime(String dateString){
	assert dateString.matches(REGEX_DATETIME);
	
	String[] dateAndTime = dateString.split(" ");
	String[] dateArray = dateAndTime[0].split("/");
	String[] timeArray = dateAndTime[1].split(":");
	int day = Integer.parseInt(dateArray[0]);
	int month = Integer.parseInt(dateArray[1]);
	int year = Integer.parseInt(dateArray[2]);
	int hour = Integer.parseInt(timeArray[0]);
	int minute = Integer.parseInt(timeArray[1]);
	return new MyDateTime(day, month, year, hour, minute);
    }
    
    /**
     * Gets the hour of this datetime object in 24-hour clock.
     * @return The hour of this DateTime object.
     */
    public int getHour(){
	return hour;
    }
    
    /**
     * Gets the minute of this datetime object.
     * @return The minute of this DateTime object.
     */
    public int getMinute(){
	return minute;
    }

    /**
     * Checks if the given time is valid.
     * @param hour The hour of the day.
     * @param minute The minute.
     * @return True if the given time is in the range [00:00, 23:59] and false otherwise.
     */
    public static boolean isValidTime(int hour, int minute){
	boolean isValidHour = (hour>=0 && hour<24);
	boolean isValidMinute = (minute>=0 && minute<60);
	return isValidHour && isValidMinute;
    }
    
    /**
     * Sets this to the given date and time.
     * @param date The day of the month.
     * @param month The month of the year.
     * @param the year.
     * @param hour The hour of the day in 24-hour clock.
     * @param minute The minute.
     * @throws IllegalArgumentException if the date and time is invalid on the calendar and/or 24-hour clock.
     */
    public void setDateTime(int date, int month, int year, int hour, int minute) throws IllegalArgumentException{
	if(!isValidDate(date, month, year) || !isValidTime(hour, minute)){
	    throw new IllegalArgumentException(MSG_INVALIDDATETIME);
	}
	setDate(date, month, year);
	setTime(hour, minute);
    }
    
    private void setHour(int hour){
	this.hour = hour;
    }
    
    private void setMinute(int minute){
	this.minute = minute;
    }
    
    /**
     * Sets the time of this DateTime object.
     * @param hour The hour of the day in 24-hour clock.
     * @param minute The minute of the hour.
     * @throws IllegalArgumentException if the parameters result in an invalid time.
     */
    public void setTime(int hour, int minute) throws IllegalArgumentException{
	if(!isValidTime(hour, minute)){
	    throw new IllegalArgumentException(MSG_INVALIDTIME);
	}
	setHour(hour);
	setMinute(minute);
    }
    
    @Override
    /**
     * Returns the string representation of this DateTime object in the format DD/MM/YYYY HH:MM.
     * @return The String representation of this DateTime object.
     */
    public String toString(){
	StringBuilder output = new StringBuilder(super.toString());
	output.append(' ');
	output.append(String.format(FORMAT_TIME, hour, minute));
	return output.toString();
    }
}
