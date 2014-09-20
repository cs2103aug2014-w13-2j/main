package main;

/**
 * Defines an object containing both date and time. Time is defined using 24-hour clock.
 * @author zixian
 */
public class DateTime extends Date {
    //The default time values
    private static final int DEFAULT_HOUR = 0;
    private static final int DEFAULT_MINUTE = 0;
    
    //The string format for time.
    private static final String FORMAT_TIME = "%1$d:%2$d";
    
    //The main additional data members
    private int hour, minute;
    
    public DateTime(){
	this(DEFAULT_DAY, DEFAULT_MONTH, DEFAULT_YEAR, DEFAULT_HOUR, DEFAULT_MINUTE);
    }
    
    public DateTime(int date, int month, int year, int hour, int minute) throws IllegalArgumentException{
	setDateTime(date, month, year, hour, minute);
    }
    
    public int getHour(){
	return hour;
    }
    
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
}
