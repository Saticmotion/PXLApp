package com.mobsoft.pxlapp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class LesroosterView extends Activity
{
	private ListView listView;
	private Lesrooster lesrooster;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		//gegenereerde code
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesrooster_view);
		// Show the Up button in the action bar.
		setupActionBar();
		// einde gegenereerde code
		
		Spinner spinner = (Spinner) findViewById(R.id.dagSpinner);
		listView = (ListView)findViewById(R.id.listViewLesrooster);
		
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
		
		spinner.setSelection(0);
		
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
	

	private void updateDag(int dag)
	{
		dag = (dag + 2) % 7;
		ArrayList<Les> lessen = lesrooster.getLessen(dag);
		
		LesroosterAdapter adapter = new LesroosterAdapter(this, R.layout.activity_lesrooster_view_row, lessen);
		
		listView.setAdapter(adapter);
	}
}
