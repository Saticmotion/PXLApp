package com.mobsoft.pxlapp;

import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;


public class InfoActivity extends Activity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_info);	
			
			
			ImageView im = (ImageView) findViewById(R.id.imageView1);  
		    im.setImageResource(R.drawable.pxl_logo_noborder);
		    
		    String algemeneInfo = "<u>Hogeschool PXL</u> <br />Elfde-Liniestraat 24 <br />B-3500 Hasselt <br />tel. + 32 11 77 55 55 <br />fax. + 32 11 77 55 59 <br />pxl@pxl.be";
			String cMine = "<u>Campus C-mine</u><br />C-mine <br />B-3600 Genk <br />tel. + 32 89 30 08 50 <br />fax. + 32 89 30 08 59 <br />infomad@khlim.be";
			String diepenbeek = "<u>Campus Diepenbeek</u> <br />Agoralaan <br />B-3590 Diepenbeek <br />tel. + 32 11 77 54 00";
			String elfde = "<u>Campus Elfde Linie</u> <br />Elfde-Liniestraat 23 - 26 <br />B-3500 Hasselt <br />tel. + 32 11 77 55 00";
			String guffensLaan ="<u>Campus Guffenslaan</u> <br />Guffenslaan 39 <br />B-3500 Hasselt <br />tel. + 32 11 77 52 00";
			String quartier = "<u>Campus Quartier Canal</u> <br />Bootstraat 11 <br />B-3500 Hasselt <br />tel. + 32 11 77 50 60";
			String vilder = "<u>Campus Vildersstraat</u> <br />Vildersstraat 5 <br />B-3500 Hasselt <br />tel. + 32 11 77 53 00";
			
			TextView aInfo = (TextView)findViewById(R.id.textView1);
			aInfo.setText(Html.fromHtml(algemeneInfo));
					
			TextView fCmine = (TextView)findViewById(R.id.textView2);
			fCmine.setText(Html.fromHtml(cMine));
			
			TextView fDiepenbeek = (TextView)findViewById(R.id.textView3);
			fDiepenbeek.setText(Html.fromHtml(diepenbeek));
			
			TextView fElfde = (TextView)findViewById(R.id.textView4);
			fElfde.setText(Html.fromHtml(elfde));
			
			TextView fGuffenslaan = (TextView)findViewById(R.id.textView5);
			fGuffenslaan.setText(Html.fromHtml(guffensLaan));
			
			TextView fQuartier = (TextView)findViewById(R.id.textView6);
			fQuartier.setText(Html.fromHtml(quartier));
			
			TextView fVilder = (TextView)findViewById(R.id.textView7);
			fVilder.setText(Html.fromHtml(vilder));
					
	}		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info, menu);
		return true;
	}

}
