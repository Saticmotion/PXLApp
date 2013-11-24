package com.mobsoft.pxlapp;

import java.util.concurrent.ExecutionException;

import org.jsoup.nodes.Document;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

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
			document = downloadLesrooster.execute("http://www.google.com").get();
			
			Log.d("bla", document.body().toString());
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
