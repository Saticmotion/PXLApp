package com.mobsoft.pxlapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LesroosterAdapter extends ArrayAdapter<Les> 
{
	Context context;
	int layoutResourceId;
	ArrayList<Les> lessen = null;

	public LesroosterAdapter(Context context, int resource, ArrayList<Les> lessen) 
	{
		super(context, resource, lessen);
		this.context = context;
		this.layoutResourceId = resource;
		this.lessen = lessen;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View row = convertView;
        LesHolder holder = null;
       
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
           
            holder = new LesHolder();
            holder.txtLesnaam = (TextView)row.findViewById(R.id.txtLesnaam);
            holder.txtLesuren = (TextView)row.findViewById(R.id.txtLesuren);
            holder.txtLokaal = (TextView)row.findViewById(R.id.txtLokaal);
            holder.txtLeerkracht = (TextView)row.findViewById(R.id.txtLeerkracht);
           
            row.setTag(holder);
        }
        else
        {
            holder = (LesHolder)row.getTag();
        }
       
        Les les = lessen.get(position);
        
        //Controleren of deze items null zijn, indien ja een lege string invoegen.
        if (les.getNaam() != null)
        {
        	holder.txtLesnaam.setText(Html.fromHtml(les.getNaam()));
        }
        else
        {
        	holder.txtLesnaam.setText("");
        }
        
        
        if (les.getStart() != null && les.getEinde() != null)
        {
        	holder.txtLesuren.setText(les.getStart().toString("HH:mm") + " - " + les.getEinde().toString("HH:mm"));
        }
        else
        {
        	holder.txtLesuren.setText("");
        }
        
        
        if (les.getLokaal() != null)
        {
        	holder.txtLokaal.setText(les.getLokaal());
        }
        else
        {
        	holder.txtLokaal.setText("");
        }
        
        if (les.getLeerkracht() != null)
        {
        	holder.txtLeerkracht.setText(les.getLeerkracht());
        }
        else
        {
        	holder.txtLeerkracht.setText("");
        }       
       
        return row;
	}
	
	static class LesHolder
	{
		TextView txtLesnaam;
		TextView txtLesuren;
		TextView txtLokaal;
		TextView txtLeerkracht;
		
	}

}
