package com.AridRayne.retroguy;

import org.apache.commons.lang3.StringEscapeUtils;

import roboguice.activity.RoboSherlockFragmentActivity;
import roboguice.inject.InjectView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.AridRayne.thegamesdb.lib.Data;
import com.AridRayne.thegamesdb.lib.PlatformItem;
import com.AridRayne.thegamesdb.lib.Utilities;
import com.actionbarsherlock.view.Menu;

public class MainActivity extends RoboSherlockFragmentActivity {

	@InjectView (R.id.textView1) TextView tv1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new Task().execute(2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	private class Task extends AsyncTask<Integer, Object, Data> {

		protected Data doInBackground(Integer... params) {
			Utilities util = new Utilities();
			return util.PlatformFromID(2);
		}
		
		protected void onPostExecute(Data<PlatformItem> result) {
			tv1.setText(StringEscapeUtils.unescapeXml(result.items.get(0).name));
		}
	}
}
