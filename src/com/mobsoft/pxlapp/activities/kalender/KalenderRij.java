package com.mobsoft.pxlapp.activities.kalender;

import java.util.ArrayList;

import com.mobsoft.pxlapp.util.SimpleDateTime;

public class KalenderRij
{
	private ArrayList<KalenderCel> cellen;
	private SimpleDateTime datum;
	private KalenderType type;
	
	public KalenderRij()
	{
		datum = new SimpleDateTime();
		cellen = new ArrayList<KalenderCel>();
		type = KalenderType.NORMAAL;
	}
	
	public KalenderRij(SimpleDateTime datum)
	{
		this.datum = datum;
		cellen = new ArrayList<KalenderCel>();
	}
	
	public KalenderRij(ArrayList<KalenderCel> cellen, SimpleDateTime datum)
	{
		this.cellen = cellen;
		this.datum = datum;
	}
	
	public KalenderRij(ArrayList<KalenderCel> cellen)
	{
		this.cellen = cellen;
		datum = new SimpleDateTime();
	}

	public KalenderCel getCel(int index)
	{
		return cellen.get(index);
	}
	
	public void addCel(KalenderCel cel)
	{
		cellen.add(cel);
	}
	
	public void setCel(int index, KalenderCel cel)
	{
		cellen.set(index, cel);
	}

	public SimpleDateTime getDatum()
	{
		return datum;
	}

	public void setDatum(SimpleDateTime datum)
	{
		this.datum = datum;
	}

	
	public KalenderType getType()
	{
		return type;
	}

	
	public void setType(KalenderType type)
	{
		this.type = type;
	}
	
	
}
