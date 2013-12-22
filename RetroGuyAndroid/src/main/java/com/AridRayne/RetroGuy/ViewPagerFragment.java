package com.AridRayne.RetroGuy;

import roboguice.fragment.RoboSherlockFragment;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;

public class ViewPagerFragment extends RoboSherlockFragment implements TabListener {
	@InjectView (R.id.ViewPager1) ViewPager viewPager;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		final ActionBar ab = getSherlockActivity().getSupportActionBar();
		if (savedInstanceState != null) {
			int a = 10;
		}
		else {
			ActionBar.Tab favoritesTab = ab.newTab().setText("Favorites").setTabListener(this);
			ab.addTab(favoritesTab);
			ActionBar.Tab recentlyAddedTab = ab.newTab().setText("Recently Added").setTabListener(this);
			ab.addTab(recentlyAddedTab);
			ActionBar.Tab recentlyPlayedTab = ab.newTab().setText("Recently Played").setTabListener(this);
			ab.addTab(recentlyPlayedTab);
			ActionBar.Tab platformsTab = ab.newTab().setText("Platforms").setTabListener(this);
			ab.addTab(platformsTab);
			ActionBar.Tab gamesTab = ab.newTab().setText("Games").setTabListener(this);
			ab.addTab(gamesTab);
			TabsAdapter adapter = new TabsAdapter(getSherlockActivity().getSupportFragmentManager());
			viewPager.setAdapter(adapter);
			
			viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
			{
				@Override
				public void onPageSelected(int position) {
					ab.setSelectedNavigationItem(position);
				}
			});
		}
		
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if (hidden)
			getSherlockActivity().getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		else
			getSherlockActivity().getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		super.onHiddenChanged(hidden);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.viewpager_fragment, container, false);
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction transaction) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

}
