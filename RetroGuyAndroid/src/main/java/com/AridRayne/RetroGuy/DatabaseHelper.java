package com.AridRayne.RetroGuy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.AridRayne.RetroGuy.GamesDB.RetroGuyGame;
import com.AridRayne.RetroGuy.GamesDB.RetroGuyPlatform;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 3;
	private static final String DATABASE_NAME = "RetroGuy";
	
	//Table names.
	private static final String TABLE_PLATFORMS = "platforms";
	private static final String TABLE_GAMES = "games";
	
	//Common column names.
	private static final String KEY_ID = "id";
	private static final String KEY_OVERVIEW = "overview";
	private static final String KEY_DEVELOPER = "developer";
	private static final String KEY_RATING = "rating";
	private static final String KEY_THUMBNAIL = "thumbnail";
	
	//Platforms column names.
	private static final String KEY_PLATFORMS_NAME = "name";
	private static final String KEY_PLATFORMS_CONSOLE = "console";
	private static final String KEY_PLATFORMS_CONTROLLER = "controller";
	private static final String KEY_PLATFORMS_MANUFACTURER = "manufacturer";
	private static final String KEY_PLATFORMS_CPU = "cpu";
	private static final String KEY_PLATFORMS_MEMORY = "memory";
	private static final String KEY_PLATFORMS_GRAPHICS = "graphics";
	private static final String KEY_PLATFORMS_SOUND = "sound";
	private static final String KEY_PLATFORMS_DISPLAY = "display";
	private static final String KEY_PLATFORMS_MEDIA = "media";
	private static final String KEY_PLATFORMS_MAX_CONTROLLERS = "max_controllers";
	
	//Games column names.
	private static final String KEY_GAMES_TITLE = "title";
	private static final String KEY_GAMES_PLATFORM_ID = "platform_id";
	private static final String KEY_GAMES_PLATFORM = "platform";
	private static final String KEY_GAMES_ESRB = "esrb";
	private static final String KEY_GAMES_GENRES = "genres";
	private static final String KEY_GAMES_PLAYERS = "players";
	private static final String KEY_GAMES_COOP = "coop";
	private static final String KEY_GAMES_YOUTUBE = "youtube";
	private static final String KEY_GAMES_PUBLISHER = "publisher";
	private static final String KEY_GAMES_RELEASE_DATE = "release_date";
	
	private SQLiteDatabase db;
	private static DatabaseHelper instance;
	private static Context DBcontext;
	private List<DatabaseListener> listener;
	
	private static final String CREATE_TABLE_PLATFORMS = "CREATE TABLE "
			+ TABLE_PLATFORMS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_OVERVIEW + " TEXT," + KEY_DEVELOPER + " TEXT,"
			+ KEY_RATING + " REAL," + KEY_PLATFORMS_NAME + " TEXT,"
			+ KEY_PLATFORMS_CONSOLE + " TEXT," + KEY_PLATFORMS_CONTROLLER + " TEXT,"
			+ KEY_PLATFORMS_MANUFACTURER + " TEXT," + KEY_PLATFORMS_CPU + " TEXT,"
			+ KEY_PLATFORMS_MEMORY + " TEXT," + KEY_PLATFORMS_GRAPHICS + " TEXT,"
			+ KEY_PLATFORMS_SOUND + " TEXT," + KEY_PLATFORMS_DISPLAY + " TEXT,"
			+ KEY_PLATFORMS_MEDIA + " TEXT," + KEY_PLATFORMS_MAX_CONTROLLERS + " TEXT,"
			+ KEY_THUMBNAIL + " TEXT)";
	
	private static final String CREATE_TABLE_GAMES = "CREATE TABLE "
			+ TABLE_GAMES + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_OVERVIEW + " TEXT," + KEY_DEVELOPER + " TEXT,"
			+ KEY_RATING + " REAL," + KEY_GAMES_TITLE + " TEXT,"
			+ KEY_GAMES_PLATFORM_ID + " INTEGER," + KEY_GAMES_PLATFORM + " TEXT,"
			+ KEY_GAMES_ESRB + " TEXT," + KEY_GAMES_GENRES + " TEXT,"
			+ KEY_GAMES_PLAYERS + " TEXT," + KEY_GAMES_COOP + " TEXT,"
			+ KEY_GAMES_YOUTUBE + " TEXT," + KEY_GAMES_PUBLISHER + " TEXT,"
			+ KEY_GAMES_RELEASE_DATE + " TEXT," + KEY_THUMBNAIL + " TEXT)";

	public static synchronized void initialize(Context context) {
		DBcontext = context;
	}
	
	public static synchronized DatabaseHelper getInstance() {
		if (instance == null)
			instance = new DatabaseHelper();
		return instance;
	}
	
	private DatabaseHelper() {
		super(DBcontext, DATABASE_NAME, null, DATABASE_VERSION);
		db = getReadableDatabase();
		listener = null;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_PLATFORMS);
		db.execSQL(CREATE_TABLE_GAMES);
	}
	
	public void addListener(DatabaseListener listener) {
		this.listener.add(listener);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLATFORMS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
		onCreate(db);
	}

	public RetroGuyPlatform platformFromCursor(Cursor cursor) {
		RetroGuyPlatform p = new RetroGuyPlatform();
		p.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
		p.setOverview(cursor.getString(cursor.getColumnIndex(KEY_OVERVIEW)));
		p.setDeveloper(cursor.getString(cursor.getColumnIndex(KEY_DEVELOPER)));
		p.setRating(cursor.getFloat(cursor.getColumnIndex(KEY_RATING)));
		p.setConsole(cursor.getString(cursor.getColumnIndex(KEY_PLATFORMS_CONSOLE)));
		p.setController(cursor.getString(cursor.getColumnIndex(KEY_PLATFORMS_CONTROLLER)));
		p.setCPU(cursor.getString(cursor.getColumnIndex(KEY_PLATFORMS_CPU)));
		p.setDisplay(cursor.getString(cursor.getColumnIndex(KEY_PLATFORMS_DISPLAY)));
		p.setGraphics(cursor.getString(cursor.getColumnIndex(KEY_PLATFORMS_GRAPHICS)));
		p.setManufacturer(cursor.getString(cursor.getColumnIndex(KEY_PLATFORMS_MANUFACTURER)));
		p.setMaxControllers(cursor.getString(cursor.getColumnIndex(KEY_PLATFORMS_MAX_CONTROLLERS)));
		p.setMedia(cursor.getString(cursor.getColumnIndex(KEY_PLATFORMS_MEDIA)));
		p.setMemory(cursor.getString(cursor.getColumnIndex(KEY_PLATFORMS_MEMORY)));
		p.setName(cursor.getString(cursor.getColumnIndex(KEY_PLATFORMS_NAME)));
		p.setSound(cursor.getString(cursor.getColumnIndex(KEY_PLATFORMS_SOUND)));
		p.setImageFileName(cursor.getString(cursor.getColumnIndex(KEY_THUMBNAIL)));
		return p;
	}
	
	public int numPlatforms() {
		Cursor c = db.rawQuery("SELECT * FROM " + TABLE_PLATFORMS, null);
		return c.getCount();
	}
	
	public long addPlatform(RetroGuyPlatform platform) {
		ContentValues values = new ContentValues();
		values.put(KEY_ID, platform.getId());
		values.put(KEY_OVERVIEW, platform.getOverview());
		values.put(KEY_DEVELOPER, platform.getDeveloper());
		values.put(KEY_RATING, platform.getRating());
		values.put(KEY_PLATFORMS_CONSOLE, platform.getConsole());
		values.put(KEY_PLATFORMS_CONTROLLER, platform.getController());
		values.put(KEY_PLATFORMS_CPU, platform.getCPU());
		values.put(KEY_PLATFORMS_DISPLAY, platform.getDisplay());
		values.put(KEY_PLATFORMS_GRAPHICS, platform.getGraphics());
		values.put(KEY_PLATFORMS_MANUFACTURER, platform.getManufacturer());
		values.put(KEY_PLATFORMS_MAX_CONTROLLERS, platform.getMaxControllers());
		values.put(KEY_PLATFORMS_MEDIA, platform.getMedia());
		values.put(KEY_PLATFORMS_MEMORY, platform.getMemory());
		values.put(KEY_PLATFORMS_NAME, platform.getName());
		values.put(KEY_PLATFORMS_SOUND, platform.getSound());
		values.put(KEY_THUMBNAIL, platform.getImageFileName());
		for (DatabaseListener l : listener) {
			l.onPlatformAdded(platform);
		}
		return db.insert(TABLE_PLATFORMS, null, values);
	}
	
	public RetroGuyPlatform getPlatformItem(int id) {
		String query = "SELECT * FROM " + TABLE_PLATFORMS + " WHERE " + KEY_ID + " = " + id;
		Cursor cursor = db.rawQuery(query, null);
		
		if (cursor == null || cursor.getCount() == 0)
			return null;
		cursor.moveToFirst();
		return platformFromCursor(cursor);
	}
	
	public List<RetroGuyPlatform> getAllPlatforms() {
		List<RetroGuyPlatform> platforms = new ArrayList<RetroGuyPlatform>();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PLATFORMS, null);
		
		if (cursor.moveToFirst()) {
			do {
				platforms.add(platformFromCursor(cursor));
			} while (cursor.moveToNext());
		}
		
		return platforms;
	}

	public RetroGuyGame gameFromCursor(Cursor cursor) {
		RetroGuyGame item = new RetroGuyGame();
		item.setCoop(cursor.getString(cursor.getColumnIndex(KEY_GAMES_COOP)));
		item.setDeveloper(cursor.getString(cursor.getColumnIndex(KEY_DEVELOPER)));
		item.setEsrb(cursor.getString(cursor.getColumnIndex(KEY_GAMES_ESRB)));
		item.setGenres(Arrays.asList(StringUtils.split(cursor.getString(cursor.getColumnIndex(KEY_GAMES_GENRES)), ",")));
		item.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
		item.setImageFileName(cursor.getString(cursor.getColumnIndex(KEY_THUMBNAIL)));
//		item.setImages(images); //TODO: Figure this out as well.
		item.setOverview(cursor.getString(cursor.getColumnIndex(KEY_OVERVIEW)));
		item.setPlatform(cursor.getString(cursor.getColumnIndex(KEY_GAMES_PLATFORM)));
		item.setPlatformID(cursor.getInt(cursor.getColumnIndex(KEY_GAMES_PLATFORM_ID)));
		item.setPlayers(cursor.getString(cursor.getColumnIndex(KEY_GAMES_PLAYERS)));
		item.setPublisher(cursor.getString(cursor.getColumnIndex(KEY_GAMES_PUBLISHER)));
		item.setRating(cursor.getFloat(cursor.getColumnIndex(KEY_RATING)));
		item.setReleaseDate(cursor.getString(cursor.getColumnIndex(KEY_GAMES_RELEASE_DATE)));
		item.setTitle(cursor.getString(cursor.getColumnIndex(KEY_GAMES_TITLE)));
		item.setYoutube(cursor.getString(cursor.getColumnIndex(KEY_GAMES_YOUTUBE)));
		return item;		
	}
	
	public int numGames() {
		Cursor c = db.rawQuery("SELECT * FROM " + TABLE_GAMES, null);
		return c.getCount();
	}
	
	public long addGame(RetroGuyGame game) {
		ContentValues values = new ContentValues();
		values.put(KEY_ID, game.getId());
		values.put(KEY_OVERVIEW, game.getOverview());
		values.put(KEY_DEVELOPER, game.getDeveloper());
		values.put(KEY_RATING, game.getRating());
		values.put(KEY_GAMES_COOP, game.getCoop());
		values.put(KEY_GAMES_ESRB, game.getEsrb());
		values.put(KEY_GAMES_GENRES, StringUtils.join(game.getGenres(), ","));
		values.put(KEY_GAMES_PLATFORM, game.getPlatform());
		values.put(KEY_GAMES_PLATFORM_ID, game.getPlatformID());
		values.put(KEY_GAMES_PLAYERS, game.getPlayers());
		values.put(KEY_GAMES_PUBLISHER, game.getPublisher());
		values.put(KEY_GAMES_RELEASE_DATE, game.getReleaseDate());
		values.put(KEY_GAMES_TITLE, game.getTitle());
		values.put(KEY_GAMES_YOUTUBE, game.getYoutube());
		values.put(KEY_THUMBNAIL, game.getImageFileName());
		for (DatabaseListener l : listener) {
			l.onGameAdded(game);
		}
		return db.insert(TABLE_GAMES, null, values);
	}
	
	public RetroGuyGame getGameItem(int id) {
		String query = "SELECT * FROM " + TABLE_GAMES + " WHERE " + KEY_ID + " = " + id;
		Cursor cursor = db.rawQuery(query, null);
		
		if (cursor == null || cursor.getCount() == 0)
			return null;
		cursor.moveToFirst();
		return gameFromCursor(cursor);
	}
	
	public List<RetroGuyGame> getAllGames() {
		List<RetroGuyGame> games = new ArrayList<RetroGuyGame>();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GAMES, null);
		
		if (cursor.moveToFirst()) {
			do {
				games.add(gameFromCursor(cursor));
			} while (cursor.moveToNext());
		}
		
		return games;
	}

}
