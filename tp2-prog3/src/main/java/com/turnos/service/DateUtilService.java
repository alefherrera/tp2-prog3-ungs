package com.turnos.service;

import java.util.Calendar;
import java.util.Date;

public class DateUtilService {

	private static DateUtilService instance;
	public static DateUtilService getInstance(){
		if (instance == null)
			instance = new DateUtilService();
		return instance;
	}
	
	private DateUtilService(){
		
	}
	
	public boolean compareOnlyDate(Date first, Date second){
		return getZeroTimeDate(first).compareTo(getZeroTimeDate(second)) == 0;
	}
	
	public Date getZeroTimeDate(Date fecha) {
	  
	    Calendar calendar = Calendar.getInstance();

	    calendar.setTime( fecha );
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);

	    return calendar.getTime();

	}
	
	public Date getWithHours(Date fecha){

	    Calendar calendar = Calendar.getInstance();

	    calendar.setTime( fecha );
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);

	    return calendar.getTime();

	}
	
	public Date changetHour(Date fecha, int h){
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(fecha); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, h); // adds one hour
	    return cal.getTime();
	}
	
}
