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
import android.widget.TextView;


public class InfoActivity extends Activity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_info);	
			setupActionBar();
				    
			ImageView im = (ImageView) findViewById(R.id.imageView1);  
		    im.setImageResource(R.drawable.pxl_logo_noborder);
		    
		    
		    String algemeneInfo = "<font color='#58a518'><b><u>Hogeschool PXL</u></b></font> <br />Elfde-Liniestraat 24 <br />B-3500 Hasselt <br />tel. + 32 11 77 55 55 <br />fax. + 32 11 77 55 59 <br />pxl@pxl.be";
		    String cMine = "<font color='#58a518'><b><u>Campus C-mine</u></b></font><br />C-mine <br />B-3600 Genk <br />tel. + 32 89 30 08 50 <br />fax. + 32 89 30 08 59 <br />infomad@khlim.be";
			String diepenbeek = "<font color='#58a518'><b><u>Campus Diepenbeek</u></b></font> <br />Agoralaan <br />B-3590 Diepenbeek <br />tel. + 32 11 77 54 00";
			String elfde = "<font color='#58a518'><b><u>Campus Elfde Linie</u></b></font> <br />Elfde-Liniestraat 23 - 26 <br />B-3500 Hasselt <br />tel. + 32 11 77 55 00";
			String guffensLaan ="<font color='#58a518'><b><u>Campus Guffenslaan</u></b></font> <br />Guffenslaan 39 <br />B-3500 Hasselt <br />tel. + 32 11 77 52 00";
			String quartier = "<font color='#58a518'><b><u>Campus Quartier Canal</u></b></font> <br />Bootstraat 11 <br />B-3500 Hasselt <br />tel. + 32 11 77 50 60";
			String vilder = "<font color='#58a518'><b><u>Campus Vildersstraat</u></b></font> <br />Vildersstraat 5 <br />B-3500 Hasselt <br />tel. + 32 11 77 53 00";
			
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
	
	
	

}
