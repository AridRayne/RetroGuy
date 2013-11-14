package com.AridRayne.retroguy;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboSherlockFragmentActivity;
import roboguice.fragment.RoboSherlockListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.AridRayne.thegamesdb.lib.Data;
import com.AridRayne.thegamesdb.lib.PlatformItem;
import com.AridRayne.thegamesdb.lib.PlatformList;
import com.AridRayne.thegamesdb.lib.PlatformListItems;
import com.AridRayne.thegamesdb.lib.Utilities;

public class AddPlatformListFragment extends RoboSherlockListFragment {
	PlatformList platforms;
	Utilities utils;
	List<Data<PlatformItem>> items;
	DatabaseHelper dbHelper;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
//		new PopulatePlatforms().execute();
		utils = new Utilities();
		items = new ArrayList<Data<PlatformItem>>();
		PlatformArrayAdapter adapter = new PlatformArrayAdapter(getActivity(), R.layout.add_platform_row, items);
		setListAdapter(adapter);
		dbHelper = new DatabaseHelper(getActivity());
		RoboSherlockFragmentActivity activity = (RoboSherlockFragmentActivity) getActivity();
		activity.getSupportActionBar().setSubtitle("Add Platform");
		AsyncTask<PlatformArrayAdapter, Integer, PlatformList> task = new GetPlatformList();
		task.execute(adapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		//TODO: Debugging code.
		Toast.makeText(getActivity(), "Adding: " + items.get(position).items.get(0).name, Toast.LENGTH_LONG).show();
		dbHelper.addPlatform(items.get(position).items.get(0));
		super.onListItemClick(l, v, position, id);
	}

	public class GetPlatformList extends AsyncTask<PlatformArrayAdapter, Integer, PlatformList> {
		PlatformArrayAdapter adapter;

		@Override
		protected PlatformList doInBackground(PlatformArrayAdapter... params) {
			adapter = params[0];
			return utils.getPlatformList();
//			PlatformList l = utils.getPlatformList();
//			PlatformList r = l;
//			for (PlatformListItems item : l.items) {
//				if (dbHelper.getPlatformItem(item.id) == null)
//					r.items.remove(item);
//			}
//			return r;
		}

		@Override
		protected void onPostExecute(PlatformList result) {
			int i = 0, numItems = result.items.size();
			getActivity().setProgressBarVisibility(true);
			for (PlatformListItems item : result.items) {
				if (dbHelper.getPlatformItem(item.id) != null)
					numItems--;
				else
					new GetPlatformData().execute(adapter, item, ++i, numItems);
			}
//			getActivity().setProgressBarVisibility(false);
//			new GetPlatformData().execute(adapter, result.items.get(0));
			super.onPostExecute(result);
		}
		
	}
	
	public class GetPlatformData extends AsyncTask<Object, Integer, Data<PlatformItem>> {
		PlatformArrayAdapter adapter;
		int progress;
		int numItems;
		
		@Override
		protected Data<PlatformItem> doInBackground(Object... params) {
			adapter = (PlatformArrayAdapter) params[0];
			PlatformListItems item = (PlatformListItems) params[1];
			progress = (Integer) params[2];
			numItems = (Integer) params[3];
			return utils.PlatformFromID(item.id);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			int p = (Window.PROGRESS_END - Window.PROGRESS_START) / values[0] * values[1];
			if (values[0] == values[1])
				p = Window.PROGRESS_END;
			getActivity().setProgress(p);
		}

		@Override
		protected void onPostExecute(Data<PlatformItem> result) {
			adapter.add(result);
			onProgressUpdate(numItems, progress);
			super.onPostExecute(result);
		}
	}
	/*
	public class PopulatePlatforms extends AsyncTask<Object, Integer, List<Data<PlatformItem>>> {

		@Override
		protected List<Data<PlatformItem>> doInBackground(Object... params) {
			Utilities utils = new Utilities();
			PlatformList platformList = utils.getPlatformList();
			PlatformArrayAdapter adapter = new PlatformArrayAdapter(getActivity(), R.layout.add_platform_row, platformData);
			setListAdapter(adapter);
			for (PlatformListItems platform : platformList.items) {
				adapter.add(utils.PlatformFromID(platform.id));
				int a = 10;
//				platformData.add(utils.PlatformFromID(platform.id));
			}
//			return platformData;
			return null;
		}

		@Override
		protected void onPostExecute(List<Data<PlatformItem>> result) {
//			PlatformArrayAdapter adapter = new PlatformArrayAdapter(getActivity(), R.layout.add_platform_row, result);
//			setListAdapter(adapter);
//			PlatformListArrayAdapter adapter = new PlatformListArrayAdapter(getActivity(), R.layout.add_platform_row, result.items);
//			List<Data<PlatformItem>> platforms = new ArrayList<Data<PlatformItem>>();
//			for (PlatformListItems platform : result.items) {
//				platforms.add(utils.PlatformFromID(platform.id));
//			}
//			ArrayAdapter<PlatformListItems> adapter = new ArrayAdapter<PlatformListItems>(getActivity(), android.R.layout.simple_list_item_1, result.items);
			super.onPostExecute(result);
		}
		
	}
	*/
}
