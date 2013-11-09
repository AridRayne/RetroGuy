package com.AridRayne.retroguy;

import roboguice.activity.RoboSherlockFragmentActivity;
import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends RoboSherlockFragmentActivity {

	//@InjectView (R.id.textView1) TextView tv1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ThemeUtils.ApplyTheme(this);
		super.onCreate(savedInstanceState);
		//TODO: Testing addPlatform fragment.
		setContentView(R.layout.activity_main);
//		Intent addPlatform = new Intent(getApplicationContext(), AddPlatformActivity.class);
		startActivity(new Intent(this, AddPlatformActivity.class));
		/*AddPlatformFragment apf = new AddPlatformFragment();
		FragmentManager man = getFragmentManager();
		FragmentTransaction transaction = man.beginTransaction();
		transaction.add(R.id.FrameLayout1, apf);
		*/
//		new Task().execute(2);
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

/*
	private class Task extends AsyncTask<Integer, Object, Data<PlatformItem>> {

		protected Data<PlatformItem> doInBackground(Integer... params) {
			Utilities util = new Utilities();
			return util.PlatformFromID(2);
		}
		
		protected void onPostExecute(Data<PlatformItem> result) {
			tv1.setText(StringEscapeUtils.unescapeXml(result.items.get(0).name));
		}
	}
	*/
}
