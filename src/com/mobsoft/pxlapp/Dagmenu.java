package com.mobsoft.pxlapp;

import java.util.ArrayList;

public class Dagmenu {
	private String dag;
	private ArrayList<String> gerechten;
	
	public Dagmenu(String dag){
		this.dag = dag;
		this.gerechten = new ArrayList<String>();
	}
	public Dagmenu(String dag, ArrayList<String> gerechten){
		this.dag= dag;
		this.gerechten = gerechten;
	}
	public void AddGerecht(String menu){
		this.gerechten.add(menu);
	}
	public ArrayList<String> getGerechten(){
		return gerechten;
	}
	public String getDag(){
		return this.dag;
	}
	
	public String toCacheString(){
		String cacheString;
		cacheString = dag+"-dag-";
		for(String gerecht: gerechten){
			if(gerecht==gerechten.get(0))
				cacheString += gerecht;
			else
				cacheString += "-gerecht-"+gerecht;
		}
		return cacheString;
	}
	public void setGerechten(ArrayList<String> gerechten){
		this.gerechten = gerechten;
	}
}
