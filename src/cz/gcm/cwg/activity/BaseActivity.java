package cz.gcm.cwg.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.example.cwggmc.R;

import cz.gcm.cwg.comm.ApiResult;
import cz.gcm.cwg.comm.BaseCwgApi;

abstract class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	protected class AsyncTaskActivity extends
			AsyncTask<BaseCwgApi, String, JSONObject> {

		ProgressDialog progressDialog = new ProgressDialog(BaseActivity.this);

		@Override
		protected void onPreExecute() {
			this.progressDialog.setCancelable(true);
			this.progressDialog.setMessage("Loading data, please wait...");
			this.progressDialog.show();

		}

		@Override
		protected JSONObject doInBackground(BaseCwgApi... object) {

			JSONObject jsonResult = new JSONObject();
			
			try{
				BaseCwgApi calledObject = object[0];
				
				//TODO: hardcore for shared prefs 
				calledObject.setContext(getApplicationContext());
				
				Log.d("TAG1", calledObject.toString());
				
				jsonResult = calledObject.getResult();	
				
				Log.d("TAG2", jsonResult.toString());
				//return calledObject.getResult();	
			}catch( Exception e){
				
				
			}
			
			return jsonResult;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			TextView txt = (TextView) findViewById(R.id.response);
			Log.d("onPostExecute", result.toString());
			txt.setText("Executed:" + result);
			this.progressDialog.dismiss();
		}

		@Override
		protected void onProgressUpdate(String... values) {
			Log.d("TAG2", values.toString());
			this.progressDialog.setMessage("onProgressUpdate:"
					+ ApiResult.resultCallback());
		}
	}

}
