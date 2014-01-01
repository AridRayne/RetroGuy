package com.AridRayne.RetroGuy;

import roboguice.activity.RoboSherlockFragmentActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.AridRayne.thegamesdb.lib.Utilities;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends RoboSherlockFragmentActivity {
	SlidingMenu menu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ThemeUtils.applyTheme(this);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		final ActionBar ab = getSupportActionBar();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		setContentView(R.layout.activity_main);
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		Utilities.getInstance().setUserAccountID(sharedPrefs.getString("gamesDBAccountID", ""));
	
		getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, new ViewPagerFragment()).commit();
		menu = new SlidingMenu(this);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setMenu(R.layout.sliding_menu_layout);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
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
		case R.id.action_add:
			startActivity(new Intent(getApplicationContext(), AddPlatformActivity.class));
//			getSupportFragmentManager()
//			.beginTransaction()
//			.add(R.id.mainFrameLayout, new AddPlatformListFragment())
//			.hide(vpf)
//			.addToBackStack(null)
//			.commit();
			return true;
		case android.R.id.home:
			getSupportFragmentManager().popBackStack();
			break;
		}
		return false;
	}

	@Override
	public void onBackPressed() {
		if (menu.isMenuShowing())
			menu.showContent();
		else
			super.onBackPressed();
	}

}
