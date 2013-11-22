package com.AridRayne.retroguy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import roboguice.fragment.RoboSherlockListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.AridRayne.thegamesdb.lib.PlatformList;
import com.AridRayne.thegamesdb.lib.PlatformListItems;
import com.AridRayne.thegamesdb.lib.Utilities;

public class AddPlatformListFragment extends RoboSherlockListFragment {
	Utilities utils;
	List<PlatformListItems> list;
	ArrayAdapter<PlatformListItems> adapter;

	@SuppressWarnings("unchecked")
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		utils = new Utilities();
		getSherlockActivity().getSupportActionBar().setSubtitle("Add Platform");
		list = new ArrayList<PlatformListItems>();
		adapter = new ArrayAdapter<PlatformListItems>(getActivity(), android.R.layout.simple_list_item_1, list);
		setListAdapter(adapter);
		if (savedInstanceState != null) {
			list.addAll((List<PlatformListItems>) savedInstanceState.getSerializable("list"));
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
		addPlatformFragment.platform = list.get(position).id;
		getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout1, addPlatformFragment).addToBackStack(null).commit();
		super.onListItemClick(l, v, position, id);
	}
	
	public class FillPlatformList extends AsyncTask<Object, Object, PlatformList> {

		@Override
		protected PlatformList doInBackground(Object... params) {
			return utils.getPlatformList();
		}

		@Override
		protected void onPostExecute(PlatformList result) {
			list.addAll(result.items);
			adapter.notifyDataSetChanged();
//			adapter = new ArrayAdapter<PlatformListItems>(getActivity(), android.R.layout.simple_list_item_1, list.items);
//			setListAdapter(adapter);
			super.onPostExecute(result);
		}
	}
}
