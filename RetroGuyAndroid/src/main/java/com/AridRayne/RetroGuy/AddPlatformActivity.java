package com.AridRayne.RetroGuy;

import roboguice.activity.RoboSherlockFragmentActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;

import com.AridRayne.RetroGuy.Fragments.AddPlatformListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;

public class AddPlatformActivity extends RoboSherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ThemeUtils.applyTheme(this);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Add Platform");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_platform);
		getSupportFragmentManager().beginTransaction().add(R.id.add_platform_frame_layout, new AddPlatformListFragment()).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.add_platform, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
