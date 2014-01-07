package com.mobsoft.pxlapp;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mobsoft.pxlapp.util.SimpleDateTime;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class WeekmenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weekmenu);
		
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.weekmenu, menu);
		return true;
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
	public void zoekMenu(String url, TextView waarde){
		
		try {
			Document weekmenudoc = Jsoup.connect(url).get();
			Elements dagen = weekmenudoc.select("div[class=catering catering1]");
			Weekmenu weekmenu = new Weekmenu();
			Dagmenu dagmenu;
			for (Element dag: dagen){
				Element datum = dag.select("h2.date").first();
				dagmenu = new Dagmenu(datum.text());
				
				if(dag==dagen.first()){
					String begindatumstring = datum.text().substring(datum.text().indexOf('(')+1,datum.text().indexOf(')'));
					weekmenu.setBegindatum(SimpleDateTime.parseDate(begindatumstring));
				}
				
				Elements menus = dag.select("div.wysiwyg p:matches(^(?!\\s*$).+)");
				waarde.append("\n"+dagmenu.getDag());
				for(Element menu: menus){
					dagmenu.AddGerecht(menu.text());
						
						
				}
				for(String gerecht: dagmenu.getGerechten()){
					waarde.append("\n"+gerecht);
				}
				weekmenu.AddDagmenu(dagmenu);
				waarde.append("\n");
			}
		} catch (IOException e) {
			waarde.append("\n geen verbinding");
			e.printStackTrace();
		}
	}
	
	@SuppressLint("NewApi")
	public void geefMenu(View view){
		findViewById(R.id.button_menuElfde).setVisibility(View.GONE);
		String gedrukt = ((Button)view).getText().toString();
		
		TextView waarde = new TextView(this);
		waarde.setText(gedrukt);
		setContentView(waarde);
		if(gedrukt.equals("Campus Elfde Linie")){
			zoekMenu("http://www.pxl.be/Pub/Studenten/Voorzieningen-Student/Catering/Weekmenu-Campus-Elfde-Linie.html",waarde);
		}else if(gedrukt.equals("Campus Diepenbeek")){
			zoekMenu("http://www.pxl.be/Pub/Studenten/Voorzieningen-Student/Catering/Catering-Weekmenu-Campus-Diepenbeek.html",waarde);
		}else{
			zoekMenu("http://www.pxl.be/Pub/Studenten/Voorzieningen-Student/Catering/Catering-Weekmenu-Campus-Vildersstraat.html",waarde);
		}
		
	}

}
