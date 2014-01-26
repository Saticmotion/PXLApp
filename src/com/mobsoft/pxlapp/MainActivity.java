package com.mobsoft.pxlapp;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mobsoft.pxlapp.activities.kalender.KalenderActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;



public class MainActivity extends Activity 
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Look up the AdView as a resource and load a request.
	    AdView adView = (AdView)this.findViewById(R.id.adView);
	    AdRequest adRequest = new AdRequest.Builder().build();
	    adView.loadAd(adRequest);
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
