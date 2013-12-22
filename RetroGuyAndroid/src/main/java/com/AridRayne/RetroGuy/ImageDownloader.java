package com.AridRayne.RetroGuy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.http.Header;

import android.app.ProgressDialog;
import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;

public class ImageDownloader {
	public static String downloadImages(String baseUrl, List<String> imageUrls, String comparisonUrl, Context context) {
		final ProgressDialog progress = new ProgressDialog(context);
		progress.setTitle("Please wait...");
		progress.setMessage("Downloading images...");
		progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progress.setProgress(0);
		progress.setMax(imageUrls.size());
		progress.setCancelable(false);
		progress.show();
		AsyncHttpClient client = new AsyncHttpClient();
		String[] content = new String[] { "image/png", "image/jpeg" };
		String matchedFile = "";
		for (String url : imageUrls) {
			final File file = new File(context.getDir(url.substring(0, url.indexOf("/")), 0), url.substring(url.indexOf("/") + 1).replace("/", "-"));
			if (comparisonUrl.equals(url))
				matchedFile = file.getPath();
			client.get(baseUrl + url, new BinaryHttpResponseHandler(content) {
				@Override
				public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
					try {
						FileOutputStream fos = new FileOutputStream(file);
						fos.write(binaryData);
						fos.close();
						progress.setProgress(progress.getProgress() + 1);
						if (progress.getProgress() >= progress.getMax())
							progress.hide();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
		return matchedFile;
	}
}
