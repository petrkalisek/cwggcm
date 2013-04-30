package cz.gcm.cwg.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

import cz.gcm.cwg.constants.Database;

public class BaseDatabaseHelper extends OrmLiteSqliteOpenHelper{

	private static final String DATABASE_NAME = Database.DATABASE_NAME;
	private static final int DATABASE_VERSION = 2;
	
	public BaseDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		
	}
	
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		
	}
}
