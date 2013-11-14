package com.AridRayne.retroguy;

import java.util.List;

import com.AridRayne.thegamesdb.lib.Data;
import com.AridRayne.thegamesdb.lib.PlatformItem;
import com.AridRayne.thegamesdb.lib.Utilities;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PlatformArrayAdapter extends ArrayAdapter<Data<PlatformItem>> {
	Utilities utils;
	Context context;
	List<Data<PlatformItem>> items;
	LayoutInflater layoutInflater;
	
	public PlatformArrayAdapter(Context context, int resource,
			List<Data<PlatformItem>> objects) {
		super(context, resource, objects);
		utils = new Utilities();
		this.context = context;
		this.items = objects;
//		Picasso.with(context).setDebugging(true);
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			row = layoutInflater.inflate(R.layout.add_platform_row, parent, false);
		}
		TextView tvP = (TextView) row.findViewById(R.id.textViewName);
		ImageView imageP = (ImageView) row.findViewById(R.id.icon);
		Data<PlatformItem> item = (Data<PlatformItem>) getItem(position);
		tvP.setText(item.items.get(0).name);
		String url = item.baseUrl;
		if (item.items.get(0).images.consoleart != null) {
			url += item.items.get(0).images.consoleart.get(0);
		}
		else if (item.items.get(0).images.boxart != null) {
			url += item.items.get(0).images.boxart.get(0).url;
		}
		else if (item.items.get(0).images.fanart != null) {
			url += item.items.get(0).images.fanart.get(0).thumb;
		}
		Picasso.with(context).load(url).placeholder(R.drawable.ic_placeholder).fit().centerInside().into(imageP);
		return row;
	}

	@Override
	public void add(Data<PlatformItem> object) {
		items.add(object);
		notifyDataSetChanged();
	}

}
