package com.AridRayne.retroguy;

import roboguice.activity.RoboSherlockActivity;
import android.os.AsyncTask;
import android.os.Bundle;

import com.AridRayne.thegamesdb.lib.PlatformList;
import com.AridRayne.thegamesdb.lib.Utilities;
import com.actionbarsherlock.view.Menu;

public class AddPlatformActivity extends RoboSherlockActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ThemeUtils.ApplyTheme(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_platform);
		new getPlatforms().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.add_platform, menu);
		return true;
	}
	public class getPlatforms extends AsyncTask<Object, Integer, PlatformList> {

		@Override
		protected PlatformList doInBackground(Object... arg0) {
			Utilities utils = new Utilities();
			return utils.getPlatformList();
		}

		@Override
		protected void onPostExecute(PlatformList result) {
			// TODO Auto-generated method stub
//			ListAdapter adapter;
//			setListAdapter(adapter);
//			Toast.makeText(getApplicationContext(), result.items.size(), Toast.LENGTH_LONG).show();
			super.onPostExecute(result);
		}
		
	}
}
