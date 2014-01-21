package com.mobsoft.pxlapp.activities.kalender;

import java.util.ArrayList;

import com.mobsoft.pxlapp.util.SimpleDateTime;

public class KalenderRij
{
	ArrayList<String> cellen;
	SimpleDateTime datum;
	KalenderRijType type;
	
	public KalenderRij()
	{
		datum = new SimpleDateTime();
		cellen = new ArrayList<String>();
	}
	
	public KalenderRij(SimpleDateTime datum)
	{
		this.datum = datum;
		cellen = new ArrayList<String>();
	}
	
	public KalenderRij(ArrayList<String> cellen, SimpleDateTime datum)
	{
		this.cellen = cellen;
		this.datum = datum;
	}
	
	public KalenderRij(ArrayList<String> cellen)
	{
		this.cellen = cellen;
		datum = new SimpleDateTime();
	}

	public String getCel(int index)
	{
		return cellen.get(index);
	}
	
	public void addCel(String cel)
	{
		cellen.add(cel);
	}
	
	public void setCel(int index, String cel)
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
	
	
}
