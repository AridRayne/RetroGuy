package com.AridRayne.RetroGuy.GamesDB;

import com.AridRayne.thegamesdb.lib.Platform;

public class RetroGuyPlatform extends Platform {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5884153457162031775L;
	private String imageFileName;

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	
	public RetroGuyPlatform() {
		super();
		this.imageFileName = "";
	}
	
	public RetroGuyPlatform(Platform platform) {
		this.setConsole(platform.getConsole());
		this.setController(platform.getController());
		this.setCPU(platform.getCPU());
		this.setDeveloper(platform.getDeveloper());
		this.setDisplay(platform.getDisplay());
		this.setGraphics(platform.getGraphics());
		this.setId(platform.getId());
		this.setImages(platform.getImages());
		this.setManufacturer(platform.getManufacturer());
		this.setMaxControllers(platform.getMaxControllers());
		this.setMedia(platform.getMedia());
		this.setMemory(platform.getMemory());
		this.setName(platform.getName());
		this.setOverview(platform.getOverview());
		this.setRating(platform.getRating());
		this.setSound(platform.getSound());
		this.imageFileName = "";
	}
}
