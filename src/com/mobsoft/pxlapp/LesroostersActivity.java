package com.mobsoft.pxlapp;

import java.util.concurrent.ExecutionException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class LesroostersActivity extends Activity 
{	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesroosters);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lesroosters, menu);
		return true;
	}

	public void displayLesrooster(View view)
	{
		findViewById(R.id.gekozen_klas_string).setVisibility(View.GONE);
		findViewById(R.id.lesrooster_weergeven_button).setVisibility(View.GONE);
		
		Lesrooster lesrooster;
		
		vulLesrooster();
	}

	private void vulLesrooster() 
	{
		if (isOnline())
		{
			Lesrooster lesrooster = new Lesrooster();
			
			DownloadLesroosterTask downloadLesrooster = new DownloadLesroosterTask();
			
			try 
			{
				Document document = downloadLesrooster.execute("https://kalender.phl.be/kalenterit2/index.php?kt=lk&yks=&cluokka=2TING&av=131118131124131124&guest=IT%2Fphl&lang=fla&print=arkipaivat").get();
				
				Element header = document.select("h1").first(); //Vraag de titel van het document op ("Klas {klas} / Week {weeknummer})
				
				TextView text = new TextView(this);
				text.setTextSize(12);
				text.setText(header.text());
				
				setContentView(text); // Voeg de titel toe aan het scherm
				
				Log.d("Pxl App", header.text());
				
				Elements datums = document.select("table th span.hdr_date font"); //Haal de datums op uit de tableheaders
				
				SimpleDateTime beginDag = SimpleDateTime.parseDate(datums.first().text()); //Steek de eerste dag van de week in een Datum-object
				lesrooster.setBeginDag(beginDag);
				SimpleDateTime eindDag = SimpleDateTime.parseDate(datums.last().text()); //Steek de laatste dag van de week in een Datum object
				lesrooster.setEindDag(eindDag);
				
				Log.d("Pxl App", lesrooster.getBeginDag().toDateString());
				Log.d("Pxl App", lesrooster.getEindDag().toDateString());
				
				
				
				Elements rows = document.select("table.asio_basic > tbody > tr"); 		// Vraag alle tablerows op
				Elements dataCells = new Elements();
				int[] offsets = new int[rows.size()];
				
				for (int i = 0; i < rows.get(0).children().size(); i++) 				//Voor elke kolom
				{
					for (int j = 0; j < rows.size(); j++) 								//Voor elke rij
					{
						if (i + offsets[j] < rows.get(j).children().size()) 			//Als de kolomindex niet groter is dan de lengte van de rij
						{
							Element cell = rows.get(j).child(i + offsets[j]); 			//Cel opvragen. Offsets om een 'kolom' naar links te schuiven 
																			  			//wanneer er in de vorige kolom een rowspan was							
							if (cell.hasAttr("rowspan")) 								//Als de cell een rowspan heeft
							{
								int rowspan = Integer.parseInt(cell.attr("rowspan")); 	// Tel de rowspan op bij j, om 'lege' cellen over te slaan.
								
								for (int k = 1; k < rowspan; k++)
								{
									offsets[j + k]--;
								}
								
								j += rowspan - 1;
								
								dataCells.add(cell); 									//Cell heeft een rowspan, en is dus een cel met data
							}
						}
					}
				}
				
				Log.d("Pxl App", "Done");
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			} 
			catch (ExecutionException e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			TextView text = new TextView(this);
			text.setTextSize(12);
			text.setText("No internet connection!");
			
			setContentView(text);
		}
	}	

	public boolean isOnline() 
	{
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) 
	    {
	        return true;
	    }
	    return false;
	}
}
