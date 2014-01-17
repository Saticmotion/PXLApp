package com.mobsoft.pxlapp;

import java.io.IOException;
import com.mobsoft.pxlapp.util.SimpleDateTime;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class LesroostersActivity extends Activity 
{	
	private ProgressDialog progress;
	
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

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar()
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				// This ID represents the Home or Up button. In the case of this
				// activity, the Up button is shown. Use NavUtils to allow users
				// to navigate up one level in the application structure. For
				// more details, see the Navigation pattern on Android Design:
				//
				// http://developer.android.com/design/patterns/navigation.html#up-vs-back
				//
				NavUtils.navigateUpFromSameTask(this);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void vulLesrooster(View view)
	{
		TextView klasText = (TextView) findViewById(R.id.gekozen_klas_string);
		String klas = klasText.getText().toString().toUpperCase().replace(" ", ""); //formatteer klas volgens voorbeeld: 2TING.
		if (klas.equals(""))
		{
			toonFout("", "Geef een klas in");
			return;
		}
		
		try
		{
			SimpleDateTime cacheDatum = CacheManager.getCacheDate(this, "lesrooster" + klas);
			SimpleDateTime vandaag = new SimpleDateTime();
			
			if (cacheDatum.getWeek() == vandaag.getWeek() && cacheDatum.getJaar() == vandaag.getJaar()) //Ook jaar controleren, bugs vermijden rond nieuwjaar
			{
				toonLessenrooster(klas);
			}
			else
			{
				if (isOnline())
				{
					progress = new ProgressDialog(this);
					progress.setMessage("Lesrooster downloaden");
					progress.show();
					new DownloadLesroosterTask(this).execute(klas);
				}
				else
				{
					if (CacheManager.fileExists(this, "lesrooster" + klas))
					{
						toonLessenrooster(klas);
					}
					else
					{
						toonFout("", "Geen internetverbinding");
					}
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void ontvangLesrooster(Lesrooster lesrooster)
	{
		try
		{
			CacheManager.cacheData(this, lesrooster.toCacheString().getBytes(), "lesrooster" + lesrooster.getKlas());
			toonLessenrooster(lesrooster.getKlas());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		progress.dismiss();
	}
	
	private boolean isOnline() 
	{
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) 
	    {
	        return true;
	    }
	    return false;
	}

	private void toonLessenrooster(String klas)
	{
		Intent intent = new Intent(this, LesroosterView.class);
		intent.putExtra("klas", klas);
		startActivity(intent);
	}

	public void toonFout(String titel,String bericht)
	{
		AlertDialog.Builder fout  = new AlertDialog.Builder(this);
		fout.setTitle(titel);
		fout.setMessage(bericht);
		fout.setPositiveButton("OK", null);
		fout.create().show();
	}
}
