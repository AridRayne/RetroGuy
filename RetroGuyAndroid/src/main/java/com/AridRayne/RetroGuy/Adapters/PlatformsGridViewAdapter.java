package com.AridRayne.RetroGuy.Adapters;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.AridRayne.RetroGuy.R;
import com.AridRayne.RetroGuy.GamesDB.RetroGuyPlatform;
import com.squareup.picasso.Picasso;

public class PlatformsGridViewAdapter extends ArrayAdapter<RetroGuyPlatform> {
	Context context;
	List<RetroGuyPlatform> items;
	
	public PlatformsGridViewAdapter(Context context, List<RetroGuyPlatform> data) {
		super(context, R.layout.grid_view_row, data);
		this.context = context;
		this.items = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.grid_view_row, parent, false);
		TextView title = (TextView) rowView.findViewById(R.id.gridViewRowText);
		ImageView image = (ImageView) rowView.findViewById(R.id.gridViewRowImage);
		title.setText(items.get(position).getName());
		Picasso.with(context).load(new File(items.get(position).getImageFileName()))
			.placeholder(R.drawable.ic_placeholder)
			.centerInside()
			.fit()
			.into(image);
		return rowView;
	}
}
