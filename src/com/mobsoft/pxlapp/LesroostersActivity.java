package com.mobsoft.pxlapp;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.ProgressDialog;
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
	DownloadLesroosterTask downloadLesrooster = new DownloadLesroosterTask(this);
	ProgressDialog progress;
		
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
		
		TextView klasText = (TextView) findViewById(R.id.gekozen_klas_string);
		String klas = klasText.getText().toString().toUpperCase().replace(" ", ""); //formatteer klas volgens voorbeeld: 2TING.
		Log.d("Pxl App", klas);
		
		try 
		{
			vulLesrooster(klas);
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

	private void vulLesrooster(String klas) throws InterruptedException, ExecutionException 
	{
		if (isOnline())
		{
			progress = new ProgressDialog(this);
			progress.setTitle("Lesrooster downloaden");
			progress.setMessage("Bezig...");
			progress.show();
			downloadLesrooster.execute("https://kalender.phl.be/kalenterit2/index.php?kt=lk&yks=&cluokka=" + klas + "&av=131118131124131124&guest=IT%2Fphl&lang=fla&print=arkipaivat");
		}
		else
		{
			TextView text = new TextView(this);
			text.setTextSize(12);
			text.setText("No internet connection!");
			
			setContentView(text);
		}
	}
	
	public void ontvangLesrooster(Lesrooster lesrooster)
	{
		progress.dismiss();
		TextView titel = new TextView(this);
		titel.setText("Done");
		
		setContentView(titel);
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
