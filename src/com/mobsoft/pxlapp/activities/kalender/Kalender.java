package com.mobsoft.pxlapp.activities.kalender;

import java.util.ArrayList;


public class Kalender
{
	private ArrayList<KalenderRij> rijen;
	private ArrayList<String> titels;
		

	public Kalender()
	{
		this.rijen = new ArrayList<KalenderRij>();
		this.titels = new ArrayList<String>();
	}

	public Kalender(ArrayList<KalenderRij> rijen)
	{
		this.rijen = rijen;
		this.titels = new ArrayList<String>();
	}
	
	public Kalender(ArrayList<KalenderRij> rijen, ArrayList<String> titels)
	{
		this.rijen = rijen;
		this.titels = titels;
	}

	
	public KalenderRij getRij(int index)
	{
		return rijen.get(index);
	}
	
	public void addRij(KalenderRij rij)
	{
		rijen.add(rij);
	}
	
	public void setRij(int index, KalenderRij rij)
	{
		rijen.set(index, rij);
	}
	
	public void setTitels(ArrayList<String> titels)
	{
		this.titels = titels;
	}

	
	public Kalender filterKalender(String titel)
	{
		Kalender kalender = new Kalender();
		kalender.setTitels(titels);
		
		for (KalenderRij rij : rijen)
		{
			KalenderRij nieuweRij = new KalenderRij();
			nieuweRij.addCel(rij.getCel(0));
			nieuweRij.addCel(rij.getCel(titels.indexOf(titel))); //dit is de kolom die we er uit filteren
			nieuweRij.addCel(rij.getCel(5));
			nieuweRij.setDatum(rij.getDatum());
			nieuweRij.setType(rij.getType());
			
			kalender.addRij(nieuweRij);
		}
		
		return kalender;
	}
}
