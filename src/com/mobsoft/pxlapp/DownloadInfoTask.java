/**
 * 
 */
package com.mobsoft.pxlapp;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

/**
 * @author Simon
 *
 */
public class DownloadInfoTask extends AsyncTask<String, Void, Document> {

	@Override
	protected Document doInBackground(String... URLs) 
	{
		Document document;
		String URL = URLs[0];
		
		try 
		{			
			document = Jsoup.connect(URL).get();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return null;
		}
		
		return document;
	}
	
	protected void onPostExecute(Long... result)
	{
		
	}
	
}
