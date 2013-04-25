package cz.gcm.cwg.database.items;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import cz.gcm.cwg.constants.Database;
import cz.gcm.cwg.database.CwgDatabaseHelper;

public class Cwg {

	public static final String TABLE_NAME = "cwg";
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
			+ " int not null" + ", " + COLUMN_NAME + " text not null collate nocase " + ", "
			+ COLUMN_IMAGE + " text not null" + ");";
	
	protected static final String ORDER_BY = COLUMN_NAME + " ASC";

	private SQLiteOpenHelper openHelper = null;
	private SQLiteDatabase writableDb = null;
	private SQLiteDatabase readableDb = null;
	private Context context = null;

	public Cwg(Context ctx) {
		context = ctx;
	}
	
	public void onStart(){
		Log.d("Cwg::onStart", "onStart");
	}

	public Cursor getAllCwg() {
		Cursor cursor = getReadableDb().query(TABLE_NAME, columns, null, null, null, null, ORDER_BY);
		return cursor;
	}

	public Cursor getCwg(long id) {
		Log.d("Cwg::getCwg", "id:"+id);
		String[] selectionArgs = { String.valueOf(id) };
		Cursor cursor = getReadableDb().query(TABLE_NAME, columns, COLUMN_ID + "= ?", selectionArgs,
				null, null, ORDER_BY);
		return cursor;
	}

	public boolean deleteCwg(long id) {
		String[] selectionArgs = { String.valueOf(id) };

		int deletedCount = getWritableDb().delete(TABLE_NAME, COLUMN_ID + "= ?", selectionArgs);
		return deletedCount > 0;
	}

	public long addCwg(ContentValues values) {
		/*
		//TODO; prijmout rovnou .... treba i stahnout obrazek a tak ... ala ORM
		ContentValues values = new ContentValues();
		values.put(COLUMN_TITLE, "Title");
		values.put(COLUMN_NOTE, text);
		*/
		
		long id = getWritableDb().insert(TABLE_NAME, null, values);
		return id;
	}
	
	public long updateCwg(int id, ContentValues values) {
		
		long count = 0;
		if(getCwg(id).getCount() == 1){
			count = getWritableDb().update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});	
		}
		return count;
	}

	public void onClose() {
		
		if(writableDb != null){
			writableDb.close();	
		}
		
		if(readableDb != null){
			readableDb.close();	
		}
		/*
		if(openHelper != null){
			openHelper.close();	
		}*/
		
	}
	
	private SQLiteDatabase getReadableDb(){
		
		if(openHelper == null){
			openHelper = CwgDatabaseHelper.getInstance(context);
		}
				
		if( readableDb == null){
			readableDb = openHelper.getReadableDatabase();
		}
		return readableDb;
	}
	
	private SQLiteDatabase getWritableDb(){
		if(openHelper == null){
			openHelper = CwgDatabaseHelper.getInstance(context);
		}
		
		if( writableDb == null){
			writableDb = openHelper.getReadableDatabase();
		}
		return writableDb;
	}
}
