package com.mobsoft.pxlapp;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class DownloadKlassenLijstTask extends AsyncTask<String, Void, Void>
{
	private LesroostersActivity activiteit;

	public DownloadKlassenLijstTask(LesroostersActivity activiteit)
	{
		this.activiteit = activiteit;
	}

	@Override
	protected Void doInBackground(String... params)
	{
		String guest = params[0];
		String url = "https://kalender.phl.be/kalenterit2/index.php?kt=lk&av=140120140126140121&guest=" + guest
				+ "&lang=fla";

		try
		{
			Document kalenderpagina = Jsoup.connect(url).timeout(20000).get();
			Elements dagen = kalenderpagina.select("option:not([selected])");
			String cacheString = "";

			for (Element dag : dagen)
			{
				cacheString += dag.text() + ",";
			}
			
			if (cacheString.length() != 0)
			{
				cacheString = cacheString.substring(0, cacheString.length() - 1); // laatste overbodige komma
																					// verwijderen
			}
			
			CacheManager.cacheData(activiteit, cacheString.getBytes(), "klassenlijst" + guest);
		}
		catch (Exception e)
		{
			activiteit.runOnUiThread(new Runnable()
			{				
				@Override
				public void run()
				{
					activiteit.toonFout("", "Er is iets fout gegaan, probeer opnieuw");
				}
			});
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result)
	{
		activiteit.klaarDownloaden();
	}
}
