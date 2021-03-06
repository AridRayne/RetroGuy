package com.AridRayne.RetroGuy;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ThemeUtils {
	public static void applyTheme(Context context) {
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		String theme = sharedPrefs.getString("appTheme", "default");
		if (theme.equals("light")) {
			context.setTheme(R.style.LightTheme);
		}
		else if (theme.equals("lightdarkactionbar")) {
			context.setTheme(R.style.LightDarkActionBarTheme);
		}
		else {
			context.setTheme(R.style.DarkTheme);
		}

	}
}
