package cz.gcm.cwg.comm;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;

public class BaseCwgApi extends Activity{

	protected Context context = null;

	public void setContext(Context incomeContext) {
		context = incomeContext;
	}

	public Context getContext() {
		return context;
	}

	public JSONObject getResult() {
		JSONObject object = new JSONObject();

		try {
			object.put("result", "result string");
		} catch (JSONException e) {

		}

		return object;
	}

}
