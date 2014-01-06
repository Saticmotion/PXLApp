package com.mobsoft.pxlapp;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;


public class InfoActivity extends Activity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_info);	
			
			if (isOnline())
			{
				DownloadInfoTask downloadinfo = new DownloadInfoTask();
				
				try {
					
					Document document = downloadinfo.execute("http://www.pxl.be/Contact.html").get();
					Element content = document.getElementById("content");
					Elements tag = content.select(".algemene_info.algemene_info1 > p");
					
					String pTest ="";
					for (Element x: tag) {
						pTest+=x.text();
					}
					
					
					Log.d("PXL App", pTest);
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			else
			{
				TextView text = new TextView(this);
				text.setTextSize(12);
				text.setText("No internet connection!");
				
				setContentView(text);
			}
	}		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info, menu);
		return true;
	}
	
	public void displayInfo(View view) {
		
		
	}
	
	public boolean isOnline() 
	{
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) 
	    {
	        return true;
	    }
	    return false;
	}


}
