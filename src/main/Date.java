package main;

/**
 * Defines the date.
 * @author zixian
 */
public class Date {
    enum MONTH {JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE,
		JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER,
		DECEMBER, INVALID};
    private int year = 0, date = 0, hour = -1, minute = -1;
    private MONTH month = MONTH.INVALID;
    
    public Date(){}
    
    public Date(int date, int month, int year){
	setDate(date, month, year);
    }

    public Date(int date, int month, int year, int hour,
	    	int minute){
	setDate(date, month, year, hour, minute);
    }
    
    private int getDate(){
	return date;
    }
    
    private int getMonth(){
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
	    default: return -1;
	}
    }
    
    private int getYear(){
	return year;
    }
    
    private int getHour(){
	return hour;
    }
    
    private int getMinute(){
	return minute;
    }
    
    /**
     * Returns this in the format
     * DD/MM/YYYY hh:mm.
     * Invalid date is --/--/---- while invalid time is
     * --:--
     */
    public String toString(){
	String output = "";
	if(date<=0 || month==MONTH.INVALID || year<=0){
	    output+="--/--/---- ";
	}else{
	    output+=getDate()+"/"+getMonth()+"/"+getYear()+" ";
	}
	if(hour==-1 || minute==-1){
	    output+="--:--";
	}else{
	    output+=getHour()+":"+getMinute();
	}
	return output;
    }
    
    /**
     * Sets this to the given date without a specified time.
     */
    public void setDate(int date, int month, int year){
	setDate(date, month, year, -1, -1);
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
}
