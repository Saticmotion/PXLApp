package com.mobsoft.pxlapp.activities.kalender;

import java.io.IOException;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.text.Spanned;
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
		LijnRechts lijnRechts;
		LijnOnder lijnOnder;

		TableLayout tabel = (TableLayout) findViewById(R.id.KalenderTable);
		tabel.removeAllViews();

		ArrayList<String> titels = kalender.getTitels();
		TableRow tableRow = new TableRow(this);
		
		for (int i = 0; i < titels.size(); i++)
		{
			KalenderTextView txtTitel = new KalenderTextView(this, titels.get(i));
			txtTitel.setTypeface(Typeface.DEFAULT_BOLD);

			tableRow.addView(txtTitel);

			if (i < titels.size() - 1)
			{
				lijnRechts = new LijnRechts(this);
				tableRow.addView(lijnRechts);
			}
		}
		
		tabel.addView(tableRow);

		lijnOnder = new LijnOnder(this);
		tabel.addView(lijnOnder);
		
		for (int i = 0; i < kalender.getRijen().size(); i++)
		{
			KalenderRij rij = kalender.getRij(i);
			TableRow tr = new TableRow(this);			

			tr.setBackgroundColor(getCelColor(rij.getType()));

			KalenderTextView txtCel = new KalenderTextView(this, rij.getDatum().toString("dd/MM/yy"));
			
			tr.addView(txtCel);

			lijnRechts = new LijnRechts(this);
			tr.addView(lijnRechts);
			
			for (int j = 0; j < rij.getCellen().size(); j++)
			{
				KalenderCel cel = rij.getCel(j);
				
				if (cel.getTekst().contains("<br />"))
				{
					txtCel = new KalenderTextView(this, Html.fromHtml(cel.getTekst()));
				}
				else
				{
					txtCel = new KalenderTextView(this, cel.getTekst());
				}
				
				txtCel.setBackgroundColor(getCelColor(cel.getType()));
				
				tr.addView(txtCel);

				if (j < kalender.getRij(i).getCellen().size() - 1)
				{
					lijnRechts = new LijnRechts(this);
					tr.addView(lijnRechts);
				}
			}
			tabel.addView(tr);
			
			lijnOnder = new LijnOnder(this);
			tabel.addView(lijnOnder);
		}
	}
	
	private int getCelColor(KalenderType type)
	{
		if (type == KalenderType.EXAMEN)
		{
			return getResources().getColor(R.color.roze2);
		}
		else if (type == KalenderType.VRIJ)
		{
			return getResources().getColor(R.color.groen2);
		}
		else if (type == KalenderType.DELIBERATIE)
		{
			return getResources().getColor(R.color.paars2);
		}
		else if (type == KalenderType.VAKANTIE)
		{
			return getResources().getColor(R.color.oranje2);
		}
		else
		{
			return 0x00000000; //Alpha-kanaal is nul, dus is volledig doorzichtig.
		}
	}

 	private class KalenderTextView extends TextView
	{

		public KalenderTextView(Context context, String text)
		{
			super(context);
			this.setText(text);
			this.setPadding(5, 3, 5, 3);
			this.setTextSize(13);
		}

		public KalenderTextView(Context context, Spanned text)
		{
			super(context);
			this.setText(text);
			this.setPadding(5, 3, 5, 3);
			this.setTextSize(13);
		}

	}

	private class LijnRechts extends View
	{
		TableRow.LayoutParams lijnRechts = new TableRow.LayoutParams(1, TableRow.LayoutParams.MATCH_PARENT);
		int grijs = getResources().getColor(R.color.grijs);
		
		public LijnRechts(Context context)
		{
			super(context);
			this.setLayoutParams(lijnRechts);
			this.setBackgroundColor(grijs);
		}
		
	}
	
	private class LijnOnder extends View
	{
		TableRow.LayoutParams lijnOnder = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1);
		int grijs = getResources().getColor(R.color.grijs);
		
		public LijnOnder(Context context)
		{
			super(context);
			this.setLayoutParams(lijnOnder);
			this.setBackgroundColor(grijs);
		}
		
	}
}
