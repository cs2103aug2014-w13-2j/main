package edu.dynamic.dynamiz.structure;


/**
 * Defines an object containing both date and time. Time is defined using 24-hour clock.
 * @author zixian
 */
public class MyDateTime extends MyDate {
    //The default time values
    private static final int DEFAULT_HOUR = 0;
    private static final int DEFAULT_MINUTE = 0;
    
    //The string format for time.
    private static final String FORMAT_TIME = "%1$d:%2$02d";
    public static final String REGEX_DATETIME = "[0-9]{1,2}/[0-9]{1,2}/[0-9]{1,4} [0-9]{1,2}:[0-9]{1,2}";
    
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
     */
    public MyDateTime(int date, int month, int year) throws IllegalArgumentException{
	this(date, month, year, DEFAULT_HOUR, DEFAULT_MINUTE);
    }
    
    /**
     * Creates a new DateTime object with the specified date and time details.
     * @throws IllegalArgumentException if the date and time is invalid on the calendar of 24-hour clock.
     */
    public MyDateTime(int date, int month, int year, int hour, int minute) throws IllegalArgumentException{
	setDateTime(date, month, year, hour, minute);
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
     * @return True if the given time is in the range [00:00, 23:59] and false otherwise.
     */
    public static boolean isValidTime(int hour, int minute){
	boolean isValidHour = (hour>=0 && hour<24);
	boolean isValidMinute = (minute>=0 && minute<60);
	return isValidHour && isValidMinute;
    }
    
    /**
     * Sets this to the given date and time.
     */
    public void setDateTime(int date, int month, int year, int hour, int minute) throws IllegalArgumentException{
	if(!isValidDate(date, month, year) || !isValidTime(hour, minute)){
	    throw new IllegalArgumentException();
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
     * @throws IllegalArgumentException if the parameters result in an invalid time.
     */
    public void setTime(int hour, int minute) throws IllegalArgumentException{
	if(!isValidTime(hour, minute)){
	    throw new IllegalArgumentException();
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

    @Override
    public int compareTo(MyDate date2){
	if(!(date2 instanceof MyDateTime)){
	    return compareTo(new MyDateTime(date2));
	} else{
	    return compareTo((MyDateTime)date2);
	}
    }
    
    /**
     * Performs the same compareTo method between 2 DateTime objects.
     */
    private int compareTo(MyDateTime date2){
	int result = super.compareTo(date2);
	if(result==0){
	    if(hour!=date2.getHour()){
		return hour-date2.getHour();
	    } else{
		return minute-date2.getMinute();
	    }
	}
	return result;
    }
}
