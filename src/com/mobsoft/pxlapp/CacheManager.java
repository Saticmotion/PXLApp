package com.mobsoft.pxlapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import com.mobsoft.pxlapp.util.SimpleDateTime;

import android.content.Context;

public class CacheManager
{

	private static final long MAX_SIZE = 5242880L; // 5MB

	private CacheManager()
	{

	}

	public static void cacheData(Context context, byte[] data, String name) throws IOException
	{

		File cacheDir = context.getCacheDir();
		long size = getDirSize(cacheDir);
		long newSize = data.length + size;

		if (newSize > MAX_SIZE)
		{
			cleanDir(cacheDir, newSize - MAX_SIZE);
		}

		File file = new File(cacheDir, name);
		FileOutputStream os = new FileOutputStream(file);
		try
		{
			os.write(data);
		}
		finally
		{
			os.flush();
			os.close();
		}
	}

	/**
	 * Returns the cached data as a byte-array
	 * @param context The context is necessary to access the CacheDir
	 * @param name The name of the file to get the cache age of
	 * @return The data
	 * @throws IOException Thrown when there is an error accessing the file.
	 */
	public static byte[] retrieveData(Context context, String name) throws IOException
	{
		File cacheDir = context.getCacheDir();
		File file = new File(cacheDir, name);

		if (!file.exists())
		{
			// Data doesn't exist
			return null;
		}

		byte[] data = new byte[(int) file.length()];
		FileInputStream is = new FileInputStream(file);
		try
		{
			is.read(data);
		}
		finally
		{
			is.close();
		}

		return data;
	}

	/**
	 * Returns the age of the data in milliseconds
	 * @param context The context is necessary to access the CacheDir
	 * @param name The name of the file to get the cache age of
	 * @return The age of the cached data
	 * @throws IOException Thrown when there is an error accessing the file.
	 */
	public static Long getCacheAge(Context context, String name) throws IOException
	{
		File cacheDir = context.getCacheDir();
		File file = new File(cacheDir, name);

		if (!file.exists())
		{
			// Data doesn't exist
			return null;
		}

		byte[] data = new byte[(int) file.length()];
		FileInputStream is = new FileInputStream(file);
		try
		{
			is.read(data);
		}
		finally
		{
			is.close();
		}
		
		String dataString = new String(data, "UTF-8");
		String[] parts = dataString.split("\n");
		
		SimpleDateTime date = new SimpleDateTime(Long.valueOf(parts[0]));
		
		Long difference = new SimpleDateTime().getMilliseconden() - date.getMilliseconden();
		
		return difference;
	}
	
	/**
	 * Returns the date the data was cached.
	 * @param context The context is necessary to access the CacheDir
	 * @param name The name of the file to get the cache date of
	 * @return The date the data was cached
	 * @throws IOException Thrown when there is an error accessing the file.
	 */
	public static SimpleDateTime getCacheDate(Context context, String name) throws IOException
	{
		File cacheDir = context.getCacheDir();
		File file = new File(cacheDir, name);

		if (!file.exists())
		{
			// Data doesn't exist
			return null;
		}

		byte[] data = new byte[(int) file.length()];
		FileInputStream is = new FileInputStream(file);
		try
		{
			is.read(data);
		}
		finally
		{
			is.close();
		}
		
		String dataString = new String(data, "UTF-8");
		String[] parts = dataString.split("\n");
		
		SimpleDateTime date = new SimpleDateTime(Long.valueOf(parts[0]));
		
		
		return date;
	}
	
	private static void cleanDir(File dir, long bytes)
	{

		long bytesDeleted = 0;
		File[] files = dir.listFiles();

		for (File file : files)
		{
			bytesDeleted += file.length();
			file.delete();

			if (bytesDeleted >= bytes)
			{
				break;
			}
		}
	}

	private static long getDirSize(File dir)
	{

		long size = 0;
		File[] files = dir.listFiles();

		for (File file : files)
		{
			if (file.isFile())
			{
				size += file.length();
			}
		}

		return size;
	}
}