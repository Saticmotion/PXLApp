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
	 * methode voor het ophalen van de gegevens voor de weekmenu
	 * @param url url van de pagina waar de gegevens zich bevinden
	 * @param waarde textview waaraan de opgehaalde gegevens worden toegevoegd(voorlopig)
	 */
	
	
	@SuppressLint("NewApi")
	public void geefMenu(View view){
		
		try{
			
				String gedrukt = ((Button)view).getText().toString();
				//nieuwe scrollview maken waaraan later een textview met info wordt toegevoegd
				TextView waarde = new TextView(this);
			if(isOnline()){
				waarde.setText(gedrukt);
			
				Weekmenu weekmenu= null;
				progress = new ProgressDialog(this);
				progress.setMessage("Weekmenu downloaden");
				progress.show();
				
				weekmenuDownloader = new DownloadWeekMenuTask();
				if(gedrukt.equals("Campus Elfde Linie")){
					weekmenu=weekmenuDownloader.execute("http://www.pxl.be/Pub/Studenten/Voorzieningen-Student/Catering/Weekmenu-Campus-Elfde-Linie.html").get();
				}else if(gedrukt.equals("Campus Diepenbeek")){
					weekmenu=weekmenuDownloader.execute("http://www.pxl.be/Pub/Studenten/Voorzieningen-Student/Catering/Catering-Weekmenu-Campus-Diepenbeek.html").get();
				}else{
					weekmenu=weekmenuDownloader.execute("http://www.pxl.be/Pub/Studenten/Voorzieningen-Student/Catering/Catering-Weekmenu-Campus-Vildersstraat.html").get();
				}
				vulWeekmenu(weekmenu,waarde);
				
			}else{
				AlertDialog.Builder fout  = new AlertDialog.Builder(this);
				fout.setTitle("Fout !");
				fout.setMessage("Er is geen verbinding met het internet, probeer opnieuw");
				fout.setPositiveButton("OK", null);
				fout.create().show();
			}
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(progress!=null){
				progress.dismiss();
			}
		}
		
		
		
	}
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
	
	public void vulWeekmenu(Weekmenu weekmenu,TextView tekst){
		ScrollView scroll = new ScrollView(this);
		scroll.addView(tekst);
		
		for(Dagmenu dag:weekmenu.getDagmenus()){
			tekst.append("\n"+dag.getDag());
			
			for(String gerecht:dag.getGerechten()){
				tekst.append("\n -"+gerecht);
			}
			tekst.append("\n\n");
		}
		setContentView(scroll);
	}

}
