package com.mobsoft.pxlapp;

import java.util.ArrayList;

import android.util.Log;

import com.mobsoft.pxlapp.util.LogUtil;
import com.mobsoft.pxlapp.util.SimpleDateTime;

public class Lesrooster
{
	private String klas;
	private ArrayList<Les> lessen;
	private SimpleDateTime beginDag;

	/**
	 * maak een lege lesrooster aan
	 */
	public Lesrooster()
	{
		lessen = new ArrayList<Les>();
	}

	public Lesrooster(String klas)
	{
		this.klas = klas;
		lessen = new ArrayList<Les>();
	}

	/**
	 * maakt een lesrooster op basis van een lijst {@link Les}sen
	 * 
	 * @param lessen
	 */
	public Lesrooster(String klas, ArrayList<Les> lessen)
	{
		this.klas = klas;
		this.lessen = lessen;
	}

	/**
	 * maakt een {@link Lesrooster} op basis van een bestaande lesrooster
	 * 
	 * @param lesrooster
	 */
	public Lesrooster(Lesrooster lesrooster)
	{
		this.lessen = lesrooster.getLessen();
	}

	/**
	 * @return de lessen
	 */
	public ArrayList<Les> getLessen()
	{
		return lessen;
	}

	public ArrayList<Les> getLessen(int dag)
	{
		ArrayList<Les> geselecteerd = new ArrayList<Les>();
		for (Les les : lessen) 
		{
			if (les.getStart().getDagVanWeek() == dag)
			{
				Log.d(LogUtil.PXL_TAG, "dagvanweek: " + les.getStart().getDagVanWeek() + "dag: " + dag);
				geselecteerd.add(les);
			}
		}
		
		return geselecteerd;
	}
	
	/**
	 * @param lessen
	 *            De lijst van lessen die je in het object wilt plaatsen
	 */
	public void setLessen(ArrayList<Les> lessen)
	{
		this.lessen = lessen;
	}

	/**
	 * Voegt een les toe aan het lesrooster
	 * 
	 * @param les
	 *            de les die je wilt toevoegen
	 */
	public void addLes(Les les)
	{
		lessen.add(les);
	}

	/**
	 * @return De eerste dag van de week
	 */
	public SimpleDateTime getBeginDag()
	{
		return beginDag;
	}

	/**
	 * @param beginDag
	 *            De eerste dag van de week
	 */
	public void setBeginDag(SimpleDateTime beginDag)
	{
		this.beginDag = beginDag;
	}

	public String getKlas()
	{
		return klas;
	}
	
	public void setKlas(String klas)
	{
		this.klas = klas;
	}
	

	/**
	 * @return Het weeknummer
	 */
	public int getWeek()
	{
		return beginDag.getWeek();
	}

	/**
	 * @param week
	 *            Het weeknummer
	 */
	public void setWeek(int week)
	{
		this.beginDag.setWeek(week);
	}

	public static Lesrooster lesroosterFromCache(String cacheString)
	{
		Lesrooster lesrooster = new Lesrooster();
		String[] lines = cacheString.split("\n");
		lesrooster.klas = lines[1]; // Eerste lijn overslaan, omdat hier de
									// datum van het cachen in staat.
		lesrooster.beginDag = new SimpleDateTime(Long.valueOf(lines[2]));

		for (int i = 3; i < lines.length; i++) // Eerste twee lijnen overslaan,
												// omdat deze al behandeld zijn.
		{
			lesrooster.lessen.add(new Les(lines[i]));
		}

		return lesrooster;
	}

	public String toCacheString()
	{
		String cacheString;

		Long cacheDatum = new SimpleDateTime().getMilliseconden();
		Long beginDatum = beginDag.getMilliseconden();

		cacheString = cacheDatum + "\n" + klas + "\n" + beginDatum;

		for (Les les : lessen)
		{
			cacheString += les.toCacheString();
		}

		return cacheString;
	}

}
