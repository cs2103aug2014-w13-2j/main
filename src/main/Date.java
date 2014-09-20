package main;

/**
 * Defines the date in the format DD/MM/YYYY.
 * @author zixian
 */
public class Date implements Comparable<Date>{
    //Default date values.
    protected static final int DEFAULT_DAY = 1;
    protected static final int DEFAULT_MONTH = 1;
    protected static final int DEFAULT_YEAR = 1970;
    
    //String format for set date and time
    protected static final String FORMAT_DATE = "%1$d/%2$d/%3$d";
    
    enum MONTH {JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE,
		JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER,
		DECEMBER, INVALID};
    private int year, date;
    private MONTH month = MONTH.INVALID;
    
    /**
     * Creates and initialises this Date object to 1 January 1970.
     */
    public Date(){
	this(DEFAULT_DAY, DEFAULT_MONTH, DEFAULT_YEAR);
    }
    
    /**
     * Creates a Date object with the specified date, month and year.
     * @throws IllegalArgumentException if any of the values cause the date to be incorrect
     * 		on the actual calendar.
     */
    public Date(int date, int month, int year) throws IllegalArgumentException{
	if(!isValidDate(date, month, year)){
	    throw new IllegalArgumentException();
	}
	setDate(date, month, year);
    }

    /**
     * Gets the day of the month of this date object.
     * @returns The day in the month of this Date object.
     */
    public int getDate(){
	return date;
    }
    
    /**
     * Gets the month of this date object.
     * @return The month of this Date object.
     */
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
	String output = String.format(FORMAT_DATE, getDate(), getMonth(), getYear());
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
     * @throws IllegalArgumentException if any of the parameters cause the new date value to be incorrect
     * 		in the actual calendar.
     */
    public void setDate(int date, int month, int year) throws IllegalArgumentException{
	if(!isValidDate(date, month, year)){
	    throw new IllegalArgumentException();
	}
	setDate(date);
	setMonth(month);
	setYear(year);
    }

    protected void setDate(int date) {
	this.date = date;
    }
    
    protected void setMonth(int month){
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
    
    protected void setYear(int year){
	this.year = year;
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
	} else{
	    return date-date2.getDate();
	}
    }
}
