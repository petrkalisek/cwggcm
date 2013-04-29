package cz.gcm.cwg.comm;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class ActivityComm extends Activity {

	private static ActivityComm activityCommInstance = null;
	private Context context;
	private AsyncTaskActivity asyncTaskActivityInstance = null;

	ActivityComm(Context context) {
		this.context = context;
		Log.d("ActivityComm::ActivityComm", this.context.getApplicationContext().toString());
	}

	public static ActivityComm getInstance(Context ctx) {
		Log.d("ActivityComm::getInstance", ctx.getApplicationContext().toString());
		if (activityCommInstance == null) {
			activityCommInstance = new ActivityComm(ctx.getApplicationContext());
		}
		return activityCommInstance;
	}
	
	public JSONObject callObject(BaseCwgApi object){
		if (asyncTaskActivityInstance == null) {
			asyncTaskActivityInstance = new AsyncTaskActivity();
		}
		
		JSONObject result = new JSONObject();
		object.setContext(context.getApplicationContext());
		
		try{
			result = asyncTaskActivityInstance.execute(object).get();	
		}catch(Exception e){
			Log.w("ActivityComm::callObject", e.getMessage());
		}
		
		
		return result;
	}
	
	
	

	final class AsyncTaskActivity extends
			AsyncTask<BaseCwgApi, String, JSONObject> {

		@Override
		protected JSONObject doInBackground(BaseCwgApi... calledObject) {

			//calledObject;

			JSONObject jsonResult = new JSONObject();

			try {
				//Log.d("TAG2", this.context.getApplicationContext().toString());
				// TODO: hardcore for shared prefs
				//calledObject[0].setContext(this.context.getApplicationContext());
				Log.d("TAG1", calledObject[0].toString());
				jsonResult = calledObject[0].getResult();
				Log.d("TAG2", jsonResult.toString());
				// return calledObject.getResult();
			} catch (Exception e) {

			}
			return jsonResult;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			
		}

		@Override
		protected void onProgressUpdate(String... values) {
			Log.d("TAG2", values.toString());
		}

	}

}
