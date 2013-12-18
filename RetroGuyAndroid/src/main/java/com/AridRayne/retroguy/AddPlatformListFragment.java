package com.AridRayne.retroguy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import roboguice.fragment.RoboSherlockListFragment;
import AridRayne.retroguy.R;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.AridRayne.thegamesdb.lib.PlatformList;
import com.AridRayne.thegamesdb.lib.PlatformListItem;
import com.AridRayne.thegamesdb.lib.Utilities;
import com.squareup.picasso.Picasso;

public class AddPlatformListFragment extends RoboSherlockListFragment {
	List<PlatformListItem> list;
	PlatformAdapter adapter;

	@SuppressWarnings("unchecked")
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		getSherlockActivity().getSupportActionBar().setSubtitle("Add Platform");
		getSherlockActivity().setSupportProgressBarIndeterminateVisibility(true);
		list = new ArrayList<PlatformListItem>();
		adapter = new PlatformAdapter(getActivity());
		setListAdapter(adapter);
		if (savedInstanceState != null) {
			list.addAll((List<PlatformListItem>) savedInstanceState.getSerializable("list"));
			adapter.notifyDataSetChanged();
		}
		else {
			new FillPlatformList().execute();			
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		outState.putSerializable("list", (Serializable) list);
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		AddPlatformFragment addPlatformFragment = new AddPlatformFragment();
		addPlatformFragment.platformID = list.get(position).getId();
		getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout1, addPlatformFragment).addToBackStack(null).commit();
		super.onListItemClick(l, v, position, id);
	}
	
	public class FillPlatformList extends AsyncTask<Object, Object, PlatformList> {

		@Override
		protected PlatformList doInBackground(Object... params) {
			return Utilities.getInstance().getPlatformList();
		}

		@Override
		protected void onPostExecute(PlatformList result) {
			list.addAll(result.getItems());
			adapter.notifyDataSetChanged();
			getSherlockActivity().setSupportProgressBarIndeterminateVisibility(false);
			super.onPostExecute(result);
		}
	}
	
	public class PlatformAdapter extends ArrayAdapter<PlatformListItem> {
		private Context context;
		
		public PlatformAdapter(Context context) {
			super(context, R.layout.add_platform_row, list);
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.add_platform_row, parent, false);
			TextView name = (TextView) rowView.findViewById(R.id.addPlatformTextViewName);
			ImageView icon = (ImageView) rowView.findViewById(R.id.addPlatformIcon);
			name.setText(list.get(position).getName());
			Picasso.with(context).load("http://thegamesdb.net/banners/platform/fanart/thumb/" + list.get(position).getId() + "-1.jpg")
				.placeholder(R.drawable.ic_placeholder)
				.centerInside()
				.fit()
				.into(icon);
			return rowView;
		}
	}
}
