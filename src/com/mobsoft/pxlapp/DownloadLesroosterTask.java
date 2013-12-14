package com.mobsoft.pxlapp;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mobsoft.pxlapp.util.SimpleDateTime;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

/**
 * @author Simon
 *
 */
public class DownloadLesroosterTask extends AsyncTask<String, Void, Void> 
{
	private LesroostersActivity activity;
	private Lesrooster lesrooster;
	
	public DownloadLesroosterTask(LesroostersActivity activity) 
	{
		this.activity = activity;
	}
	
	@Override
	protected Void doInBackground(String... URLs) 
	{
		String URL = URLs[0];
		
		try 
		{
			Document document = Jsoup.connect(URL).get();
			lesrooster = new Lesrooster();

			Element header = document.select("h1").first(); //Vraag de titel van het document op ("Klas {klas} / Week {weeknummer})
			
			Log.d("Pxl App", header.text());
			
			Elements datums = document.select("table th span.hdr_date font"); //Haal de datums op uit de tableheaders
			
			SimpleDateTime beginDag = SimpleDateTime.parseDate(datums.first().text()); //Steek de eerste dag van de week in een Datum-object
			lesrooster.setBeginDag(beginDag);
			SimpleDateTime eindDag = SimpleDateTime.parseDate(datums.last().text()); //Steek de laatste dag van de week in een Datum object
			lesrooster.setEindDag(eindDag);
			
//			Log.d("Pxl App", lesrooster.getBeginDag().toDateString());
//			Log.d("Pxl App", lesrooster.getEindDag().toDateString());
			
			Elements rows = document.select("table.asio_basic > tbody > tr"); 		// Vraag alle tablerows op
			Elements dataCells = new Elements();
			int[] offsets = new int[rows.size()];
			
			for (int i = 0; i < rows.get(0).children().size(); i++) 				//Voor elke kolom
			{
				for (int j = 0; j < rows.size(); j++) 								//Voor elke rij
				{
					Element cell = rows.get(j).child(i + offsets[j]); 				//Cel opvragen. Offsets om een 'kolom' naar links te schuiven 
																	  				//wanneer er in de vorige kolom een rowspan was							
					if (cell.hasAttr("rowspan")) 									//Als de cell een rowspan heeft
					{
						int rowspan = Integer.parseInt(cell.attr("rowspan")); 		// Tel de rowspan op bij j, om 'lege' cellen over te slaan.
						
						for (int k = 1; k < rowspan; k++)
						{
							offsets[j + k]--;
						}
						
						j += rowspan - 1;
						
						dataCells.add(cell); 										//Cell heeft een rowspan, en is dus een cel met data
					}
				}
			}
			
			Log.d("Pxl App", "Done");
			
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return null;
		}
		
		return null;
	}
	
	protected void onPostExecute(Void result)
	{
		activity.ontvangLesrooster(lesrooster);
	}	
}
