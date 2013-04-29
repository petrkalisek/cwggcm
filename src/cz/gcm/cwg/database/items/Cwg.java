package cz.gcm.cwg.database.items;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
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
	private Context context = null;

	public Cwg(Context ctx) {
		context = ctx;
		openHelper = CwgDatabaseHelper.getInstance(context);
	}
	
	public Cursor getAllCwg() {
		Cursor mCount= getWritableDb().rawQuery("select count(*) from "+TABLE_NAME, null);
		mCount.moveToFirst();
		int count= mCount.getInt(0);
		Log.i("getAllCwg::count", "count:"+count);
		mCount.close();
		
		Cursor cursor = getWritableDb().query(TABLE_NAME, columns, null, null, null, null, ORDER_BY);
		dumpCursor(cursor);
		return cursor;
	}

	public Cursor getCwg(long id) {
		//Log.d("Cwg::getCwg", "id:"+id);
		Cursor cursor = null;
		
		try{
			String[] selectionArgs = { String.valueOf(id) };
			cursor = getWritableDb().query(TABLE_NAME, columns, COLUMN_ID + "= ?", selectionArgs,
					null, null, ORDER_BY);
			cursor.close();
		}catch(Exception e){
			Log.w("getCwg EXCEPTION", e.getMessage());
			return null;
		}
		return cursor;
	}

	public boolean deleteCwg(long id) {
		String[] selectionArgs = { String.valueOf(id) };
		int deletedCount = getWritableDb().delete(TABLE_NAME, COLUMN_ID + "= ?", selectionArgs);
		return deletedCount > 0;
	}

	public long addCwg(ContentValues values) {
		Log.d("Cwg::addCwg", "values:"+values.toString());
		Log.d("Cwg::addCwg", "getCwg("+values.getAsLong(COLUMN_ID)+"):"+getCwg(values.getAsLong(COLUMN_ID)).toString());

		long id = 0;
		
		if(getCwg(values.getAsLong(COLUMN_ID)).getCount() > 0){
			Log.d("Cwg::updateCwg", "");
			id = updateCwg(values.getAsLong(COLUMN_ID), values);
		}else{
			Log.d("Cwg::insert", "");
			id = getWritableDb().insert(TABLE_NAME, null, values);
		}
		getWritableDb().close();
		
		return id;
	}
	
	public long updateCwg(long id, ContentValues values) {
		
		long count = 0;
		if(getCwg(id) != null ){
			count = getWritableDb().update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});	
		}
		return count;
	}

	
	private SQLiteDatabase getReadableDb(){
		return getWritableDb();
	}
	
	private SQLiteDatabase getWritableDb(){
		
		if(writableDb == null){
			writableDb = openHelper.getWritableDatabase();	
		}
		return writableDb;
	}
	
	
	private void dumpCursor(Cursor c){
		Log.d("Cwg::dumpCursor", c.toString());
		
		c.moveToFirst();
		while (c.moveToNext()) {
	        Bundle data = c.getExtras();
	        Log.d("Cwg::dumpCursor", data.toString());
	    }
		
	}
}
