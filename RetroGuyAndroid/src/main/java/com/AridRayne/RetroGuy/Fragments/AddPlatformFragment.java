package com.AridRayne.RetroGuy.Fragments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import roboguice.fragment.RoboSherlockFragment;
import roboguice.inject.InjectView;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.AridRayne.RetroGuy.DatabaseHelper;
import com.AridRayne.RetroGuy.ImageDownloader;
import com.AridRayne.RetroGuy.R;
import com.AridRayne.RetroGuy.GamesDB.RetroGuyPlatform;
import com.AridRayne.RetroGuy.R.drawable;
import com.AridRayne.RetroGuy.R.id;
import com.AridRayne.RetroGuy.R.layout;
import com.AridRayne.thegamesdb.lib.Data;
import com.AridRayne.thegamesdb.lib.Platform;
import com.AridRayne.thegamesdb.lib.Utilities;
import com.AridRayne.thegamesdb.lib.image.FanArt;
import com.AridRayne.thegamesdb.lib.image.Image;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.squareup.picasso.Picasso;

public class AddPlatformFragment extends RoboSherlockFragment {

	int platformID;
	RetroGuyPlatform platform;
	String baseUrl;
	
	@InjectView
	(R.id.editTextName) TextView name;
	@InjectView
	(R.id.editTextOverview) TextView overview;
	@InjectView
	(R.id.editTextDeveloper) TextView developer;
	@InjectView
	(R.id.ratingBar) RatingBar ratingBar;
	@InjectView
	(R.id.editTextManufacturer) TextView manufacturer;
	@InjectView
	(R.id.editTextCPU) TextView cpu;
	@InjectView
	(R.id.editTextMemory) TextView memory;
	@InjectView
	(R.id.editTextGraphics) TextView graphics;
	@InjectView
	(R.id.editTextSound) TextView sound;
	@InjectView
	(R.id.editTextDisplay) TextView display;
	@InjectView
	(R.id.editTextMedia) TextView media;
	@InjectView
	(R.id.editTextMaxControllers) TextView maxControllers;
	@InjectView
	(R.id.buttonAddPlatform) Button addPlatformButton;
	@InjectView
	(R.id.imagesGallery) Gallery imagesGallery;

	@Override
 	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.add_platform_fragment, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		addPlatformButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO: Add some way to let the user know that we're downloading the images. Maybe a progress dialog?
				DatabaseHelper dbHelper = DatabaseHelper.getInstance();
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

				List<String> urls = new ArrayList<String>();
				
				if (platform.getImages().getBanner() != null) {
					for (Image image : platform.getImages().getBanner()) {
						urls.add(image.getUrl());
					}
				}
				
				if (platform.getImages().getBoxart() != null) {
					for (Image image : platform.getImages().getBoxart()) {
						urls.add(image.getUrl());
					}
				}
				
				if (platform.getImages().getClearLogo() != null) {
					for (Image image : platform.getImages().getClearLogo()) {
						urls.add(image.getUrl());
					}
				}
				
				if (platform.getImages().getConsoleArt() != null) {
					for (String url : platform.getImages().getConsoleArt()) {
						urls.add(url);
					}
				}
				
				if (platform.getImages().getControllerArt() != null) {
					for (String url : platform.getImages().getControllerArt()) {
						urls.add(url);
					}
				}
				
				if (platform.getImages().getFanart() != null) {
					for (FanArt image : platform.getImages().getFanart()) {
						urls.add(image.getOriginal().getUrl());
					}
				}
				
				if (platform.getImages().getScreenshot() != null) {
					for (FanArt image : platform.getImages().getScreenshot()) {
						urls.add(image.getOriginal().getUrl());
					}
				}
				platform.setImageFileName(ImageDownloader.downloadImages(baseUrl, urls, (String)imagesGallery.getSelectedItem(), getSherlockActivity(), true));
				dbHelper.addPlatform(platform);
			}
		});
		addPlatformButton.setEnabled(false);
		getSherlockActivity().setSupportProgressBarIndeterminateVisibility(true);
		new GetPlatform().execute(platformID);
	}
	
	public String downloadImage(File directory, String imageURL) {
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
		return i.getPath();
	}
	
	public void setValues(Data<Platform> platform) {
		this.platform = new RetroGuyPlatform(platform.getItem(0));
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

		List<String> imageURLs = new ArrayList<String>();
		if (this.platform.getImages().getBanner() != null) {
			for (Image image : this.platform.getImages().getBanner()) {
				imageURLs.add(image.getUrl());
			}
		}
		
		if (this.platform.getImages().getBoxart() != null) {
			for (Image image : this.platform.getImages().getBoxart()) {
				imageURLs.add(image.getUrl());
			}
		}
		
		if (this.platform.getImages().getClearLogo() != null) {
			for (Image image : this.platform.getImages().getClearLogo()) {
				imageURLs.add(image.getUrl());
			}
		}
		
		if (this.platform.getImages().getConsoleArt() != null) {
			for (String url : this.platform.getImages().getConsoleArt()) {
				imageURLs.add(url);
			}
		}
		
		if (this.platform.getImages().getControllerArt() != null) {
			for (String url : this.platform.getImages().getControllerArt()) {
				imageURLs.add(url);
			}
		}
		
		if (this.platform.getImages().getFanart() != null) {
			for (FanArt image : this.platform.getImages().getFanart()) {
				imageURLs.add(image.getThumb());
			}
		}
		
		if (this.platform.getImages().getScreenshot() != null) {
			for (FanArt image : this.platform.getImages().getScreenshot()) {
				imageURLs.add(image.getThumb());
			}
		}
		imagesGallery.setAdapter(new ImageAdapter(getSherlockActivity().getApplicationContext(), imageURLs));
	}
	
	public class GetPlatform extends AsyncTask<Integer, Integer, Data<Platform>> {
		@Override
		protected Data<Platform> doInBackground(Integer... params) {
			return Utilities.getInstance().getPlatform(params[0]);
		}

		@Override
		protected void onPostExecute(Data<Platform> result) {
			setValues(result);
			addPlatformButton.setEnabled(true);
			getSherlockActivity().setSupportProgressBarIndeterminateVisibility(false);
		}
	}
	
	public class ImageAdapter extends ArrayAdapter<String> {
		List<String> imageURLs;
		Context context;
		
		public ImageAdapter(Context context, List<String> objects) {
			super(context, 0, objects);
			imageURLs = objects;
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView image = new ImageView(context);
			image.setLayoutParams(new Gallery.LayoutParams(200, 200));
			Picasso.with(context)
				.load(baseUrl + imageURLs.get(position))
				.placeholder(R.drawable.ic_placeholder)
				.centerInside()
				.fit()
				.into(image);
			return image;
		}
	}
}
