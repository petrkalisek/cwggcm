package cz.gcm.cwg.comm;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;

import cz.gcm.cwg.constants.Comm;
import cz.gcm.cwg.database.CwgDatabaseHelper;
import cz.gcm.cwg.database.items.Cwg;
import cz.gcm.cwg.exceptions.DialogException;
import cz.gcm.cwg.exceptions.LoginException;

public class MyCollection extends CwgApi {
	
	private Dao<Cwg, Integer> cwgDao;
	private static final String LOG_TAG = MyCollection.class.getName();
	private Context context;

	public MyCollection(Context context) {
		this.context = context;
	}

	public JSONObject getResult() {

		JSONObject jsonCwgInfo = null;
		Log.i("MyCollection::getResult", "getResult");
		try {
			
			
			Log.i("MyCollection::getResult", "after init cwgDao");
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("format", "json"));
			Log.i(LOG_TAG, params.toString());

			jsonCwgInfo = callUrl(Comm.API_URL_MY_COLLECTION, params);

			saveData(jsonCwgInfo);

			return jsonCwgInfo;
		} catch (LoginException e) {
			Log.e(LOG_TAG, e.getMessage());
		} catch (DialogException e) {
			Log.e(LOG_TAG, e.getMessage());
		}catch( Exception e){
			Log.w(LOG_TAG, e.getMessage());
		}

		return jsonCwgInfo;
	}

	private void saveData(JSONObject myCollectionResult) {

		try {
			//Log.i("MyCollection::saveData", "init cwgDao");
			CwgDatabaseHelper cwgDatabaseHelper = new CwgDatabaseHelper(context);
			cwgDao = cwgDatabaseHelper.getCwgDao();	
			//Log.i("MyCollection::getResult", "after init cwgDao");
			
			if (myCollectionResult.optJSONArray("Export").length() > 0) {

				JSONArray exportArray = myCollectionResult
						.getJSONArray("Export");

				for (int i = 0; i < exportArray.length(); i++) {
					JSONObject t = (JSONObject) exportArray.get(i);
					JSONArray collection = t.optJSONArray("collection");
					if (collection != null) {
						Log.i("MyCollectionActivity", "ID:" + t.getInt("id"));
						Log.i("MyCollectionActivity", "collection:"
								+ collection.toString());
					} else {
						Log.i("MyCollectionActivity",
								"NOT COLLECTION ID:" + t.getInt("id"));
					}
					/*
					ContentValues values = new ContentValues();
					values.put(Cwg.COLUMN_ID, t.optString("id"));
					values.put(Cwg.COLUMN_NAME, t.optString("name"));
					values.put(Cwg.COLUMN_CWGNO, t.optString("cwgno"));
					values.put(Cwg.COLUMN_VERSION, t.optString("version"));
					values.put(Cwg.COLUMN_IMAGE, t.optString("image"));
					*/
					
					Cwg newCwg = new Cwg();
					newCwg.setId(t.optInt("id"));
					newCwg.setName(t.optString("name"));
					newCwg.setCwgNo(t.optString("cwgno"));
					newCwg.setVersion(t.optInt("version"));
					newCwg.setImageUrl(t.optString("image"));
					
					if (cwgDao.queryForId(t.optInt("id")) == null) {
						cwgDao.create(newCwg);
					}else{
						cwgDao.update(newCwg);
					}

					Log.i("after getCwg", "" + t.getInt("id") + " ins id");
				}
			}

		} catch (Exception e) {
			Log.w("MyCollection::saveData", e.getMessage());
		}

	}

}
