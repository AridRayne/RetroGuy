package com.AridRayne.RetroGuy.GamesDB;

import com.AridRayne.thegamesdb.lib.Game;

public class RetroGuyGame extends Game {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7036620829902702149L;
	private String imageFileName;
	
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public RetroGuyGame() {
		super();
		imageFileName = "";
	}
	
	public RetroGuyGame(Game game) {
		this.setCoop(game.getCoop());
		this.setDeveloper(game.getDeveloper());
		this.setEsrb(game.getEsrb());
		this.setGenres(game.getGenres());
		this.setId(game.getId());
		this.setImages(game.getImages());
		this.setOverview(game.getOverview());
		this.setPlatform(game.getPlatform());
		this.setPlatformID(game.getPlatformID());
		this.setPlayers(game.getPlayers());
		this.setPublisher(game.getPublisher());
		this.setRating(game.getRating());
		this.setReleaseDate(game.getReleaseDate());
		this.setTitle(game.getTitle());
		this.setYoutube(game.getYoutube());
		this.imageFileName = "";
	}

}
