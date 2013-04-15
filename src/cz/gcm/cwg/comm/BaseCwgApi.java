package cz.gcm.cwg.comm;

import org.json.JSONException;
import org.json.JSONObject;

import cz.gcm.cwg.exceptions.DialogException;
import cz.gcm.cwg.exceptions.data.DataFailed;

import android.content.Context;
import android.util.Log;

public class BaseCwgApi {

	protected Context context = null;
	
	public void setContext(Context incomeContext) {
		context = incomeContext;
	}
	
	public Context getContext() {
		return context;
	}
	
	public JSONObject getResult(){
		JSONObject object = new JSONObject();
		
		try{
			object.put("result", "result string");	
		}catch(JSONException e){
			
		}
		
		
		return object;
	}
	
	public void login() throws DataFailed, DialogException {}
	
	
}
