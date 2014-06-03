package net.usbsync.appdesk.jobs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAppDataHelper extends SQLiteOpenHelper implements infAppDatas {
	SQLiteDatabase db = this.getWritableDatabase();
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "AppDataManager";

	// Contacts table name
	private static final String TABLE_CONTACTS = "AppDatas";

	// Contacts Table Columns names
	private static final String KEY_ID = "Id";
	private static final String KEY_NAME = "AppName";
	private static final String KEY_PCK_NM = "PackageName";
	private static final String KEY_APP_ICO = "AppIcon";
	private static final String KEY_OPT = "Option";

	public DBAppDataHelper(Context context) {
		super(context.getApplicationContext(), DATABASE_NAME, null,
				DATABASE_VERSION);
		db.execSQL("PRAGMA read_uncommitted = true;");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_APPDATAS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " VARCHAR(50),"
				+ KEY_PCK_NM + " VARCHAR(100)," + KEY_APP_ICO + " VARCHAR(20),"
				+ KEY_OPT + " VARCHAR(10)" + ")";
		db.execSQL(CREATE_APPDATAS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

		// Create tables again
		onCreate(db);
	}

	@Override
	public void addAppDatas(AppDatas AppDatas) {
		// db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, AppDatas.getAppName());
		values.put(KEY_PCK_NM, AppDatas.getPackageName());
		values.put(KEY_APP_ICO, AppDatas.getAppIcon());
		values.put(KEY_OPT, AppDatas.getOption());

		// Inserting Row
		db.insert(TABLE_CONTACTS, null, values);
	}

	@Override
	public AppDatas getAppDatas(int id) {
		// db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
				KEY_NAME, KEY_PCK_NM, KEY_APP_ICO, KEY_OPT }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		AppDatas contact = new AppDatas(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2),
				Integer.parseInt(cursor.getString(3)), cursor.getString(4));
		// return contact
		return contact;
	}

	@Override
	public ArrayList<AppDatas> getAllAppDatass() {
		ArrayList<AppDatas> appDataList = new ArrayList<AppDatas>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

		// db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				AppDatas appData = new AppDatas();
				appData.setId(Integer.parseInt(cursor.getString(0)));
				appData.setAppName(cursor.getString(1));
				appData.setPackageName(cursor.getString(2));
				appData.setAppIcon(Integer.parseInt(cursor.getString(3)));
				appData.setOption(cursor.getString(4));
				// Adding contact to list
				appDataList.add(appData);
			} while (cursor.moveToNext());
		}

		// return contact list
		return appDataList;
	}

	@Override
	public int getAppDatassCount() {
		String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
		// db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		return cursor.getCount();
	}

	@Override
	public int updateAppDatas(AppDatas appDatas) {
		// db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, appDatas.getAppName());
		values.put(KEY_PCK_NM, appDatas.getPackageName());
		values.put(KEY_APP_ICO, appDatas.getAppIcon());
		values.put(KEY_OPT, appDatas.getOption());

		// updating row
		return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(appDatas.getId()) });

	}

	@Override
	public void deleteAppDatas(AppDatas appDatas) {
		// db = this.getWritableDatabase();
		db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
				new String[] { String.valueOf(appDatas.getId()) });

	}

	public void dbOpen() {
		db = this.getWritableDatabase();
		db.beginTransaction();
		db.rawQuery("PRAGMA cache_size=5000", null);
	}

	public void dbClose() {
		db.close();
	}

}
