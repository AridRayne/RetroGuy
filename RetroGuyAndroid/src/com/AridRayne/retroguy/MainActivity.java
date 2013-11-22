package com.AridRayne.retroguy;

import roboguice.activity.RoboSherlockFragmentActivity;
import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;

public class MainActivity extends RoboSherlockFragmentActivity {

	//@InjectView (R.id.textView1) TextView tv1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ThemeUtils.ApplyTheme(this);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DatabaseHelper dbHelper = new DatabaseHelper(this);
		if (dbHelper.numPlatforms() == 0)
			getSupportFragmentManager().beginTransaction().add(R.id.FrameLayout1, new AddPlatformListFragment()).addToBackStack(null).commit();
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
		case android.R.id.home:
			getSupportFragmentManager().popBackStack();
			break;
		}
		return false;
	}
}
