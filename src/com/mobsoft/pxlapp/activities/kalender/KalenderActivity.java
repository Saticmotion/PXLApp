package com.mobsoft.pxlapp.activities.kalender;

import java.io.IOException;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Html;
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
		TableRow.LayoutParams lijnOnder = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1);
		TableRow.LayoutParams lijnRechts = new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT);
		
		View lijn = new View(this);
		
		int grijs = getResources().getColor(R.color.grijs);
		int oranje = getResources().getColor(R.color.oranje);
		int roze = getResources().getColor(R.color.roze);
		int groen = getResources().getColor(R.color.green_accent);
		int paars = getResources().getColor(R.color.paars);
		
		TableLayout tabel = (TableLayout) findViewById(R.id.KalenderTable);
		tabel.removeAllViews();

		ArrayList<String> titels = kalender.getTitels();
		TableRow tableRow = new TableRow(this);
		for (String string : titels)
		{
			TextView txtTitel = new TextView(this);
			txtTitel.setTypeface(Typeface.DEFAULT_BOLD);
			txtTitel.setPadding(5, 3, 5, 3);
			txtTitel.setText(string);

			tableRow.addView(txtTitel);
			
			lijn = new View(this);
			lijn.setLayoutParams(lijnRechts);
			lijn.setBackgroundColor(grijs);
			tableRow.addView(lijn);
		}
		tabel.addView(tableRow);

		lijn = new View(this);
		lijn.setLayoutParams(lijnOnder);
		lijn.setBackgroundColor(grijs);
		tabel.addView(lijn);
		
		for (KalenderRij rij : kalender.getRijen())
		{
			TableRow tr = new TableRow(this);

			TextView txtCel = new TextView(this);
			txtCel.setText(rij.getDatum().toString("dd/MM/yy"));
			txtCel.setPadding(5, 3, 5, 3);
			if (rij.getType() == KalenderType.VAKANTIE)
			{
				tr.setBackgroundColor(oranje);
			}
			tr.addView(txtCel);

			lijn = new View(this);
			lijn.setLayoutParams(lijnRechts);
			lijn.setBackgroundColor(grijs);
			tr.addView(lijn);

			for (KalenderCel cel : rij.getCellen())
			{
				txtCel = new TextView(this);
				txtCel.setText(Html.fromHtml(cel.getTekst()));
				txtCel.setPadding(5, 3, 5, 3);
				if (cel.getType() == KalenderType.EXAMEN)
				{
					txtCel.setBackgroundColor(roze);
				}
				else if (cel.getType() == KalenderType.VRIJ)
				{
					txtCel.setBackgroundColor(groen);
				}
				else if (cel.getType() == KalenderType.DELIBERATIE)
				{
					txtCel.setBackgroundColor(paars);
				}
				tr.addView(txtCel);
				lijn = new View(this);
				lijn.setLayoutParams(lijnRechts);
				lijn.setBackgroundColor(grijs);
				tr.addView(lijn);
			}

			tabel.addView(tr);
			lijn = new View(this);
			lijn.setLayoutParams(lijnOnder);
			lijn.setBackgroundColor(grijs);
			tabel.addView(lijn);
		}
	}
}
