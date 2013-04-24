package cz.gcm.cwg.database.items;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import cz.gcm.cwg.constants.Database;

public class Cwg {

	protected static final String TABLE_NAME = "cwg";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_CWGNO = "cwgno";
	public static final String COLUMN_VERSION = "version";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_IMAGE = "image_url";
	public static final String[] columns = { COLUMN_ID, COLUMN_CWGNO,
			COLUMN_VERSION, COLUMN_NAME, COLUMN_IMAGE };

	public static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME + "(" + COLUMN_ID + " integer primary key " + ", "
			+ COLUMN_CWGNO + " text not null" + ", " + COLUMN_VERSION
			+ " int not null" + ", " + COLUMN_NAME + " text not null" + ", "
			+ COLUMN_IMAGE + " text not null" + ");";
	
	protected static final String ORDER_BY = COLUMN_NAME + " ASC";

	private SQLiteOpenHelper openHelper;

	public Cwg(Context ctx) {
		openHelper = new DatabaseHelper(ctx);
	}

	public Cursor getAllCwg() {
		SQLiteDatabase db = openHelper.getReadableDatabase();
		return db.query(TABLE_NAME, columns, null, null, null, null, ORDER_BY);
	}

	public Cursor getCwg(long id) {
		SQLiteDatabase db = openHelper.getReadableDatabase();
		String[] selectionArgs = { String.valueOf(id) };
		return db.query(TABLE_NAME, columns, COLUMN_ID + "= ?", selectionArgs,
				null, null, ORDER_BY);
	}

	public boolean deleteCwg(long id) {
		SQLiteDatabase db = openHelper.getWritableDatabase();
		String[] selectionArgs = { String.valueOf(id) };

		int deletedCount = db.delete(TABLE_NAME, COLUMN_ID + "= ?", selectionArgs);
		db.close();
		return deletedCount > 0;
	}

	public long addCwg(ContentValues values) {
		SQLiteDatabase db = openHelper.getWritableDatabase();
		/*
		//TODO; prijmout rovnou .... treba i stahnout obrazek a tak ... ala ORM
		ContentValues values = new ContentValues();
		values.put(COLUMN_TITLE, "Title");
		values.put(COLUMN_NOTE, text);
		*/
		
		long id = db.insert(TABLE_NAME, null, values);
		db.close();
		return id;
	}
	
	public long updateCwg(int id, ContentValues values) {
		SQLiteDatabase db = openHelper.getWritableDatabase();
		
		long count = 0;
		if(getCwg(id).getCount() == 1){
			count = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});	
		}
		db.close();
		return count;
	}

	public void close() {
		openHelper.close();
	}
	
	public void onDestroy()
	{
		openHelper.close();
	}

	static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, Database.DATABASE_NAME, null,
					Database.DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_TABLE_SQL);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO: upgrade
			// db.execSQL("DROP TABLE IF EXISTS notes");
			// onCreate(db);
		}

	}

}
