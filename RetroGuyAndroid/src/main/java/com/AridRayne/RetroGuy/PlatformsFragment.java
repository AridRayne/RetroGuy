package com.AridRayne.RetroGuy;

import java.util.ArrayList;
import java.util.List;

import roboguice.fragment.RoboSherlockFragment;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.AridRayne.RetroGuy.GamesDB.RetroGuyPlatform;

public class PlatformsFragment extends RoboSherlockFragment {
	List<RetroGuyPlatform> items;
	GridViewAdapter adapter;
	DatabaseHelper dbHelper;

	@InjectView (R.id.gridView1) GridView gridView;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		dbHelper = new DatabaseHelper(getSherlockActivity());
		items = new ArrayList<RetroGuyPlatform>();
		items.addAll(dbHelper.getAllPlatforms());
		
		adapter = new GridViewAdapter(getSherlockActivity(), items);
		gridView.setAdapter(adapter);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.grid_view_items, container, false);
	}

}
