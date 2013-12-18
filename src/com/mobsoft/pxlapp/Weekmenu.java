package com.mobsoft.pxlapp;

import java.util.ArrayList;

import com.mobsoft.pxlapp.util.SimpleDateTime;

public class Weekmenu {
	private SimpleDateTime begindatum;
	private ArrayList<Dagmenu> dagmenus;
	
	public Weekmenu(SimpleDateTime begindatum, ArrayList<Dagmenu> dagmenus){
		this.begindatum = begindatum;
		this.dagmenus = dagmenus;
	}
	
}
