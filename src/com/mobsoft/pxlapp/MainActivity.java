package com.mobsoft.pxlapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.mobsoft.pxlapp.activities.kalender.KalenderActivity;

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void openLesroosters(View view)
	{
		Intent intent = new Intent(this, DepartementActivity.class);
		startActivity(intent);
	}

	public void openInfo(View view)
	{
		Intent intent = new Intent(this, InfoActivity.class);
		startActivity(intent);
	}

	public void openWeekmenu(View view)
	{
		Intent intent = new Intent(this, WeekmenuActivity.class);
		startActivity(intent);
	}

	public void openKalender(View view)
	{
		Intent intent = new Intent(this, KalenderActivity.class);
		startActivity(intent);
	}

}
