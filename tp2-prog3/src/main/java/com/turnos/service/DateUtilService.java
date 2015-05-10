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
	
	private Date getZeroTimeDate(Date fecha) {
	    Date res = fecha;
	    Calendar calendar = Calendar.getInstance();

	    calendar.setTime( fecha );
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);

	    res = calendar.getTime();

	    return res;
	}
	
}
