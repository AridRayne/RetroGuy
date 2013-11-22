package com.AridRayne.retroguy;

import org.apache.commons.lang3.StringEscapeUtils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.AridRayne.thegamesdb.lib.Data;
import com.AridRayne.thegamesdb.lib.Platform;
import com.AridRayne.thegamesdb.lib.Utilities;

import roboguice.fragment.RoboSherlockFragment;
import roboguice.inject.InjectView;

public class AddPlatformFragment extends RoboSherlockFragment {
	int platform;
	
	@InjectView (R.id.editTextName) TextView name;
	@InjectView (R.id.editTextOverview) TextView overview;
	@InjectView (R.id.editTextDeveloper) TextView developer;
	@InjectView (R.id.ratingBar) RatingBar ratingBar;
	@InjectView (R.id.editTextManufacturer) TextView manufacturer;
	@InjectView (R.id.editTextCPU) TextView cpu;
	@InjectView (R.id.editTextMemory) TextView memory;
	@InjectView (R.id.editTextGraphics) TextView graphics;
	@InjectView (R.id.editTextSound) TextView sound;
	@InjectView (R.id.editTextDisplay) TextView display;
	@InjectView (R.id.editTextMedia) TextView media;
	@InjectView (R.id.editTextMaxControllers) TextView maxControllers;
	@InjectView (R.id.buttonAddPlatform) Button addPlatformButton;

	@Override
 	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.add_platform, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		addPlatformButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
				Platform platform = new Platform();
				platform.setName(name.getText().toString());
				platform.setOverview(overview.getText().toString());
				platform.setDeveloper(developer.getText().toString());
				platform.setRating((double) ratingBar.getRating());
				platform.setManufacturer(manufacturer.getText().toString());
				platform.setCpu(cpu.getText().toString());
				platform.setMemory(memory.getText().toString());
				platform.setGraphics(graphics.getText().toString());
				platform.setSound(sound.getText().toString());
				platform.setDisplay(display.getText().toString());
				platform.setMedia(media.getText().toString());
				platform.setMaxControllers(maxControllers.getText().toString());
				dbHelper.addPlatform(platform);
			}
		});
		addPlatformButton.setEnabled(false);
		getSherlockActivity().setSupportProgressBarIndeterminateVisibility(true);
		new GetPlatform().execute(platform);
	}
	
	public void setValues(Data<Platform> platform) {
		name.setText(platform.getItems().get(0).getName());
		overview.setText(StringEscapeUtils.unescapeXml(platform.getItem(0).getOverview()));
		developer.setText(StringEscapeUtils.unescapeXml(platform.getItem(0).getDeveloper()));
		ratingBar.setRating((float) platform.getItem(0).getRating());
		manufacturer.setText(StringEscapeUtils.unescapeXml(platform.getItem(0).getManufacturer()));
		cpu.setText(StringEscapeUtils.unescapeXml(platform.getItem(0).getCpu()));
		memory.setText(StringEscapeUtils.unescapeXml(platform.getItem(0).getMemory()));
		graphics.setText(StringEscapeUtils.unescapeXml(platform.getItem(0).getGraphics()));
		sound.setText(StringEscapeUtils.unescapeXml(platform.getItem(0).getSound()));
		display.setText(StringEscapeUtils.unescapeXml(platform.getItem(0).getDisplay()));
		media.setText(StringEscapeUtils.unescapeXml(platform.getItem(0).getMedia()));
		maxControllers.setText(StringEscapeUtils.unescapeXml(platform.getItem(0).getMaxControllers()));
	}
	
	public class GetPlatform extends AsyncTask<Integer, Integer, Data<Platform>> {
		@Override
		protected Data<Platform> doInBackground(Integer... params) {
			return Utilities.getInstance().getPlatformFromID(params[0]);
		}

		@Override
		protected void onPostExecute(Data<Platform> result) {
			setValues(result);
			addPlatformButton.setEnabled(true);
			getSherlockActivity().setSupportProgressBarIndeterminateVisibility(false);
		}
	}
}
