package com.mobsoft.pxlapp;

import java.util.ArrayList;

public class Lesrooster 
{
	ArrayList<Les> lessen;
	
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
	 * @return the lessen
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
}
