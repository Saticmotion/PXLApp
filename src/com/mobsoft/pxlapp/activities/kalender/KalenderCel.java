package com.mobsoft.pxlapp.activities.kalender;

public class KalenderCel
{
	private String tekst;
	private KalenderType type;
	
	public KalenderCel()
	{
		this.tekst = "";
		this.type = KalenderType.NORMAAL;
	}
	
	public KalenderCel(String tekst)
	{
		this.tekst = tekst;
		this.type = KalenderType.NORMAAL;
	}

	public String getTekst()
	{
		return tekst;
	}

	public void setTekst(String tekst)
	{
		this.tekst = tekst;
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
