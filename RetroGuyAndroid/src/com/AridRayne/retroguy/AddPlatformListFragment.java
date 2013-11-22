package com.AridRayne.retroguy;

import roboguice.activity.RoboSherlockFragmentActivity;
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
	PlatformList platforms;
	Utilities utils;
	DatabaseHelper dbHelper;
	PlatformList list;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		utils = new Utilities();
		list = new PlatformList();
		dbHelper = new DatabaseHelper(getActivity());
		RoboSherlockFragmentActivity activity = (RoboSherlockFragmentActivity) getActivity();
		activity.getSupportActionBar().setSubtitle("Add Platform");
		new FillPlatformList().execute();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		AddPlatformFragment addPlatformFragment = new AddPlatformFragment();
		addPlatformFragment.platform = list.items.get(position).id;
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
			list = result;
			ArrayAdapter<PlatformListItems> adapter = new ArrayAdapter<PlatformListItems>(getActivity(), android.R.layout.simple_list_item_1, list.items);
			setListAdapter(adapter);
			super.onPostExecute(result);
		}
	}
}
