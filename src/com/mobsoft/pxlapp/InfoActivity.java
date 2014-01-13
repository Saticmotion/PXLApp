package com.mobsoft.pxlapp;

import com.google.android.gms.ads.*;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class InfoActivity extends Activity {

	private AdView adView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_info);	
			setupActionBar();
			
			// Create the adView.
		    adView = new AdView(this);
		    adView.setAdUnitId("ca-app-pub-9643427369143894/6739888360");
		    adView.setAdSize(AdSize.BANNER);

		    // Lookup your LinearLayout assuming it's been given
		    // the attribute android:id="@+id/mainLayout".
		    LinearLayout layout = (LinearLayout)findViewById(R.id.LinearLayoutInfo);

		    // Add the adView to it.
		    layout.addView(adView);

		    // Initiate a generic request.
		    AdRequest adRequest = new AdRequest.Builder().build();

		    // Load the adView with the ad request.
		    adView.loadAd(adRequest);
		    
			ImageView im = (ImageView) findViewById(R.id.imageView1);  
		    im.setImageResource(R.drawable.pxl_logo_noborder);
		    
		    
		    String algemeneInfo = "<u>Hogeschool PXL</u> <br />Elfde-Liniestraat 24 <br />B-3500 Hasselt <br />tel. + 32 11 77 55 55 <br />fax. + 32 11 77 55 59 <br />pxl@pxl.be";
		    String cMine = "<u>Campus C-mine</u><br />C-mine <br />B-3600 Genk <br />tel. + 32 89 30 08 50 <br />fax. + 32 89 30 08 59 <br />infomad@khlim.be";
			String diepenbeek = "<u>Campus Diepenbeek</u> <br />Agoralaan <br />B-3590 Diepenbeek <br />tel. + 32 11 77 54 00";
			String elfde = "<u>Campus Elfde Linie</u> <br />Elfde-Liniestraat 23 - 26 <br />B-3500 Hasselt <br />tel. + 32 11 77 55 00";
			String guffensLaan ="<u>Campus Guffenslaan</u> <br />Guffenslaan 39 <br />B-3500 Hasselt <br />tel. + 32 11 77 52 00";
			String quartier = "<u>Campus Quartier Canal</u> <br />Bootstraat 11 <br />B-3500 Hasselt <br />tel. + 32 11 77 50 60";
			String vilder = "<u>Campus Vildersstraat</u> <br />Vildersstraat 5 <br />B-3500 Hasselt <br />tel. + 32 11 77 53 00";
			
			fillTextView((TextView)findViewById(R.id.textView1), algemeneInfo);
			fillTextView((TextView)findViewById(R.id.textView2), cMine);
			fillTextView((TextView)findViewById(R.id.textView3), diepenbeek);
			fillTextView((TextView)findViewById(R.id.textView4), elfde);
			fillTextView((TextView)findViewById(R.id.textView5), guffensLaan);
			fillTextView((TextView)findViewById(R.id.textView6), quartier);
			fillTextView((TextView)findViewById(R.id.textView7), vilder);
	}	
	
	private void fillTextView(TextView textView, String tekst)
	{
		textView.setText(Html.fromHtml(tekst));
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info, menu);
		return true;
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
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
	
	@Override
	  public void onPause() {
	    adView.pause();
	    super.onPause();
	  }

	  @Override
	  public void onResume() {
	    super.onResume();
	    adView.resume();
	  }

	  @Override
	  public void onDestroy() {
	    adView.destroy();
	    super.onDestroy();
	  }
	

}
