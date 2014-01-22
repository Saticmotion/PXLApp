package com.mobsoft.pxlapp.activities.kalender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;

import com.mobsoft.pxlapp.util.SimpleDateTime;

import android.R.integer;
import android.R.string;
import android.app.Activity;

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

	public ArrayList<KalenderRij> getRijen()
	{
		return rijen;
	}

	public KalenderRij getRij(int index)
	{
		return rijen.get(index);
	}

	public ArrayList<String> getTitels()
	{
		return titels;
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
		ArrayList<String> nieuweTitels = new ArrayList<String>();
		nieuweTitels.add(titels.get(0));
		nieuweTitels.add(titel);
		nieuweTitels.add(titels.get(6));
		kalender.setTitels(nieuweTitels);

		for (KalenderRij rij : rijen)
		{
			KalenderRij nieuweRij = new KalenderRij();
			nieuweRij.addCel(rij.getCel(0));
			nieuweRij.addCel(rij.getCel(titels.indexOf(titel))); // dit is de kolom die we er uit filteren
			nieuweRij.addCel(rij.getCel(5));
			nieuweRij.setDatum(rij.getDatum());
			nieuweRij.setType(rij.getType());

			kalender.addRij(nieuweRij);
		}

		return kalender;
	}

	public static Kalender kalenderVanBestand(Activity activity) throws IOException
	{
		InputStream is = activity.getAssets().open("kalender.txt");
		Writer writer = new StringWriter();
		char[] buffer = new char[2048];

		try
		{
			Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			int n;
			while ((n = reader.read(buffer)) != -1)
			{
				writer.write(buffer, 0, n);
			}
		}
		finally
		{
			is.close();
		}
		
		

		String kalenderGegevens = writer.toString();
		Kalender kalender = new Kalender();

		String[] lijnen = kalenderGegevens.split("\n");
		String[] kTitels = lijnen[0].split(",");

		kalender.setTitels(new ArrayList<String>(Arrays.asList(kTitels)));

		for (int i = 1; i < lijnen.length; i++)
		{
			KalenderRij rij = new KalenderRij();
			String[] cellen = lijnen[i].split(",");
			
			rij.setDatum(SimpleDateTime.parseDate(cellen[0])); // De cel met de datum opslaan
			for (int j = 1; j < cellen.length; j++)
			{
				rij.addCel(new KalenderCel(cellen[j]));
			}
			kalender.addRij(rij);
		}
		return kalender;
	}
}
