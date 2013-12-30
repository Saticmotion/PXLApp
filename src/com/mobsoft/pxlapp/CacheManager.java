package com.mobsoft.pxlapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import android.content.Context;
import com.mobsoft.pxlapp.util.SimpleDateTime;

public class CacheManager 
{		
	public void writeToCache(Context context, Cacheable object) throws IOException
	{
		String bestandsnaam = object.getBestandsnaam();
		
		String output = String.valueOf(new SimpleDateTime().getMilliseconden());
		output += object.toCacheString();
		
		File file = new File(context.getCacheDir(), bestandsnaam);
		FileOutputStream os = new FileOutputStream(file);
		
		//TODO: Implement http://stackoverflow.com/questions/9942560/when-to-clear-the-cache-dir-in-android
	}
}
