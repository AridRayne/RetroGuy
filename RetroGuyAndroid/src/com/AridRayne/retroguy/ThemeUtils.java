package com.AridRayne.retroguy;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ThemeUtils {
	public static void ApplyTheme(Context context) {
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		String theme = sharedPrefs.getString("appTheme", "default");
		if (theme.equals("light")) {
			context.setTheme(com.roboguice.R.style.Theme_Sherlock_Light);
		}
		else if (theme.equals("lightdarkactionbar")) {
			context.setTheme(com.roboguice.R.style.Theme_Sherlock_Light_DarkActionBar);
		}
		else {
			context.setTheme(com.roboguice.R.style.Theme_Sherlock);
		}

	}
}
