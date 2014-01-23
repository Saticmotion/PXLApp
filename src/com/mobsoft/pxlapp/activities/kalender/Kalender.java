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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		nieuweTitels.add(titels.get(1));
		nieuweTitels.add(titel);
		nieuweTitels.add(titels.get(6));
		kalender.setTitels(nieuweTitels);

		for (KalenderRij rij : rijen)
		{
			KalenderRij nieuweRij = new KalenderRij();
			nieuweRij.addCel(rij.getCel(0));
			// dit is de kolom die we er uit filteren
			// - 1 omdat er een titel is voor de datumkolom.
			nieuweRij.addCel(rij.getCel(titels.indexOf(titel) - 1)); 
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
		String matched = "";
		kalender.setTitels(new ArrayList<String>(Arrays.asList(kTitels)));
		Pattern pattern = Pattern.compile("<[a-z]*>");
		
		for (int i = 1; i < lijnen.length; i++) {
			
			KalenderRij rij = new KalenderRij();
			Matcher matcher = pattern.matcher(lijnen[i]);
			String[] cellen = lijnen[i].split(",");
			rij.setDatum(SimpleDateTime.parseDate(cellen[0])); // De cel met de datum opslaan
			
			for (int j = 1; j < cellen.length; j++) 
			{
				KalenderCel cel = new KalenderCel();
				matcher = pattern.matcher(cellen[j]);
				
				if (matcher.find()) 
				{
					matched = matcher.group();
					cel.setType(vindType(matched));	
					cel.setTekst(cellen[j].replace(matched, ""));
				}
				else 
				{
					cel.setTekst(cellen[j]);
				}
				
				
				rij.addCel(cel);
			}
			kalender.addRij(rij);
			
		}
		
		/**for (int i = 1; i < lijnen.length; i++)
		{
			KalenderRij rij = new KalenderRij();
			//Matcher matcher = pattern.matcher(lijnen[i]);
			
			
			String[] cellen = lijnen[i].split(",");
			
			
			
			//rij.setDatum(SimpleDateTime.parseDate(cellen[0])); // De cel met de datum opslaan
			
			
			for (int j = 1; j < cellen.length; j++)
			{
				KalenderCel cel = new KalenderCel();
				*matcher = pattern.matcher(cellen[j]);
				if (matcher.find()) 
				{
					matched = matcher.group(1);
				}
				cel.setType(vindType(matched));	
				cel.setTekst(cellen[j].replace(matched, ""));
				cel.setTekst(cellen[i]);
				rij.addCel(cel);
			}
			kalender.addRij(rij);
		}*/
		return kalender;
	}
	
	private static KalenderType vindType(String stringType)
	{
		if (stringType.equals("<examen>")) 
		{
			return KalenderType.EXAMEN;
		}
		else if (stringType.equals("<delibiratie>")) 
		{
			return KalenderType.DELIBERATIE;
		}
		else if (stringType.equals("<vakantie>")) 
		{
			return KalenderType.VAKANTIE;
		}
		else if(stringType.equals("<vrij>"))
		{
			return KalenderType.VRIJ;
		}
		else 
		{
			return KalenderType.NORMAAL;
		}
	}
}
