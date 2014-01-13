package com.mobsoft.pxlapp;

import java.util.concurrent.ExecutionException;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class WeekmenuActivity extends Activity {
	private DownloadWeekMenuTask weekmenuDownloader;
	private ProgressDialog progress;
	private Weekmenu weekmenu;
	private TextView overzicht;
	
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
	
	/**
	 * methode voor het kiezen van de campus, daarna worden de gegevens gedownload
	 * @param view
	 */
	@SuppressLint("NewApi")
	public void geefMenu(View view){
		
			String gedrukt = ((Button)view).getText().toString();
			//nieuwe scrollview maken waaraan later een textview met info wordt toegevoegd
			overzicht = new TextView(this);
			if(isOnline()){
				overzicht.setText(gedrukt);
				
				progress = new ProgressDialog(this);
				progress.setMessage("Weekmenu downloaden");
				
				weekmenuDownloader = new DownloadWeekMenuTask(this);
				weekmenuDownloader.setProgress(progress); //progress doorgeven aan task zodat deze dismissed kan worden na uitvoering
				
				//uitvoeren task met juiste url
				if(gedrukt.equals("Campus Elfde Linie")){
					weekmenuDownloader.execute("http://www.pxl.be/Pub/Studenten/Voorzieningen-Student/Catering/Weekmenu-Campus-Elfde-Linie.html");
				}else if(gedrukt.equals("Campus Diepenbeek")){
					weekmenuDownloader.execute("http://www.pxl.be/Pub/Studenten/Voorzieningen-Student/Catering/Catering-Weekmenu-Campus-Diepenbeek.html");
				}else{
					weekmenuDownloader.execute("http://www.pxl.be/Pub/Studenten/Voorzieningen-Student/Catering/Catering-Weekmenu-Campus-Vildersstraat.html");
				}
				
				
			}else{
				toonFout("Fout!","Er is geen verbinding met het internet, probeer opnieuw");
			}
	}
	/**
	 * checkt of er een internetverbinding is
	 * @return true or false
	 */
	public boolean isOnline() 
	{
        try
        {
            ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnectedOrConnecting(); 
        }
        catch (Exception e)
        {
            return false;
        }
	}
	/**
	 * vult de view met gegevens(voorlopig)
	 */
	public void vulWeekmenu(){
		ScrollView scroll = new ScrollView(this);
		scroll.addView(overzicht);
		
		for(Dagmenu dag:weekmenu.getDagmenus()){
			overzicht.append("\n"+dag.getDag());
			
			for(String gerecht:dag.getGerechten()){
				overzicht.append("\n -"+gerecht);
			}
			overzicht.append("\n\n");
		}
		setContentView(scroll);
	}
	
	public void setWeekmenu(Weekmenu weekmenu){
		this.weekmenu = weekmenu;
	}
	
	/**
	 * aanmaken foutboodschap
	 * @param titel titel foutboodschap
	 * @param bericht bericht foutboodschap
	 */
	public void toonFout(String titel,String bericht){
		AlertDialog.Builder fout  = new AlertDialog.Builder(this);
		fout.setTitle(titel);
		fout.setMessage(bericht);
		fout.setPositiveButton("OK", null);
		fout.create().show();
	}

}
