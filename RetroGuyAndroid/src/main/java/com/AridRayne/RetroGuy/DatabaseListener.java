package com.AridRayne.RetroGuy;

import java.util.EventListener;

import com.AridRayne.RetroGuy.GamesDB.RetroGuyGame;
import com.AridRayne.RetroGuy.GamesDB.RetroGuyPlatform;

public interface DatabaseListener extends EventListener {
	void onPlatformAdded(RetroGuyPlatform Platform);
	void onGameAdded(RetroGuyGame Game);
}
