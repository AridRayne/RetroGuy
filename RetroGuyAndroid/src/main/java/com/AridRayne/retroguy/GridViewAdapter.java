package com.AridRayne.retroguy;

import java.util.List;

import AridRayne.retroguy.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class GridViewAdapter extends ArrayAdapter<GridViewItem> {
	Context context;
	List<GridViewItem> items;
	
	public GridViewAdapter(Context context, List<GridViewItem> data) {
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
		title.setText(items.get(position).getTitle());
		Picasso.with(context).load(items.get(position).getFileName())
			.placeholder(R.drawable.ic_placeholder)
			.centerInside()
			.fit()
			.into(image);
		return rowView;
	}
}
