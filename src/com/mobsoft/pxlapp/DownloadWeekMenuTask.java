package com.mobsoft.pxlapp;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mobsoft.pxlapp.util.SimpleDateTime;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class DownloadWeekMenuTask extends AsyncTask<String, Void, Void>
{

	private WeekmenuActivity activiteit;
	private ProgressDialog progress;
	private Weekmenu weekmenu;
	private WeekmenuView weekmenuView;
	private int e;

	public DownloadWeekMenuTask(WeekmenuActivity activiteit)
	{
		this.activiteit = activiteit;
	}

	public DownloadWeekMenuTask(WeekmenuView weekmenuView)
	{
		this.weekmenuView = weekmenuView;
	}

	@Override
	protected Void doInBackground(String... waarden)
	{
		String url = null;
		if (waarden[0].equals("Campus Elfde Linie"))
		{
			url = "http://www.pxl.be/Pub/Studenten/Voorzieningen-Student/Catering/Weekmenu-Campus-Elfde-Linie.html";
		}
		else if (waarden[0].equals("Campus Diepenbeek"))
		{
			url = "http://www.pxl.be/Pub/Studenten/Voorzieningen-Student/Catering/Catering-Weekmenu-Campus-Diepenbeek.html";
		}
		else
		{
			url = "http://www.pxl.be/Pub/Studenten/Voorzieningen-Student/Catering/Catering-Weekmenu-Campus-Vildersstraat.html";
		}
		weekmenu = zoekMenu(url, waarden[0]);
		return null;
	}

	/**
	 * haalt de gegevens op en steekt deze in een object van de klasse Weekmenu
	 * 
	 * @param url de url waar de gegevens staan
	 * @return gevulde Weekmenu
	 */
	public Weekmenu zoekMenu(String url, String campus)
	{

		// ophalen html pagina
		weekmenu = null;
		try
		{
			Document weekmenudoc = Jsoup.connect(url).get();
			Elements dagen = weekmenudoc.select("div[class=catering catering1]"); // selecteren van alle dagen met hun
																					// info
			weekmenu = new Weekmenu();
			weekmenu.setCampus(campus);
			Dagmenu dagmenu;
			for (Element dag : dagen)
			{ // per dag de naam van de dag eruithalen en deze opslaan in de klasse Dagmenu
				Element datum = dag.select("h2.date").first();
				dagmenu = new Dagmenu(datum.text());

				if (dag == dagen.first())
				{ // datum van de eerste dag opslaan als de begindatum voor het weekmenu (voor caching later)
					String begindatumstring = datum.text().substring(datum.text().indexOf('(') + 1,	datum.text().indexOf(')'));
					weekmenu.setBegindatum(SimpleDateTime.parseDate(begindatumstring));
				}

				Elements menus = dag.select("div.wysiwyg p:matches(^(?!\\s*$).+)"); // ophalen van de gerechten op de
																					// huidige dag en lege <p> tags
																					// eruit filteren

				for (Element menu : menus)
				{
					String innerHtml = menu.html();
					String[] parts = innerHtml.split(" ?<br /> ?");
					for (String part : parts)
					{
						dagmenu.AddGerecht(part);
					}
				}
				weekmenu.AddDagmenu(dagmenu); // verschillende dagen + gerechten toevoegen
			}

			return weekmenu;
		}
		catch (IOException e)
		{
			this.e = 2;
		}
		catch (NullPointerException e)
		{
			this.e = 1; // vermijd fouten, anders was looper nodig
		}
		return null;
	}

	public void setProgress(ProgressDialog progress)
	{
		this.progress = progress;
	}

	@Override
	protected void onPostExecute(Void test)
	{
		super.onPostExecute(test);
		progress.dismiss(); // progressdialog dismissen
		if (e == 1)
		{
			activiteit.toonFout("Het weekmenu is momenteel niet beschikbaar");
		}
		else if (e == 2)
		{
			activiteit.toonFout("Er is een fout opgetreden, probeer opnieuw");
		}
		else
		{
			if (weekmenuView == null)
			{
				activiteit.setWeekmenu(weekmenu); // weekmenu doorgeven aan activity
				activiteit.toonWeekmenu(weekmenu.getCampus());
			}
			else
			{
				weekmenuView.setWeekmenu(weekmenu);
			}
		}
	}

	@Override
	protected void onPreExecute()
	{
		progress.show();
	}
}
