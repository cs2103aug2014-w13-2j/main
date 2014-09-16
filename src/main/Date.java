package main;

/**
 * Defines the date.
 * @author zixian
 */
public class Date implements Comparable<Date>{
    //Default, invalid date values.
    public static final int DEFAULT_DAY = 0;
    public static final int DEFAULT_MONTH = 0;
    public static final int DEFAULT_YEAR = 0;
    public static final int DEFAULT_HOUR = -1;
    public static final int DEFAULT_MINUTE = -1;
    
    //String format for unset date and time
    private static final String FORMAT_DEFAULT_DATE = "--/--/----";
    private static final String FORMAT_DEFAULT_TIME = "--:--";
    
    //String format for set date and time
    private static final String FORMAT_DATE = "%1$d/%2$d/%3$d";
    private static final String FORMAT_TIME = "%1$d:%2$d";
    
    enum MONTH {JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE,
		JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER,
		DECEMBER, INVALID};
    private int year, date, hour, minute;
    private MONTH month = MONTH.INVALID;
    
    public Date(){
	this(DEFAULT_DAY, DEFAULT_MONTH, DEFAULT_YEAR, DEFAULT_HOUR, DEFAULT_MINUTE);
    }
    
    public Date(int date, int month, int year){
	setDate(date, month, year);
    }

    public Date(int date, int month, int year, int hour, int minute){
	setDate(date, month, year, hour, minute);
    }
    
    public int getDate(){
	return date;
    }
    
    public int getMonth(){
	switch(month){
	    case JANUARY: return 1;
	    case FEBRUARY: return 2;
	    case MARCH: return 3;
	    case APRIL: return 4;
	    case MAY: return 5;
	    case JUNE: return 6;
	    case JULY: return 7;
	    case AUGUST: return 8;
	    case SEPTEMBER: return 9;
	    case OCTOBER: return 10;
	    case NOVEMBER: return 11;
	    case DECEMBER: return 12;
	    default: return DEFAULT_MONTH;
	}
    }
    
    public int getYear(){
	return year;
    }
    
    public int getHour(){
	return hour;
    }
    
    public int getMinute(){
	return minute;
    }
    
    /**
     * Returns the String representation of this Date object for printing purpose.
     * Format is: DD/MM/YYYY hh:mm.
     * Invalid date is --/--/---- while invalid time is --:--
     * @return String representation of this Date object.
     */
    public String toString(){
	String output = "";
	if(date<=0 || month==MONTH.INVALID || year<=0){
	    output+=FORMAT_DEFAULT_DATE+" ";
	}else{
	    output+=(String.format(FORMAT_DATE, getDate(), getMonth(), getYear())+" ");
	}
	if(hour==-1 || minute==-1){
	    output+=FORMAT_DEFAULT_TIME;
	}else{
	    output+=String.format(FORMAT_TIME, getHour(), getMinute());
	}
	return output;
    }
    
    /**
     * Returns String representation of this Date for writing to file.
     * Format: 
     * @return String representation of this Date object for file IO.
     */
    public String toFileString(){
	
	return "";
    }
    
    /**
     * Sets this to the given date without a specified time.
     */
    public void setDate(int date, int month, int year){
	setDate(date, month, year, DEFAULT_HOUR, DEFAULT_MINUTE);
    }
    
    /**
     * Sets this to the given date and time.
     */
    public void setDate(int date, int month, int year,
	    		int hour, int minute){
	setDate(date);
	setMonth(month);
	setYear(year);
	setHour(hour);
	setMinute(minute);
    }
    
    private void setDate(int date) {
	this.date = date;
    }
    
    private void setMonth(int month){
	switch(month){
	    case 1: this.month = MONTH.JANUARY;
	    		break;
	    case 2: this.month = MONTH.FEBRUARY;
	    		break;
	    case 3: this.month = MONTH.MARCH;
	    		break;
	    case 4: this.month = MONTH.APRIL;
	    		break;
	    case 5: this.month = MONTH.MAY;
	    		break;
	    case 6: this.month = MONTH.JUNE;
	    		break;
	    case 7: this.month = MONTH.JULY;
	    		break;
	    case 8: this.month = MONTH.AUGUST;
	    		break;
	    case 9: this.month = MONTH.SEPTEMBER;
	    		break;
	    case 10: this.month = MONTH.OCTOBER;
	    		break;
	    case 11: this.month = MONTH.NOVEMBER;
	    		break;
	    case 12: this.month = MONTH.DECEMBER;
	    		break;
	    default: this.month = MONTH.INVALID;
	}
    }
    
    private void setYear(int year){
	this.year = year;
    }
    
    private void setHour(int hour){
	this.hour = hour;
    }
    
    private void setMinute(int minute){
	this.minute = minute;
    }
    
    @Override
    /**
     * Compares which of the 2 Date objects comes later.
     * --/--/---- is larger than 31/12/9999 and
     * --:-- is larger than 23:59.
     * Pre-condition: Date and time will only come in either the former
     * 			or the latter form. Mix and match between the 2 forms
     * 			is not supported by ALL Date methods.
     * @return 0 if the 2 dates have same date and time, positive
     * 		integer if this come later than date2, negative integer
     * 		if this comes earlier than date2.
     */
    public int compareTo(Date date2){
	if(year==DEFAULT_YEAR && date2.getYear()==DEFAULT_YEAR){
	    return 0;
	} else if(date2.getYear()==DEFAULT_YEAR){
	    return -1;
	} else if(year==DEFAULT_YEAR){
	    return 1;
	} else if(year!=date2.getYear()){
	    return year-date2.getYear();
	} else if(getMonth()!=date2.getMonth()){
	    return getMonth()-date2.getMonth();
	} else if(date!=date2.getDate()){
	    return date-date2.getDate();
	} else if(hour==DEFAULT_HOUR && date2.getHour()==DEFAULT_HOUR){
	    return 0;
	} else if(date2.getHour()==DEFAULT_HOUR){
	    return -1;
	} else if(hour==DEFAULT_HOUR){
	    return 1;
	} else if(hour!=date2.getHour()){
	    return hour-date2.getHour();
	} else{
	    return minute-date2.getMinute();
	}
    }
}
