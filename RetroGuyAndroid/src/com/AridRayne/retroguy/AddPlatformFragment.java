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
import com.AridRayne.thegamesdb.lib.PlatformItem;
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
				PlatformItem platform = new PlatformItem();
				platform.name = name.getText().toString();
				platform.overview = overview.getText().toString();
				platform.developer = developer.getText().toString();
				platform.rating = (double) ratingBar.getRating();
				platform.manufacturer = manufacturer.getText().toString();
				platform.cpu = cpu.getText().toString();
				platform.memory = memory.getText().toString();
				platform.graphics = graphics.getText().toString();
				platform.sound = sound.getText().toString();
				platform.display = display.getText().toString();
				platform.media = media.getText().toString();
				platform.maxControllers = maxControllers.getText().toString();
				dbHelper.addPlatform(platform);
			}
		});
		addPlatformButton.setEnabled(false);
		getSherlockActivity().setSupportProgressBarIndeterminate(true);
		new GetPlatform().execute(platform);
	}
	
	public void setValues(Data<PlatformItem> platform) {
		name.setText(platform.items.get(0).name);
		overview.setText(StringEscapeUtils.unescapeXml(platform.items.get(0).overview));
		developer.setText(StringEscapeUtils.unescapeXml(platform.items.get(0).developer));
		ratingBar.setRating((float) platform.items.get(0).rating);
		manufacturer.setText(StringEscapeUtils.unescapeXml(platform.items.get(0).manufacturer));
		cpu.setText(StringEscapeUtils.unescapeXml(platform.items.get(0).cpu));
		memory.setText(StringEscapeUtils.unescapeXml(platform.items.get(0).memory));
		graphics.setText(StringEscapeUtils.unescapeXml(platform.items.get(0).graphics));
		sound.setText(StringEscapeUtils.unescapeXml(platform.items.get(0).sound));
		display.setText(StringEscapeUtils.unescapeXml(platform.items.get(0).display));
		media.setText(StringEscapeUtils.unescapeXml(platform.items.get(0).media));
		maxControllers.setText(StringEscapeUtils.unescapeXml(platform.items.get(0).maxControllers));
	}
	
	public class GetPlatform extends AsyncTask<Integer, Integer, Data<PlatformItem>> {
		@Override
		protected Data<PlatformItem> doInBackground(Integer... params) {
			return new Utilities().PlatformFromID(params[0]);
		}

		@Override
		protected void onPostExecute(Data<PlatformItem> result) {
			setValues(result);
			addPlatformButton.setEnabled(true);
			getSherlockActivity().setSupportProgressBarIndeterminate(false);
		}
	}
}
