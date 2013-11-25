package com.mobsoft.pxlapp;

import java.util.concurrent.ExecutionException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
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
		Lesrooster lesrooster = new Lesrooster();
		
		DownloadLesroosterTask downloadLesrooster = new DownloadLesroosterTask();
		
		Document document;
		
		try 
		{
			document = downloadLesrooster.execute("https://kalender.phl.be/kalenterit2/index.php?kt=lk&yks=&cluokka=2TING&av=131118131124131124&guest=IT%2Fphl&lang=fla&print=arkipaivat").get();
			
			Element header = document.select("h1").first();
			
			TextView text = new TextView(this);
			text.setTextSize(12);
			text.setText(header.text());
			
			setContentView(text);
			
			Log.d("Pxl App", header.text());
			
			
			
			Element table = document.select("table").first();
			Elements rows = table.select("tr");
			
			for (Element row : rows) 
			{
				Elements cells = row.children();
				
				for (Element cell : cells)
				{
					Log.d("Pxl App", cell.html());
				}
			}
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
}
