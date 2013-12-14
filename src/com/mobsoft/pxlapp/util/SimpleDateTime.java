package com.mobsoft.pxlapp.util;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple class for storing dates and times.
 * There is no support for time zones, leap years, etc.
 * So only use then when you're certain the dates and times you're storing are generated with special cases in mind.
 * @author Simon
 *
 */
public class SimpleDateTime 
{
	private Calendar datum;
	
	public SimpleDateTime()
	{
		datum = Calendar.getInstance();
	}
	
	public int getJaar()
	{
		return datum.get(0);
	}
	
	/**
	 * Parses a date from a string to a {@link SimpleDateTime} object.
	 * Can handle various delimiters, as in these examples:
	 * <ul>
	 * <li>15/10/1993</li>
	 * <li>15-10-1993</li>
	 * <li>15.10.1993</li>
	 * <li>15:10:1993</li>
	 * </ul>
	 * It can also handle mixed delimiters as in these examples:
	 * <ul>
	 * <li>15/10.1993</li>
	 * <li>15-10/1993</li>
	 * <li>15:10-1993</li>
	 * <li>etc.</li>
	 * </ul>
	 * @param date the string to parse
	 * @return
	 */
	public static SimpleDateTime parseDate(String date)
	{
		String[] dateParts = date.split("[-\\.:]");
		int day = Integer.parseInt(dateParts[0]);
		int month = Integer.parseInt(dateParts[1]);
		int year = Integer.parseInt(dateParts[2]);
		
		return null;//new SimpleDateTime(year, month, day);
	}

	
	/**
	 * Parses a time from a string to a {@link SimpleDateTime} object.
	 * Can handle various delimiters, as in these examples:
	 * <ul>
	 * <li>20:07</li>
	 * <li>20-07</li>
	 * <li>20.07</li>
	 * </ul>
	 * @param time the string to parse
	 * @return
	 */
	public static SimpleDateTime parseTime(String time)
	{
		String[] timeParts = time.split("[-\\.:]");
		int hours = Integer.parseInt(timeParts[0]);
		int minutes = Integer.parseInt(timeParts[1]);
		
		return null; //new SimpleDateTime(hours, minutes);
	}
}
