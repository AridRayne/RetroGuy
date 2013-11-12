package com.AridRayne.retroguy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.AridRayne.thegamesdb.lib.GameItem;
import com.AridRayne.thegamesdb.lib.PlatformItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "RetroGuy";
	
	//Table names.
	private static final String TABLE_PLATFORMS = "platforms";
	private static final String TABLE_GAMES = "games";
	
	//Common column names.
	private static final String KEY_ID = "id";
	private static final String KEY_OVERVIEW = "overview";
	private static final String KEY_DEVELOPER = "developer";
	private static final String KEY_RATING = "rating";
	
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
	
	private static final String CREATE_TABLE_PLATFORMS = "CREATE TABLE "
			+ TABLE_PLATFORMS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_OVERVIEW + " TEXT," + KEY_DEVELOPER + " TEXT,"
			+ KEY_RATING + " REAL," + KEY_PLATFORMS_NAME + " TEXT,"
			+ KEY_PLATFORMS_CONSOLE + " TEXT," + KEY_PLATFORMS_CONTROLLER + " TEXT,"
			+ KEY_PLATFORMS_MANUFACTURER + " TEXT," + KEY_PLATFORMS_CPU + " TEXT,"
			+ KEY_PLATFORMS_MEMORY + " TEXT," + KEY_PLATFORMS_GRAPHICS + " TEXT,"
			+ KEY_PLATFORMS_SOUND + " TEXT," + KEY_PLATFORMS_DISPLAY + " TEXT,"
			+ KEY_PLATFORMS_MEDIA + " TEXT," + KEY_PLATFORMS_MAX_CONTROLLERS + " TEXT)";
	
	private static final String CREATE_TABLE_GAMES = "CREATE TABLE "
			+ TABLE_GAMES + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_OVERVIEW + " TEXT," + KEY_DEVELOPER + " TEXT,"
			+ KEY_RATING + " REAL," + KEY_GAMES_TITLE + " TEXT,"
			+ KEY_GAMES_PLATFORM_ID + " INTEGER," + KEY_GAMES_PLATFORM + " TEXT,"
			+ KEY_GAMES_ESRB + " TEXT," + KEY_GAMES_GENRES + " TEXT,"
			+ KEY_GAMES_PLAYERS + " TEXT," + KEY_GAMES_COOP + " TEXT,"
			+ KEY_GAMES_YOUTUBE + " TEXT," + KEY_GAMES_PUBLISHER + " TEXT,"
			+ KEY_GAMES_RELEASE_DATE + " DATETIME)";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_PLATFORMS);
		db.execSQL(CREATE_TABLE_GAMES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLATFORMS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
		onCreate(db);
	}

	public int numPlatforms() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + TABLE_PLATFORMS, null);
		return c.getCount();
	}
	
	public long addPlatform(PlatformItem platform) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_ID, platform.id);
		values.put(KEY_OVERVIEW, platform.overview);
		values.put(KEY_DEVELOPER, platform.developer);
		values.put(KEY_RATING, platform.rating);
		values.put(KEY_PLATFORMS_CONSOLE, platform.console);
		values.put(KEY_PLATFORMS_CONTROLLER, platform.controller);
		values.put(KEY_PLATFORMS_CPU, platform.cpu);
		values.put(KEY_PLATFORMS_DISPLAY, platform.display);
		values.put(KEY_PLATFORMS_GRAPHICS, platform.graphics);
		values.put(KEY_PLATFORMS_MANUFACTURER, platform.manufacturer);
		values.put(KEY_PLATFORMS_MAX_CONTROLLERS, platform.maxControllers);
		values.put(KEY_PLATFORMS_MEDIA, platform.media);
		values.put(KEY_PLATFORMS_MEMORY, platform.memory);
		values.put(KEY_PLATFORMS_NAME, platform.name);
		values.put(KEY_PLATFORMS_SOUND, platform.sound);
		return db.insert(TABLE_PLATFORMS, null, values);
	}
	
	public PlatformItem getPlatformItem(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		String query = "SELECT * FROM " + TABLE_PLATFORMS + " WHERE " + KEY_ID + " = " + id;
		Cursor cursor = db.rawQuery(query, null);
		
		if (cursor == null || cursor.getCount() == 0)
			return null;
		cursor.moveToFirst();
		PlatformItem item = new PlatformItem();
		item.id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
		item.overview = cursor.getString(cursor.getColumnIndex(KEY_OVERVIEW));
		item.developer = cursor.getString(cursor.getColumnIndex(KEY_DEVELOPER));
		item.rating = cursor.getFloat(cursor.getColumnIndex(KEY_RATING));
		item.console = cursor.getString(cursor.getColumnIndex(KEY_PLATFORMS_CONSOLE));
		item.controller = cursor.getString(cursor.getColumnIndex(KEY_PLATFORMS_CONTROLLER));
		item.cpu = cursor.getString(cursor.getColumnIndex(KEY_PLATFORMS_CPU));
		item.display = cursor.getString(cursor.getColumnIndex(KEY_PLATFORMS_DISPLAY));
		item.graphics = cursor.getString(cursor.getColumnIndex(KEY_PLATFORMS_GRAPHICS));
		item.manufacturer = cursor.getString(cursor.getColumnIndex(KEY_PLATFORMS_MANUFACTURER));
		item.maxControllers = cursor.getString(cursor.getColumnIndex(KEY_PLATFORMS_MAX_CONTROLLERS));
		item.media = cursor.getString(cursor.getColumnIndex(KEY_PLATFORMS_MEDIA));
		item.memory = cursor.getString(cursor.getColumnIndex(KEY_PLATFORMS_MEMORY));
		item.name = cursor.getString(cursor.getColumnIndex(KEY_PLATFORMS_NAME));
		item.sound = cursor.getString(cursor.getColumnIndex(KEY_PLATFORMS_SOUND));
		return item;
	}
	
	public long addGame(GameItem game) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_ID, game.id);
		values.put(KEY_OVERVIEW, game.overview);
		values.put(KEY_DEVELOPER, game.developer);
		values.put(KEY_RATING, game.rating);
		values.put(KEY_GAMES_COOP, game.coop);
		values.put(KEY_GAMES_ESRB, game.esrb);
		values.put(KEY_GAMES_GENRES, StringUtils.join(game.genres, ","));
		values.put(KEY_GAMES_PLATFORM, game.platform);
		values.put(KEY_GAMES_PLATFORM_ID, game.platformID);
		values.put(KEY_GAMES_PLAYERS, game.players);
		values.put(KEY_GAMES_PUBLISHER, game.publisher);
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
		values.put(KEY_GAMES_RELEASE_DATE, df.format(game.releaseDate));
		values.put(KEY_GAMES_TITLE, game.title);
		values.put(KEY_GAMES_YOUTUBE, game.youtube);
		return db.insert(TABLE_GAMES, null, values);
	}
}
