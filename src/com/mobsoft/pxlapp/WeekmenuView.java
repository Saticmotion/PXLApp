package com.mobsoft.pxlapp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class WeekmenuView extends Activity {
	private Weekmenu weekmenu;
	private ArrayAdapter<String> gerechtenLijst;
	private Spinner dagen;
	private LinearLayout gerechten;
	private ProgressDialog progress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weekmenu_view);
		
		String campus = getIntent().getExtras().getString("campus");
		this.setTitle(campus);
		String cacheString;
		
		try {
			cacheString = new String(CacheManager.retrieveData(this, "weekmenu" + campus), "UTF-8");
			weekmenu = Weekmenu.weekmenuFromCache(cacheString);
			dagen = (Spinner) findViewById(R.id.spinnerDagmenu);
			updateSpinner();
			gerechten = (LinearLayout) findViewById(R.id.LinearLayoutGerechten);
			dagen.setOnItemSelectedListener(new OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int pos, long id) {
					updateDagmenu(pos);
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			dagen.setSelection(0);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.weekmenu_view, menu);
		return true;
	}
	public void updateDagmenu(int pos){
		gerechten.removeAllViews();
		LinearLayout gerecht=null;
		TextView tekst=null;
		for(String menu: weekmenu.getDagmenuAt(pos).getGerechten()){
			gerecht = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_weekmenu_view_row,null);
			tekst=(TextView) gerecht.findViewById(R.id.GerechtTekst);
			tekst.setText(menu);
			gerechten.addView(gerecht);
		}
	}
	

	public void downloadOpnieuw(View view)
	{
		if (isOnline())
		{
			progress = new ProgressDialog(this);
			progress.setMessage("Weekmenu Downloaden");
			DownloadWeekMenuTask task = new DownloadWeekMenuTask(this);
			task.setProgress(progress);
			task.execute(weekmenu.getCampus());
			
		}
		else
		{
			toonFout("", "Geen internetverbinding");
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
	
	public void toonFout(String titel,String bericht){
		AlertDialog.Builder fout  = new AlertDialog.Builder(this);
		fout.setTitle(titel);
		fout.setMessage(bericht);
		fout.setPositiveButton("OK", null);
		fout.create().show();
	}
	public void updateSpinner(){
		ArrayAdapter<String> dagenNamen = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,weekmenu.getDagenNaam());
		dagenNamen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dagen.setAdapter(dagenNamen);
	}
	public void setWeekmenu(Weekmenu weekmenu){
		this.weekmenu = weekmenu;
		try {
			CacheManager.cacheData(this, weekmenu.toCacheString().getBytes(), "weekmenu" + weekmenu.getCampus());
			updateSpinner();
			dagen.setSelection(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
