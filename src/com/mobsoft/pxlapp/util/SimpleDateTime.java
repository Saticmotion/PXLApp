package com.mobsoft.pxlapp.util;

/**
 * A simple class for storing dates and times.
 * There is no support for time zones, leap years, etc.
 * So only use then when you're certain the dates and times you're storing are generated with special cases in mind.
 * @author Simon
 *
 */
public class SimpleDateTime 
{
	int year;
	int month;
	int day;
	int hour;
	int minute;	
	
	
	/**
	 * Construct a simple date and time object
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 */
	public SimpleDateTime(int year, int month, int day, int hour, int minute) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
	}
	
	/**
	 * Construct a simple date object, with time component initialised to 0
	 * @param year
	 * @param month
	 * @param day
	 */
	public SimpleDateTime(int year, int month, int day) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = 0;
		this.minute = 0;
	}
	
	/**
	 * Construct a simple time object, with date component initialised to 0
	 * @param hour
	 * @param minute
	 */
	public SimpleDateTime(int hour, int minute)
	{
		super();
		this.year = 0;
		this.month = 0;
		this.day = 0;
		this.hour = hour;
		this.minute = minute;
	}
	
	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	
	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	
	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}
	
	/**
	 * @param month the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}
	
	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}
	
	/**
	 * @param day the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}
	
	/**
	 * @return the hour
	 */
	public int getHour() {
		return hour;
	}
	
	/**
	 * @param hour the hour to set
	 */
	public void setHour(int hour) {
		this.hour = hour;
	}
	
	/**
	 * @return the minute
	 */
	public int getMinute() {
		return minute;
	}
	
	/**
	 * @param minute the minute to set
	 */
	public void setMinute(int minute) {
		this.minute = minute;
	}	

	
	/**
	 * 
	 * @return returns a date string with the following format: dd/mm/yyyy
	 */
	public String toDateString()
	{
		return (day < 10 ? "0" + day : day) + "/" + (month < 10 ? "0" + month : month) + "/" + year;
	}
	
	/**
	 * 
	 * @return returns a time string in the following format: hh:mm
	 */
	public String toTimeString()
	{
		return (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute);
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
		
		return new SimpleDateTime(year, month, day);
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
		
		return new SimpleDateTime(hours, minutes);
	}
}
