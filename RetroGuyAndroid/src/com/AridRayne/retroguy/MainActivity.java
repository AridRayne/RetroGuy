package com.AridRayne.retroguy;

import org.apache.commons.lang3.StringEscapeUtils;

import roboguice.activity.RoboSherlockFragmentActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

import com.AridRayne.thegamesdb.lib.Data;
import com.AridRayne.thegamesdb.lib.PlatformItem;
import com.AridRayne.thegamesdb.lib.Utilities;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends RoboSherlockFragmentActivity {

	@InjectView (R.id.textView1) TextView tv1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String theme = sharedPrefs.getString("appTheme", "default");
		if (theme.equals("light")) {
			setTheme(com.roboguice.R.style.Theme_Sherlock_Light);
		}
		else if (theme.equals("lightdarkactionbar")) {
			setTheme(com.roboguice.R.style.Theme_Sherlock_Light_DarkActionBar);
		}
		else {
			setTheme(com.roboguice.R.style.Theme_Sherlock);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new Task().execute(2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
			return true;
		}
		return false;
	}


	private class Task extends AsyncTask<Integer, Object, Data<PlatformItem>> {

		protected Data<PlatformItem> doInBackground(Integer... params) {
			Utilities util = new Utilities();
			return util.PlatformFromID(2);
		}
		
		protected void onPostExecute(Data<PlatformItem> result) {
			tv1.setText(StringEscapeUtils.unescapeXml(result.items.get(0).name));
		}
	}
}
