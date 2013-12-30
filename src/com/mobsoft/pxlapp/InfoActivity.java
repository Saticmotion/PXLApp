package com.mobsoft.pxlapp;

import java.util.concurrent.ExecutionException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;


public class InfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_info);
			
			DownloadInfoTask downloadInfo = new DownloadInfoTask();
			
			
			try {
				Document document = downloadInfo.execute("http://www.pxl.be/Contact.html").get();
				
				Elements tekst = document.select("algemene_info > p");
				
				TextView text = new TextView(this);
				text.setTextSize(12);
				text.setText((CharSequence) tekst);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info, menu);
		return true;
	}

}
