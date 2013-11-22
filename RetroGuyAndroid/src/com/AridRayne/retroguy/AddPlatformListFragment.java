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
//	List<Data<PlatformItem>> items;
	DatabaseHelper dbHelper;
	PlatformList list;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
//		new PopulatePlatforms().execute();
		utils = new Utilities();
//		items = new ArrayList<Data<PlatformItem>>();
		list = new PlatformList();
//		PlatformArrayAdapter adapter = new PlatformArrayAdapter(getActivity(), R.layout.add_platform_row, items);
		dbHelper = new DatabaseHelper(getActivity());
		RoboSherlockFragmentActivity activity = (RoboSherlockFragmentActivity) getActivity();
		activity.getSupportActionBar().setSubtitle("Add Platform");
//		AsyncTask<PlatformArrayAdapter, Integer, PlatformList> task = new GetPlatformList();
		new FillPlatformList().execute();
//		task.execute(adapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		//TODO: Debugging code.
//		Toast.makeText(getActivity(), "Adding: " + items.get(position).items.get(0).name, Toast.LENGTH_LONG).show();
//		dbHelper.addPlatform(items.get(position).items.get(0));
//		ParcelablePlatform pp = new ParcelablePlatform();
//		pp.platform = items.get(position).items.get(0);
		
		AddPlatformFragment addPlatformFragment = new AddPlatformFragment();
		addPlatformFragment.platform = list.items.get(position).id;
//		new GetPlatform().execute(list.items.get(position).id, addPlatformFragment);
		//TODO: This is kinda hackish, add a parcelable and pass the info properly.
//		addPlatformFragment.platformID = list.items.get(position).id;
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
//			PlatformListAdapter listAdapter = new PlatformListAdapter(getActivity(), R.layout.add_platform_row, list);
//			setListAdapter(listAdapter);
			super.onPostExecute(result);
		}
	}
//	
//	public class GetPlatformList extends AsyncTask<PlatformArrayAdapter, Integer, PlatformList> {
//		PlatformArrayAdapter adapter;
//
//		@Override
//		protected PlatformList doInBackground(PlatformArrayAdapter... params) {
//			adapter = params[0];
//			return utils.getPlatformList();
//		}
//
//		@Override
//		protected void onPostExecute(PlatformList result) {
//			int i = 0, numItems = result.items.size();
//			getActivity().setProgressBarVisibility(true);
//			for (PlatformListItems item : result.items) {
//				if (dbHelper.getPlatformItem(item.id) != null)
//					numItems--;
//				else
//					new GetPlatformData().execute(adapter, item, ++i, numItems);
//			}
////			getActivity().setProgressBarVisibility(false);
////			new GetPlatformData().execute(adapter, result.items.get(0));
//			super.onPostExecute(result);
//		}
//		
//	}
//	
//	public class GetPlatformData extends AsyncTask<Object, Integer, Data<PlatformItem>> {
//		PlatformArrayAdapter adapter;
//		int progress;
//		int numItems;
//		
//		@Override
//		protected Data<PlatformItem> doInBackground(Object... params) {
//			adapter = (PlatformArrayAdapter) params[0];
//			PlatformListItems item = (PlatformListItems) params[1];
//			progress = (Integer) params[2];
//			numItems = (Integer) params[3];
//			return utils.PlatformFromID(item.id);
//		}
//
//		@Override
//		protected void onProgressUpdate(Integer... values) {
//			int p = (Window.PROGRESS_END - Window.PROGRESS_START) / values[0] * values[1];
//			if (values[0] == values[1])
//				p = Window.PROGRESS_END;
//			getActivity().setProgress(p);
//		}
//
//		@Override
//		protected void onPostExecute(Data<PlatformItem> result) {
//			adapter.add(result);
//			onProgressUpdate(numItems, progress);
//			super.onPostExecute(result);
//		}
//	}
}
