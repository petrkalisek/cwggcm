package cz.gcm.cwg.database.items;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "cwg")
public class Cwg {
	
	public static final String TABLE_NAME = "cwg";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_CWGNO = "cwgno";
	public static final String COLUMN_VERSION = "version";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_IMAGE = "image_url";
	public static final String[] columns = { COLUMN_ID, COLUMN_CWGNO,
			COLUMN_VERSION, COLUMN_NAME, COLUMN_IMAGE };
	
	protected static final String ORDER_BY = COLUMN_NAME + " ASC";
	
	
	@DatabaseField(generatedId = false, id = true)
	private long _id;
	@DatabaseField(index = true, canBeNull = false)
	private String name;
	@DatabaseField(index = true, canBeNull = false)
	private String cwgno;
	@DatabaseField(index = true, canBeNull = false)
	private int version;
	@DatabaseField(canBeNull = true)
	private String image_url;
	
	public Cwg() {
		// needed by ormlite
	}
	
	public void setId(int id){
		this._id = id;
	}
	
	public long getId(){
		return this._id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setCwgNo(String cwgno){
		this.cwgno = cwgno;
	}
	
	public String getCwgNo(){
		return this.cwgno;
	}
	
	public void setVersion(int version){
		this.version = version;
	}
	
	public int getVersion(){
		return this.version;
	}
	
	public void setImageUrl(String image_url){
		this.image_url = image_url;
	}
	
	public String getImageUrl(){
		return this.image_url;
	}
	
	
	/*
	public static Cwg getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new Cwg(ctx.getApplicationContext());
        }
        
        return mInstance;
    }
	
	public Cursor getAllCwg() {
		Cursor cursor = getWritableDb().query(TABLE_NAME, columns, null, null, null, null, ORDER_BY);
		return cursor;
	}

	public Cursor getCwg(long id) {
		Cursor cursor = null;
		try{
			String[] selectionArgs = { String.valueOf(id) };
			cursor = getWritableDb().query(TABLE_NAME, columns, COLUMN_ID + "= ?", selectionArgs,
					null, null, ORDER_BY);
		}catch(Exception e){
			Log.w("getCwg EXCEPTION", e.getMessage());
			return null;
		}
		cursor.moveToFirst();
		return cursor;
	}

	public boolean deleteCwg(long id) {
		String[] selectionArgs = { String.valueOf(id) };
		int deletedCount = getWritableDb().delete(TABLE_NAME, COLUMN_ID + "= ?", selectionArgs);
		return deletedCount > 0;
	}

	public long addCwg(ContentValues values) {
		long id = 0;
		
		if(getCwg(values.getAsLong(COLUMN_ID)).getCount() > 0){
			id = updateCwg(values.getAsLong(COLUMN_ID), values);
		}else{
			id = getWritableDb().insert(TABLE_NAME, null, values);
		}
		
		return id;
	}
	
	public long updateCwg(long id, ContentValues values) {
		
		long count = 0;
		if(getCwg(id) != null ){
			count = getWritableDb().update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});	
		}
		return count;
	}
	
	
	
	public void getImage(long id){
		if(getCwg(id) != null ){
			
		}
	}
	
	public void close(){
		Log.i("Cwg::close","close");
		if( writableDb != null && writableDb.isOpen()){
			writableDb.close();
			openHelper.close();
			writableDb = null;
		}
	}
	
	
	private SQLiteDatabase getWritableDb(){
		
		if(writableDb == null || !writableDb.isOpen()){
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
		
	}*/
}
