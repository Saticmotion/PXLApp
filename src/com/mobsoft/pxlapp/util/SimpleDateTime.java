package com.mobsoft.pxlapp.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A wrapper class for a Calendar object.
 * @author Simon
 *
 */
public class SimpleDateTime 
{
	private Calendar datum;
	
	public SimpleDateTime()
	{
		datum = Calendar.getInstance();
		datum.setLenient(true);
	}
	
	public SimpleDateTime(int minuut, int uur, int dag, int maand, int jaar)
	{
		this();
		setDag(dag);
		setMaand(maand);
		setJaar(jaar);
		setUur(uur);
		setMinuut(minuut);
	}
	
	public SimpleDateTime(int dag, int maand, int jaar)
	{
		this();
		setDag(dag);
		setMaand(maand);
		setJaar(jaar);
	}
	
	public SimpleDateTime(int minuut, int uur)
	{
		this();
		setUur(uur);
		setMinuut(minuut);
	}
	
	public SimpleDateTime(SimpleDateTime dateTime) 
	{
		this();
		this.datum = dateTime.datum;
	}

	public SimpleDateTime(Long milliseconden)
	{
		this();
		datum.setTimeInMillis(milliseconden);
	}
	
	public int getJaar()
	{
		return datum.get(Calendar.YEAR);
	}
	
	public void setJaar(int jaar)
	{
		datum.set(Calendar.YEAR, jaar);
	}
	
	
	public int getMaand()
	{
		return datum.get(Calendar.MONTH);
	}
	
	public void setMaand(int maand)
	{
		datum.set(Calendar.MONTH, maand);
	}
	
	
	public int getWeek()
	{
		return datum.get(Calendar.WEEK_OF_YEAR);
	}
	
	public void setWeek(int week)
	{
		datum.set(Calendar.WEEK_OF_YEAR, week);
	}
	
	
	public int getDag()
	{
		return datum.get(Calendar.DAY_OF_MONTH);
	}
	
	public void setDag(int dag)
	{
		datum.set(Calendar.DAY_OF_MONTH, dag);
	}
	
	
	public int getDagVanWeek()
	{
		return datum.get(Calendar.DAY_OF_WEEK);
	}
	
	public void setDagVanWeek(int dag)
	{
		datum.set(Calendar.DAY_OF_WEEK, dag);
	}
	
	
	public int getUur()
	{
		return datum.get(Calendar.HOUR_OF_DAY);
	}
	
	public void setUur(int uur)
	{
		datum.set(Calendar.HOUR_OF_DAY, uur);
	}
	
	
	public int getMinuut()
	{
		return datum.get(Calendar.MINUTE);
	}
	
	public void setMinuut(int minuut)
	{
		datum.set(Calendar.MINUTE, minuut);
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
		int dag = Integer.parseInt(dateParts[0]);
		int maand = Integer.parseInt(dateParts[1]);
		int jaar = Integer.parseInt(dateParts[2]);
		
		return new SimpleDateTime(dag, maand, jaar);
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
		int uur = Integer.parseInt(timeParts[0]);
		int minuut = Integer.parseInt(timeParts[1]);
		
		return new SimpleDateTime(minuut, uur);
	}

	/**
	 * Geeft de tijd sinds Epoch (1 januari 1970 00:00:00.000 GMT) in milliseconden
	 * @return tijd in milliseconden
	 */
	public Long getMilliseconden()
	{
		return datum.getTimeInMillis();
	}
	
	public String toString(String patroon)
	{
		String output = new SimpleDateFormat(patroon).format(datum.getTime());
		return output;
	}
}
