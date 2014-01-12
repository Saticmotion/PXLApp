package com.mobsoft.pxlapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
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
        holder.txtLesnaam.setText(les.getNaam());
        holder.txtLesuren.setText(les.getStart().toString("hh:mm") + " - " + les.getEinde().toString("hh:mm"));
        holder.txtLokaal.setText(les.getLokaal());
        holder.txtLeerkracht.setText(les.getLeerkracht());
       
       
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
