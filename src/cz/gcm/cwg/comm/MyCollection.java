package cz.gcm.cwg.comm;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import cz.gcm.cwg.constants.Comm;
import cz.gcm.cwg.database.items.Cwg;
import cz.gcm.cwg.exceptions.DialogException;
import cz.gcm.cwg.exceptions.LoginException;

public class MyCollection extends CwgApi {

	private static final String LOG_TAG = MyCollection.class.getName();
	private Context context;
	
	public MyCollection(Context context){
		this.context = context;
	}
	
	public JSONObject getResult() {

		JSONObject jsonCwgInfo = null;

		try {
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
		}

		return jsonCwgInfo;
	}

	private void saveData(JSONObject myCollectionResult) {
		
		
		
		try {

			Log.w("MyCollection::saveData::JSONObject","WWWW");
			
			Cwg cwg = new Cwg(context);
			Log.w("MyCollection::saveData::JSONObject","XXXX");
			
			Log.i("MyCollection::saveData::JSONObject", ""+myCollectionResult.optJSONArray("Export").length());
			
			if (myCollectionResult.optJSONArray("Export").length() > 0) {
				
				
				JSONArray exportArray = myCollectionResult
						.getJSONArray("Export");
				Cursor c = null;

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

					// TODO: udelat nejak insert ale i jako update, kdyz
					// znam ID
					ContentValues values = new ContentValues();
					values.put(Cwg.COLUMN_ID, t.optString("id"));
					values.put(Cwg.COLUMN_NAME, t.optString("name"));
					values.put(Cwg.COLUMN_CWGNO, t.optString("cwgno"));
					values.put(Cwg.COLUMN_VERSION, t.optString("version"));
					values.put(Cwg.COLUMN_IMAGE, t.optString("image"));

					long id = cwg.addCwg(values);

					Log.i("after getCwg", "" + t.getInt("id") + " ins id:" + id);
				}
			}

		} catch (Exception e) {
			Log.w("MyCollection::saveData", e.getMessage());
		}

	}

}
