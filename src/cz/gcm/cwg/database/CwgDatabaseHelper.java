package cz.gcm.cwg.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import cz.gcm.cwg.constants.Database;
import cz.gcm.cwg.database.items.Cwg;

public class CwgDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = Database.DATABASE_NAME;
    private static final String DATABASE_TABLE = Cwg.TABLE_NAME;
    private static final int DATABASE_VERSION = 1;
	private static CwgDatabaseHelper mInstance = null;
	private Context mCxt;
	
	CwgDatabaseHelper(Context context) {
		super(context, Database.DATABASE_NAME, null,
				Database.DATABASE_VERSION);
	}
	
	public static CwgDatabaseHelper getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new CwgDatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Cwg.CREATE_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO: upgrade
		// db.execSQL("DROP TABLE IF EXISTS notes");
		// onCreate(db);
	}

}
