package com.AridRayne.RetroGuy.Adapters;
import com.AridRayne.RetroGuy.Fragments.FavoritesFragment;
import com.AridRayne.RetroGuy.Fragments.GamesFragment;
import com.AridRayne.RetroGuy.Fragments.PlatformsFragment;
import com.AridRayne.RetroGuy.Fragments.RecentlyAddedFragment;
import com.AridRayne.RetroGuy.Fragments.RecentlyPlayedFragment;

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
			return new FavoritesFragment();

		case 1: // Recently Added
			return new RecentlyAddedFragment();
			
		case 2: // Recently Played
			return new RecentlyPlayedFragment();
			
		case 3: // Platforms
			return new PlatformsFragment();
			
		case 4: // Games
			return new GamesFragment();
			
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		return 5;
	}

}
