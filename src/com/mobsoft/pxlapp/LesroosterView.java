package com.mobsoft.pxlapp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.mobsoft.pxlapp.util.LogUtil;
import com.mobsoft.pxlapp.util.SimpleDateTime;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

public class LesroosterView extends Activity
{
	private ListView listView;
	private Lesrooster lesrooster;
	private ProgressDialog progress;
	private Spinner spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// gegenereerde code
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesrooster_view);
		// Show the Up button in the action bar.
		setupActionBar();
		// einde gegenereerde code

		spinner = (Spinner) findViewById(R.id.dagSpinner);
		listView = (ListView) findViewById(R.id.listViewLesrooster);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
			{
				updateDag(pos);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{

			}
		});

		int dagVanWeek = new SimpleDateTime().getDagVanWeek();
		int spinnerIndex = (dagVanWeek - 2) % 7; //correctie om dag van week om te zetten naar index spinner		
		if (spinnerIndex > 4)
		{
			spinnerIndex = 0; 
		}
			
		spinner.setSelection(spinnerIndex); 	// correctie dag v week om te zetten naar de
																				//index van de spinner

		try
		{
			String cacheString;
			String klas = getIntent().getExtras().getString("klas");
			cacheString = new String(CacheManager.retrieveData(this, "lesrooster" + klas), "UTF-8");
			lesrooster = Lesrooster.lesroosterFromCache(cacheString);
			updateDag(spinner.getSelectedItemPosition());
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
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
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lesrooster_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void updateDag(int dag)
	{
		dag = (dag + 2) % 7; // correctie om index van de spinner om te zetten
								// naar de dag v week
		ArrayList<Les> lessen = lesrooster.getLessen(dag);
		
		if (lessen.size() == 0)
		{
			lessen.add(new Les("Geen les vandaag!", null, null, null, null));
		}

		LesroosterAdapter adapter = new LesroosterAdapter(this, R.layout.activity_lesrooster_view_row, lessen);

		listView.setAdapter(adapter);
	}

	public void downloadOpnieuw(View view)
	{
		if (isOnline())
		{
			progress = new ProgressDialog(this);
			progress.setMessage("Lesrooster downloaden");
			progress.show();
			new DownloadLesroosterTask(this).execute(lesrooster.getKlas());
		}
		else
		{
			toonFout("", "Geen internetverbinding");
		}
	}

	public void ontvangLesrooster(Lesrooster lesrooster)
	{
		try
		{
			CacheManager.cacheData(this, lesrooster.toCacheString().getBytes(), "lesrooster" + lesrooster.getKlas());
			this.lesrooster = lesrooster;
			updateDag(spinner.getSelectedItemPosition());
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

	public void toonFout(String titel, String bericht)
	{
		AlertDialog.Builder fout = new AlertDialog.Builder(this);
		fout.setTitle(titel);
		fout.setMessage(bericht);
		fout.setPositiveButton("OK", null);
		fout.create().show();
	}
}
