package com.mobsoft.pxlapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class WeekmenuViewRow extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weekmenu_view_row);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.weekmenu_view_row, menu);
		return true;
	}

}
