package com.mobsoft.pxlapp;

import java.util.Calendar;
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

public class LesroostersActivity extends Activity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesroosters);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
			
			Document document;
			
			try 
			{
				document = downloadLesrooster.execute("https://kalender.phl.be/kalenterit2/index.php?kt=lk&yks=&cluokka=2TING&av=131118131124131124&guest=IT%2Fphl&lang=fla&print=arkipaivat").get();
				
				Element header = document.select("h1").first();
				
	//			TextView text = new TextView(this);
	//			text.setTextSize(12);
	//			text.setText(header.text());
	//			
	//			setContentView(text);
	//			
	//			Log.d("Pxl App", header.text());
				
				Elements datums = document.select("table th span.hdr_date font");
				
	//			ArrayList<Calendar> datumsList = new ArrayList<Calendar>();
				
				SimpleDateTime beginDag = SimpleDateTime.parseDate(datums.first().text());
				lesrooster.setBeginDag(beginDag);
				SimpleDateTime eindDag = SimpleDateTime.parseDate(datums.last().text());
				lesrooster.setEindDag(eindDag);
				
				Log.d("Pxl App", lesrooster.getBeginDag().toDateString());
				Log.d("Pxl App", lesrooster.getEindDag().toDateString());
				
				
				
				Elements rows = document.select("table.asio_basic > tbody > tr");
				Log.d("Pxl App", String.valueOf(rows.size()));
				Elements dataCells = new Elements();
				
				for (int i = 0; i < rows.size(); i++)
				{
					Log.d("Pxl App", String.valueOf(rows.get(i).childNodeSize()));
					
					for (int j = 0; j < rows.get(i).children().size(); j++)
					{
						Element cell = rows.get(i).child(j);
						
						if (cell.hasAttr("rowspan"))
						{
							j += Integer.parseInt(cell.attr("rowspan"));
							dataCells.add(cell);
						}
					}
				}
				
				Log.d("Pxl App", dataCells.toString());
				
				Log.d("Pxl App", "Done");
				
	//			TextView lessen = new TextView(this);
	//			lessen.setTextSize(12);
	//			lessen.setText(content.text());
	//			
	//			setContentView(lessen);
	//			
	//			Log.d("Pxl App", content.html());
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (ExecutionException e) 
			{
				// TODO Auto-generated catch block
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
