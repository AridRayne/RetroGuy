package com.AridRayne.retroguy;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class TabsAdapter extends FragmentPagerAdapter {

	public TabsAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0: // Favorites
			return new PlatformsFragment();

		case 1: // Recently Added
			return new PlatformsFragment();
			
		case 2: // Recently Played
			return new PlatformsFragment();
			
		case 3: // Platforms
			return new PlatformsFragment();
			
		case 4: // Games
			return new PlatformsFragment();
			
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 5;
	}

}
