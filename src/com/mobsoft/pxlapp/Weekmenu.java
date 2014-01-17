package com.mobsoft.pxlapp;

import java.util.ArrayList;
import java.util.Arrays;

import com.mobsoft.pxlapp.util.SimpleDateTime;

public class Weekmenu{
	
	private String campus;
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
	public void setCampus(String campus){
		this.campus = campus;
	}
	public String toCacheString(){
		String cacheString;
		
		Long cacheDatum = new SimpleDateTime().getMilliseconden();
		Long beginDatum = begindatum.getMilliseconden();
		cacheString = cacheDatum + "\n" + campus + "\n" + beginDatum+"\n";
		for(Dagmenu dag: dagmenus){
			if(dag == this.getFirstDagmenu())
				cacheString+=dag.toCacheString();
			else
				cacheString+="-dagmenu-"+dag.toCacheString();
		}
		return cacheString;
	}
	public static Weekmenu weekmenuFromCache(String cacheString){
		Weekmenu weekmenu = new Weekmenu();
		String waarden[] = cacheString.split("\n");
		
		weekmenu.campus = waarden[1];
		weekmenu.begindatum = new SimpleDateTime(Long.valueOf(waarden[2]));
		
		String dagenString[] = waarden[3].split("-dagmenu-");
		
		for(String dag: dagenString){
			String dagsplit[] = dag.split("-dag-");
			Dagmenu dagmenu = new Dagmenu(dagsplit[0]);
			
			String gerechtenString[] = dagsplit[1].split("-gerecht-");
			ArrayList<String> gerechten = new ArrayList<String>(Arrays.asList(gerechtenString));
			dagmenu.setGerechten(gerechten);
			
			weekmenu.AddDagmenu(dagmenu);
			
		}
		
		return weekmenu;
	}
	public Dagmenu getFirstDagmenu(){
		return dagmenus.get(0);
	}
	public String getCampus(){
		return campus;
	}
	public ArrayList<String> getDagenNaam(){
		ArrayList<String> namen = new ArrayList<String>();
		for(Dagmenu dag: dagmenus){
			namen.add(dag.getDag());
		}
		return namen;
	}
	public Dagmenu getDagmenuAt(int pos){
		return dagmenus.get(pos);
		
	}
	
}
