package com.mobsoft.pxlapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import com.mobsoft.pxlapp.util.LogUtil;
import com.mobsoft.pxlapp.util.RowLayout;
import com.mobsoft.pxlapp.util.SimpleDateTime;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class LesroostersActivity extends Activity 
{	
	private ProgressDialog progress;
	String guest;
	ArrayList<String> klassen = new ArrayList<String>();
	ArrayList<String> gefilterdeKlassen = new ArrayList<String>();
	ViewGroup layout;
	EditText gekozenKlas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesroosters);
		guest = getIntent().getStringExtra("guest");
		
		gekozenKlas = (EditText)findViewById(R.id.gekozen_klas_string);
		gekozenKlas.addTextChangedListener(new TextWatcher()
		{
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{}
			
			@Override
			public void afterTextChanged(Editable s)
			{
				gefilterdeKlassen = filterKlassenLijst(klassen, gekozenKlas.getText().toString());
				updateKlassenlijst();
			}
		});
		
		laadKlassenlijst();
		gefilterdeKlassen = klassen;
		updateKlassenlijst();
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
		String klas = klasText.getText().toString(); //formatteer klas volgens voorbeeld: 2TING.
		if (klas.equals(""))
		{
			toonFout("", "Geef een klas in");
			return;
		}
		
		if(!klassen.contains(klas))
		{
			toonFout("", "Klas bestaat niet");
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

	private void laadKlassenlijst()
	{
		if (CacheManager.fileExists(this, "klassenlijst" + guest))
		{
			try
			{
				byte[] data = CacheManager.retrieveData(this, "klassenlijst" + guest);
				String dataString = new String(data, "UTF-8");
				klassen = new ArrayList<String>(Arrays.asList(dataString.split(",")));
			}
			catch (IOException e)
			{
				toonFout("", "Er is iets fout gegaan, probeer nog eens");
			}
		}
		else
		{
			if (isOnline())
			{
				ProgressDialog progress = new ProgressDialog(this);
				progress.setMessage("Klassenlijst Downloaden");
				progress.show();
				
				DownloadKlassenLijstTask downloadTask = new DownloadKlassenLijstTask(this);
				
				try
				{
					downloadTask.execute(guest).get();
					laadKlassenlijst();
				}
				catch (Exception e)
				{
					toonFout("", "Er is iets fout gegaan, probeer nog eens");
				}
				progress.dismiss();
			}
			else
			{
				toonFout("", "Geen internetverbinding");
			}
		}
	}
	
	public void updateKlassenlijst()
	{
		if (layout == null)
		{
			layout = (ViewGroup) findViewById(R.id.lesrooster_activity_layout);
		}

		RowLayout rowLayout = (RowLayout) findViewById(R.id.row_layout);
		rowLayout.removeAllViews();
		for (String s : gefilterdeKlassen)
		{
			TextView txtKlas = new TextView(this);
			txtKlas.setText(s);
			txtKlas.setTextSize(15);
			txtKlas.setOnClickListener(new TextView.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					gekozenKlas = (EditText)findViewById(R.id.gekozen_klas_string);
					gekozenKlas.setText(((TextView)v).getText());
				}
			});
			rowLayout.addView(txtKlas);
		}
		
	}
	
	public ArrayList<String> filterKlassenLijst(ArrayList<String> klassen, String filter)
	{
		ArrayList<String> gefilterdeLijst = new ArrayList<String>();
		
		filter = filter.toUpperCase();
		
		for (String s : klassen)
		{
			if (s.toUpperCase().contains(filter))
			{
				gefilterdeLijst.add(s);
			}
		}
		
		return gefilterdeLijst;
	}
}
