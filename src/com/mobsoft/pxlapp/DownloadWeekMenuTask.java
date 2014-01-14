package com.mobsoft.pxlapp;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mobsoft.pxlapp.util.SimpleDateTime;

import android.app.ProgressDialog;
import android.os.AsyncTask;
<<<<<<< HEAD
import android.os.Looper;
import android.util.Log;
=======
>>>>>>> 0bd66da1f0159aa9d408580c4693c90b65b5eca9

public class DownloadWeekMenuTask extends AsyncTask<String,Void, Void>{
	
	private WeekmenuActivity activiteit;
	private ProgressDialog progress;
	private Weekmenu weekmenu;
	private int e;
	
	public DownloadWeekMenuTask(WeekmenuActivity activiteit){
		this.activiteit = activiteit;
	}
	
	@Override
	protected Void doInBackground(String... waarden) {
		weekmenu = zoekMenu(waarden[0],waarden[1]);
		return null;
	}
	
	/**
	 * haalt de gegevens op en steekt deze in een object van de klasse Weekmenu
	 * @param url de url waar de gegevens staan
	 * @return gevulde Weekmenu
	 */
	public Weekmenu zoekMenu(String url, String campus){
			
			//ophalen html pagina
			weekmenu = null;
			try {
				Document weekmenudoc = Jsoup.connect(url).get();
				Elements dagen = weekmenudoc.select("div[class=catering catering1]"); //selecteren van alle dagen met hun info
				weekmenu = new Weekmenu();
				weekmenu.setCampus(campus);
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
					weekmenu.AddDagmenu(dagmenu); //verschillende dagen + gerechten toevoegen
				}
				
				return weekmenu;
			} catch (IOException e) {
				this.e=2;
			} catch(NullPointerException e){
				this.e=1; //vermijd fouten, anders was looper nodig
			}
			return null;
	}
	public void setProgress(ProgressDialog progress){
		this.progress = progress;
	}
	@Override
	protected void onPostExecute(Void test){
		super.onPostExecute(test);
		progress.dismiss(); //progressdialog dismissen
		if(e==1){
			activiteit.toonFout("Fout", "Helaas, de weekmenu is momenteel niet beschikbaar");
		}else if(e==2){
			activiteit.toonFout("Fout", "Helaas, er is een fout opgetreden, probeer opnieuw");
		}
		else{
			activiteit.setWeekmenu(weekmenu); //weekmenu doorgeven aan activity
			activiteit.vulWeekmenu(); //activiteit scherm laten vullen
		}
	}
	@Override
	protected void onPreExecute(){
		progress.show();
	}
}
