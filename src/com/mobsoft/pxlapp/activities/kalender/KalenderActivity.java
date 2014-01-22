package com.mobsoft.pxlapp.activities.kalender;

import java.io.IOException;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.mobsoft.pxlapp.R;

public class KalenderActivity extends Activity
{

	private Spinner spinner;
	private Kalender kalenderVolledig;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kalender);
		// Show the Up button in the action bar.
		setupActionBar();
		toonKalender();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar()
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kalender, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
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

	public void toonKalender()
	{
		Kalender kalender;

		spinner = (Spinner) findViewById(R.id.soortSpinner);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
			{
				String waarde = spinner.getSelectedItem().toString();
				updateKalender(waarde);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{

			}
		});

		try
		{
			this.kalenderVolledig = Kalender.kalenderVanBestand(this);

			tekenKalender(kalenderVolledig);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void updateKalender(String waarde)
	{
		Kalender kalender = kalenderVolledig.filterKalender(waarde);
		tekenKalender(kalender);
	}

	private void tekenKalender(Kalender kalender)
	{
		TableLayout tabel = (TableLayout) findViewById(R.id.KalenderTable);
		tabel.removeAllViews();

		ArrayList<String> kTitels = kalender.getTitels();
		TableRow tableRow = new TableRow(this);
		for (String string : kTitels)
		{

			TextView txtTitel = new TextView(this);
			txtTitel.setText(string);
			txtTitel.setTypeface(Typeface.DEFAULT_BOLD);
			tableRow.addView(txtTitel);

		}
		tabel.addView(tableRow);

		for (KalenderRij rij : kalender.getRijen())
		{
			TableRow tr = new TableRow(this);
			
			TextView txtCel = new TextView(this);
			txtCel.setText(rij.getDatum().toString("dd/MM/yy"));
			tr.addView(txtCel);

			for (KalenderCel cel : rij.getCellen())
			{
				txtCel = new TextView(this);
				txtCel.setText(cel.getTekst());
				tr.addView(txtCel);
			}
			tabel.addView(tr);
		}
	}
}
