package cz.gcm.cwg.comm;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class ActivityComm extends Activity {

	private static ActivityComm activityCommInstance = null;
	final Context context;
	private AsyncTaskActivity asyncTaskActivityInstance = null;

	ActivityComm(Context context) {
		this.context = context;
	}

	public static ActivityComm getInstance(Context ctx) {
		if (activityCommInstance == null) {
			activityCommInstance = new ActivityComm(ctx.getApplicationContext());
		}
		return activityCommInstance;
	}

	public JSONObject callObject(BaseCwgApi object) {
		ProgressDialog progress = new ProgressDialog(context);
		progress.setMessage("Loading...");

		if (asyncTaskActivityInstance == null) {
			asyncTaskActivityInstance = new AsyncTaskActivity(progress);
		}

		JSONObject result = new JSONObject();
		object.setContext(context.getApplicationContext());

		try {
			result = asyncTaskActivityInstance.execute(object).get();
		} catch (Exception e) {
			Log.w("ActivityComm::callObject", e.getMessage());
		}
		;
		return result;
	}

	final class AsyncTaskActivity extends
			AsyncTask<BaseCwgApi, String, JSONObject> {

		private ProgressDialog progress;

		public AsyncTaskActivity(ProgressDialog progress) {
			this.progress = progress;
		}

		public void onPreExecute() {
			progress.show();
		}

		@Override
		protected JSONObject doInBackground(BaseCwgApi... calledObject) {

			// calledObject;

			JSONObject jsonResult = new JSONObject();

			try {
				// Log.d("TAG2",
				// this.context.getApplicationContext().toString());
				// TODO: hardcore for shared prefs
				// calledObject[0].setContext(this.context.getApplicationContext());
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
			progress.dismiss();
		}

		@Override
		protected void onProgressUpdate(String... values) {
			Log.d("TAG2", values.toString());
		}

	}

}
