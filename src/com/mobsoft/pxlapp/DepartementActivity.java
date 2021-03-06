package com.mobsoft.pxlapp;

import com.mobsoft.pxlapp.util.PreferencesUtil;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.Preference;
import android.preference.PreferenceManager;

public class DepartementActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		if (preferences.contains(PreferencesUtil.STANDAARD_KLAS))
		{
			//Als gebruiker geen nieuwe klas wilt, doorsturen naar lesrooster
			if (getIntent().getExtras() == null)
			{
				Intent intent = new Intent(this, LesroosterView.class);
				intent.putExtra("klas", preferences.getString(PreferencesUtil.STANDAARD_KLAS, ""));
				startActivity(intent);
			}
		}
		setContentView(R.layout.activity_departement);
		// Show the Up button in the action bar.
		setupActionBar();
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
		getMenuInflater().inflate(R.menu.departement, menu);
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

	public void openKlassenlijst(View view)
	{
		String guest = "";
		
		switch (view.getId())
		{
			case R.id.eersteRij:
				guest = "HB/phl";
				break;
			case R.id.tweedeRij:
				guest = "lo/phl";
				break;
			case R.id.derdeRij:
				guest = "GZ/phl";
				break;
			case R.id.vierdeRij:
				guest = "IT/phl";
				break;
			case R.id.vijfdeRij:
				guest = "bi/phl";
				break;
		}
		Intent intent = new Intent(this, LesroostersActivity.class);
		intent.putExtra("guest", guest);
		startActivity(intent);
	}
}
