package com.mobsoft.pxlapp;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mobsoft.pxlapp.util.SimpleDateTime;

import android.os.AsyncTask;
import android.util.Log;

/**
 * @author Simon
 *
 */
public class DownloadLesroosterTask extends AsyncTask<String, Void, Void> 
{
	private LesroostersActivity activity;
	private Lesrooster lesrooster;
	private String debugTag = "Pxl App";
	
	public DownloadLesroosterTask(LesroostersActivity activity) 
	{
		this.activity = activity;
	}
	
	@Override
	protected Void doInBackground(String... klas) 
	{
		String URL = "https://kalender.phl.be/kalenterit2/index.php?kt=lk&yks=&cluokka=" + klas[0] + "&guest=IT%2Fphl&lang=fla&print=arkipaivat";
		
		try 
		{
			Document document = Jsoup.connect(URL).get();
			lesrooster = new Lesrooster(klas[0]);
			
			SimpleDateTime beginDag = SimpleDateTime.parseDate(document.select("table th span.hdr_date font").first().text()); //Haal de datums op uit de tableheaders
			lesrooster.setBeginDag(beginDag);
			
			Elements rows = document.select("table.asio_basic > tbody > tr"); // Vraag alle tablerows op			
			ArrayList<IndexedElement> elements = parseRows(rows);			
			parseElements(elements);
			
			Log.d(debugTag, "Done");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			//activity.showError("Fout", "Er is iets fout gegaan tijdens het downloaden van het lessenrooster, probeer opnieuw.");
		}
		
		return null;
	}
	
	protected void onPostExecute(Void result)
	{
		activity.ontvangLesrooster(lesrooster);
	}
	
	private ArrayList<IndexedElement> parseRows(Elements rows)
	{
		int[] offsets = new int[rows.size()];
		ArrayList<IndexedElement> elements = new ArrayList<IndexedElement>();
		
		for (int i = 0; i < rows.get(0).children().size(); i++) //Voor elke kolom (1 kolom = 1 dag)
		{
			int lesIndex = 0;
			
			for (int j = 0; j < rows.size(); j++) 				//Voor elke rij (1 rij = 30 min)
			{
				Element cell = rows.get(j).child(i + offsets[j]); //Cel opvragen. Offsets om een 'kolom' naar links te schuiven 
																  //wanneer er in de vorige kolom een rowspan was.
				if (cell.hasAttr("rowspan")) 					//Als de cell een rowspan heeft
				{
					int rowspan = Integer.parseInt(cell.attr("rowspan")); // Tel de rowspan op bij j, om 'lege' cellen over te slaan.
					
					for (int k = 1; k < rowspan; k++)
					{
						offsets[j + k]--;
					}
					
					j += rowspan - 1;
					
					elements.add(new IndexedElement(cell, i - 1, lesIndex++));				//Cell heeft een rowspan, en is dus een cel met data. i - 1 als correctie voor kolom met uren.
				}
			}
		}
		
		return elements;
	}
	
	private void parseElements(ArrayList<IndexedElement> elements)
	{
		for (IndexedElement e : elements) 
		{
			String tijdEnNaam = e.getElement().select("b").html(); //selecteert "[beginuur] - [einduur]<br/>[lesnaam]"
			if (!tijdEnNaam.equals(""))
			{
				String[] parts = tijdEnNaam.split("<br />"); // selecteert "[beginuur] - [einduur]" en "[lesnaam]"
				String[] tijden = parts[0].split(" - "); //selecteert "[beginuur]" en "[einduur]"
				SimpleDateTime begin = new SimpleDateTime(SimpleDateTime.parseTime(tijden[0])); //beginuur parsen
				begin.setDag(lesrooster.getBeginDag().getDag() + e.getDag());
				SimpleDateTime einde = new SimpleDateTime(SimpleDateTime.parseTime(tijden[1])); //einduur parsen
				einde.setDag(lesrooster.getBeginDag().getDag() + e.getDag());
				String naam = parts[1]; //lesnaam selecteren
				
				String lokaalHMTL = e.getElement().select("font").html();
				String[] lokaalParts = lokaalHMTL.split("<br />");
				String lokaal = "";
				
				if (lokaalParts.length > 2) //Het is mogelijk dat er geen lokaal is opgegeven. In dat geval blijft lokaal een lege string
				{
					  lokaal = lokaalParts[2];
				}
				
				String leerkrachtHTML = e.getElement().select("tr > td").first().ownText();
				
				lesrooster.addLes(new Les(naam, lokaal, leerkrachtHTML, begin, einde));
			}
		} 
	}
	
	private class IndexedElement
	{
		private Element element;
		private int dag;
		private int les;
		
		public IndexedElement(Element element, int dag, int les)
		{
			this.setElement(element);
			this.setDag(dag);
			this.setLes(les);
		}

		public Element getElement() {
			return element;
		}

		public void setElement(Element element) {
			this.element = element;
		}

		public int getDag() {
			return dag;
		}

		public void setDag(int dag) {
			this.dag = dag;
		}

		public int getLes() {
			return les;
		}

		public void setLes(int les) {
			this.les = les;
		}
	}
}
