package com.AridRayne.retroguy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.Header;

import roboguice.fragment.RoboSherlockFragment;
import roboguice.inject.InjectView;
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
import com.AridRayne.thegamesdb.lib.image.FanArt;
import com.AridRayne.thegamesdb.lib.image.Image;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;

public class AddPlatformFragment extends RoboSherlockFragment {
	int platformID;
	Platform platform;
	String baseUrl;
	
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
				platform.setName(name.getText().toString());
				platform.setOverview(overview.getText().toString());
				platform.setDeveloper(developer.getText().toString());
				platform.setRating((double) ratingBar.getRating());
				platform.setManufacturer(manufacturer.getText().toString());
				platform.setCPU(cpu.getText().toString());
				platform.setMemory(memory.getText().toString());
				platform.setGraphics(graphics.getText().toString());
				platform.setSound(sound.getText().toString());
				platform.setDisplay(display.getText().toString());
				platform.setMedia(media.getText().toString());
				platform.setMaxControllers(maxControllers.getText().toString());

				File iDirectory;
				
				if (platform.getImages().getBanner() != null) {
					iDirectory = getSherlockActivity().getDir("platform_banner", 0);
					for (Image image : platform.getImages().getBanner()) {
						downloadImage(iDirectory, baseUrl + image.getUrl());
					}
				}
				
				if (platform.getImages().getBoxart() != null) {
					iDirectory = getSherlockActivity().getDir("platform_boxart", 0);
					for (Image image : platform.getImages().getBoxart()) {
						downloadImage(iDirectory, baseUrl + image.getUrl());
					}
				}
				
				if (platform.getImages().getClearlogo() != null) {
					iDirectory = getSherlockActivity().getDir("platform_clearlogo", 0);
					for (Image image : platform.getImages().getClearlogo()) {
						downloadImage(iDirectory, baseUrl + image.getUrl());
					}
				}
				
				if (platform.getImages().getConsoleart() != null) {
					iDirectory = getSherlockActivity().getDir("platform_consoleart", 0);
					for (String url : platform.getImages().getConsoleart()) {
						downloadImage(iDirectory, baseUrl + url);
					}
				}
				
				if (platform.getImages().getControllerart() != null) {
					iDirectory = getSherlockActivity().getDir("platform_controllerart", 0);
					for (String url : platform.getImages().getControllerart()) {
						downloadImage(iDirectory, baseUrl + url);
					}
				}
				
				if (platform.getImages().getFanart() != null) {
					iDirectory = getSherlockActivity().getDir("platform_fanart", 0);
					for (FanArt image : platform.getImages().getFanart()) {
						downloadImage(iDirectory, baseUrl + image.getOriginal().getUrl());
					}
				}
				
				if (platform.getImages().getScreenshot() != null) {
					iDirectory = getSherlockActivity().getDir("platform_screenshot", 0);
					for (FanArt image : platform.getImages().getScreenshot()) {
						downloadImage(iDirectory, baseUrl + image.getOriginal().getUrl());
					}
				}
				//TODO: Commented out for debugging.
//				dbHelper.addPlatform(platform);
			}
		});
		addPlatformButton.setEnabled(false);
		getSherlockActivity().setSupportProgressBarIndeterminateVisibility(true);
		new GetPlatform().execute(platformID);
	}
	
	public void downloadImage(File directory, String imageURL) {
		AsyncHttpClient client = new AsyncHttpClient();
		String[] content = new String[] { "image/png", "image/jpeg" };
		final File i = new File(directory, imageURL.substring(imageURL.lastIndexOf("/")));
		client.get(imageURL, new BinaryHttpResponseHandler(content) {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] binaryData) {
				try {
					FileOutputStream fos = new FileOutputStream(i.getPath());
					fos.write(binaryData);
					fos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] binaryData, Throwable error) {
			}
		});
		
	}
	
	public void setValues(Data<Platform> platform) {
		this.platform = platform.getItem(0);
		name.setText(platform.getItems().get(0).getName());
		overview.setText(platform.getItem(0).getOverview());
		developer.setText(platform.getItem(0).getDeveloper());
		ratingBar.setRating((float) platform.getItem(0).getRating());
		manufacturer.setText(platform.getItem(0).getManufacturer());
		cpu.setText(platform.getItem(0).getCPU());
		memory.setText(platform.getItem(0).getMemory());
		graphics.setText(platform.getItem(0).getGraphics());
		sound.setText(platform.getItem(0).getSound());
		display.setText(platform.getItem(0).getDisplay());
		media.setText(platform.getItem(0).getMedia());
		maxControllers.setText(platform.getItem(0).getMaxControllers());
		baseUrl = platform.getBaseUrl();
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
