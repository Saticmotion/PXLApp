package com.mobsoft.pxlapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.mobsoft.pxlapp.util.LogUtil;
import com.mobsoft.pxlapp.util.SimpleDateTime;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class LesroostersActivity extends Activity 
{	
	private DownloadLesroosterTask downloadLesrooster = new DownloadLesroosterTask(this);
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

	public void displayLesrooster(View view)
	{
		SimpleDateTime test = new SimpleDateTime();
		Log.d(LogUtil.PXL_TAG, " " + test.getJaar());
		
		TextView klasText = (TextView) findViewById(R.id.gekozen_klas_string);
		String klas = klasText.getText().toString().toUpperCase().replace("\n ", ""); //formatteer klas volgens voorbeeld: 2TING.
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
		try
		{
			SimpleDateTime cacheDatum = CacheManager.getCacheDate(this, "lesrooster" + klas);
			SimpleDateTime vandaag = new SimpleDateTime();
			if (cacheDatum.getWeek() == vandaag.getWeek() && cacheDatum.getJaar() == vandaag.getJaar()) //Ook jaar controleren, bugs vermijden rond nieuwjaar
			{
				TextView titel = new TextView(this);
				titel.setText("Done");
				
				setContentView(titel);
				toonLessenrooster(klas);
			}
			else
			{
				if (isOnline())
				{
					progress = new ProgressDialog(this);
					progress.setMessage("Lesrooster downloaden");
					progress.show();
					downloadLesrooster.execute(klas);
				}
				else
				{
					TextView text = new TextView(this);
					text.setTextSize(12);
					text.setText("Geen verbinding, oud rooster.");
					
					setContentView(text);
					toonLessenrooster(klas);
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
			Log.d("Pxl App", lesrooster.toCacheString());
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

	public void showError(String title, String message)
	{
		AlertDialog error = new AlertDialog.Builder(this)
				.setTitle(title)
				.setMessage(message)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() 
				{					
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						dialog.cancel();
					}
				})
				.create();
		
		error.show();
	}

	private void toonLessenrooster(String klas)
	{
		Intent intent = new Intent(this, LesroosterView.class);
		intent.putExtra("klas", klas);
		startActivity(intent);
	}
}
