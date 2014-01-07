package com.mobsoft.pxlapp;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mobsoft.pxlapp.util.SimpleDateTime;

import android.os.AsyncTask;
import android.widget.TextView;

public class DownloadWeekMenuTask extends AsyncTask<String,Void, Weekmenu>{

	@Override
	protected Weekmenu doInBackground(String... url) {
		Weekmenu weekmenu = zoekMenu(url[0]);
		return weekmenu;
	}
	
	public Weekmenu zoekMenu(String url){
			
			//ophalen html pagina
			try {
				Document weekmenudoc = Jsoup.connect(url).get();
				Elements dagen = weekmenudoc.select("div[class=catering catering1]"); //selecteren van alle dagen met hun info
				Weekmenu weekmenu = new Weekmenu();
				Dagmenu dagmenu;
				for (Element dag: dagen){ //per dag de naam van de dag eruithalen en deze opslaan in de klasse Dagmenu
					Element datum = dag.select("h2.date").first();
					dagmenu = new Dagmenu(datum.text());
					
					if(dag==dagen.first()){ //datum van de eerste dag opslaan als de begindatum voor het weekmenu (voor caching later)
						String begindatumstring = datum.text().substring(datum.text().indexOf('(')+1,datum.text().indexOf(')'));
						weekmenu.setBegindatum(SimpleDateTime.parseDate(begindatumstring));
					}
					
					Elements menus = dag.select("div.wysiwyg p:matches(^(?!\\s*$).+)"); //ophalen van de gerechten op de huidige dag en lege <p> tags eruit filteren
					
					for(Element menu: menus){
						dagmenu.AddGerecht(menu.text());
							
							
					}
					weekmenu.AddDagmenu(dagmenu);
				}
				return weekmenu;
			} catch (IOException e) {
				//fout bij verbinden
				e.printStackTrace();
			}
			return null;
	}
}
