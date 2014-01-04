package com.AridRayne.RetroGuy.Fragments;

import com.AridRayne.RetroGuy.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import roboguice.fragment.RoboSherlockFragment;

public class RecentlyPlayedFragment extends RoboSherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.recently_played_fragment, container, false);
	}

}
