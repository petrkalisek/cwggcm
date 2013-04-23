package cz.gcm.cwg.comm;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.util.Log;
import cz.gcm.cwg.constants.Comm;
import cz.gcm.cwg.exceptions.DialogException;
import cz.gcm.cwg.exceptions.LoginException;

public class MyCollection extends CwgApi {

	private static final String LOG_TAG = MyCollection.class.getName();
	
	public JSONObject getResult() {

		JSONObject jsonCwgInfo = null;
		
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("format", "json"));
			Log.i(LOG_TAG, params.toString());
			

			jsonCwgInfo = callUrl(Comm.API_URL_MY_COLLECTION, params);
			Log.i(LOG_TAG+"result jsonCwgInfo:", jsonCwgInfo.toString());
			return jsonCwgInfo;
			/*
			try{
				return jsonCwgInfo;
			
				if(json[Comm.API_ERROR_NAME] > 0){
					return jsonCwgInfo; 
				}
			}catch(JSONException e){
				Log.d(LOG_TAG, "JSONException:"+jsonCwgInfo.toString());
			}*/
		} catch (LoginException e) {
			Log.w(LOG_TAG, e.getMessage());
		} catch (DialogException e) {
			Log.w(LOG_TAG, e.getMessage());
		}
		return jsonCwgInfo;
	
	}
	
	
}
