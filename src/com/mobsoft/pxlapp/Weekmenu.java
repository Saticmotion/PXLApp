package com.mobsoft.pxlapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import com.mobsoft.pxlapp.util.SimpleDateTime;

public class Weekmenu{
	
	private static final long serialVersionUID = 1L;
	private SimpleDateTime begindatum;
	private ArrayList<Dagmenu> dagmenus;
	
	public Weekmenu(){
		dagmenus = new ArrayList<Dagmenu>();
	}
	public Weekmenu(SimpleDateTime begindatum, ArrayList<Dagmenu> dagmenus){
		this.begindatum = begindatum;
		this.dagmenus = dagmenus;
	}
	public void AddDagmenu(Dagmenu dagmenu){
		dagmenus.add(dagmenu);
	}
	public void setBegindatum(SimpleDateTime begindatum){
		this.begindatum = begindatum;
	}
	public SimpleDateTime getBegindatum(){
		return begindatum;
	}
	public ArrayList<Dagmenu> getDagmenus(){
		return dagmenus;
	}
	
}
