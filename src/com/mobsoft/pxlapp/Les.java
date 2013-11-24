package com.mobsoft.pxlapp;

import java.util.Date;

/**
 * Stelt een les voor. <br>
 * Bevat volgende velden:
 * <ul>
 * <li>naam</li>
 * <li>lokaal</li>
 * <li>leerkracht</li>
 * <li>startuur</li>
 * <li>einduur</li>
 * </ul>
 * @author Simon
 *
 */
public class Les 
{
	private String naam;
	private String lokaal;
	private String leerkracht;
	private Date start;
	private Date einde;

	/**
	 * 
	 * @param naam de naam van de les
	 * @param lokaal het lokaal waar de les plaats vindt
	 * @param leerkracht de leerkracht die de les geeft
	 * @param start het beginuur van de les
	 * @param einde het einduur van de les
	 */
	public Les(String naam, String lokaal, String leerkracht, Date start, Date einde) 
	{
		super();
		this.naam = naam;
		this.lokaal = lokaal;
		this.leerkracht = leerkracht;
		this.start = start;
		this.einde = einde;
	}
	
	/**
	 * @return de naam van de les
	 */
	public String getNaam() {
		return naam;
	}

	/**
	 * @param naam de naam van de les
	 */
	public void setNaam(String naam) {
		this.naam = naam;
	}

	/**
	 * @return het lokaal
	 */
	public String getLokaal() {
		return lokaal;
	}

	/**
	 * @param lokaal het lokaal
	 */
	public void setLokaal(String lokaal) {
		this.lokaal = lokaal;
	}

	/**
	 * @return de leerkracht die de les geeft
	 */
	public String getLeerkracht() {
		return leerkracht;
	}

	/**
	 * @param leerkracht de leerkracht die de les geeft
	 */
	public void setLeerkracht(String leerkracht) {
		this.leerkracht = leerkracht;
	}

	/**
	 * @return het startuur van de les
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * @param start het startuur van de les
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * @return het einduur van de les
	 */
	public Date getEinde() {
		return einde;
	}

	/**
	 * @param einde het einduur van de les
	 */
	public void setEinde(Date einde) {
		this.einde = einde;
	}
	
}
