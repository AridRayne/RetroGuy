package com.AridRayne.retroguy;

import java.util.ArrayList;
import java.util.List;

import roboguice.fragment.RoboSherlockFragment;
import roboguice.inject.InjectView;
import AridRayne.retroguy.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class PlatformsFragment extends RoboSherlockFragment {
	List<GridViewItem> items;
	GridViewAdapter adapter;

	@InjectView (R.id.gridView1) GridView gridView;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		
		items = new ArrayList<GridViewItem>();
		//TODO: DEBUG CODE
		items.add(new GridViewItem("http://thegamesdb.net/banners/platform/consoleart/15.png", "Microsoft Xbox 360"));
		
		adapter = new GridViewAdapter(getSherlockActivity(), items);
		gridView.setAdapter(adapter);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.grid_view_items, container, false);
	}

}
