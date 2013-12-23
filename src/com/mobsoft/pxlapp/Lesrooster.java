package com.mobsoft.pxlapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.mobsoft.pxlapp.util.SimpleDateTime;

public class Lesrooster 
{
	private ArrayList<Les> lessen;
	private SimpleDateTime beginDag;
	private SimpleDateTime eindDag;
	private int week;
	
	/**
	 * maak een lege lesrooster aan
	 */
	public Lesrooster() {
		super();
		lessen = new ArrayList<Les>();
	}

	/**
	 * maakt een lesrooster op basis van een lijst {@link Les}sen
	 * @param lessen
	 */
	public Lesrooster(ArrayList<Les> lessen) {
		super();
		this.lessen = lessen;
	}
	
	/**
	 * maakt een {@link Lesrooster} op basis van een bestaande lesrooster
	 * @param lesrooster
	 */
	public Lesrooster(Lesrooster lesrooster)
	{
		this.lessen = lesrooster.getLessen();
	}

	/**
	 * @return de lessen
	 */
	public ArrayList<Les> getLessen() {
		return lessen;
	}

	/**
	 * @param lessen De lijst van lessen die je in het object wilt plaatsen
	 */
	public void setLessen(ArrayList<Les> lessen) {
		this.lessen = lessen;
	}
	
	/**
	 * Voegt een les toe aan het lesrooster
	 * @param les de les die je wilt toevoegen
	 */
	public void addLes(Les les)
	{
		lessen.add(les);
	}

	
	/**
	 * @return De eerste dag van de week
	 */
	public SimpleDateTime getBeginDag() {
		return beginDag;
	}

	
	/**
	 * @param beginDag De eerste dag van de week
	 */
	public void setBeginDag(SimpleDateTime beginDag) {
		this.beginDag = beginDag;
	}

	
	/**
	 * @return De laatste dag van de week
	 */
	public SimpleDateTime getEindDag() {
		return eindDag;
	}

	
	/**
	 * @param eindDag De laatste dag van de week
	 */
	public void setEindDag(SimpleDateTime eindDag) {
		this.eindDag = eindDag;
	}

	
	/**
	 * @return Het weeknummer
	 */
	public int getWeek() {
		return week;
	}

	
	/**
	 * @param week Het weeknummer
	 */
	public void setWeek(int week) {
		this.week = week;
	}
}
