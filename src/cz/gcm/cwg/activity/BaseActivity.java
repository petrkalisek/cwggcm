package cz.gcm.cwg.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.example.cwggmc.R;

import cz.gcm.cwg.comm.BaseCwgApi;

abstract class BaseActivity extends Activity {

	private BaseCwgApi calledObject = null;
	protected ProgressDialog progressDialog = null;
	
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

	
	protected void showProcessDialog(Context context){
		if(progressDialog == null){
			progressDialog = new ProgressDialog(context);	
			progressDialog.setCancelable(true);
			progressDialog.setMessage("Loading data, please wait ...");
		}
		progressDialog.show();
	}
	
	protected void hideProcessDialog(){
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
        }
	}
	
	
	/*
	
	
	final class AsyncTaskActivity extends
			AsyncTask<Context, String, JSONObject> {
		
		private BaseCwgApi action;
		private Context context;
		
		public AsyncTaskActivity(BaseCwgApi action)
        {
            this.action = action;
        }
		
		
		@Override
		protected JSONObject doInBackground(Context... context){
			//this.progress.show();
			
			this.context = context[0];
			
			JSONObject jsonResult = new JSONObject();
			
			try{
				calledObject = this.action;
				//TODO: hardcore for shared prefs 
				calledObject.setContext(this.context);
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
			Toast.makeText(getApplicationContext(), "onPostExecute", Toast.LENGTH_LONG).show();
		}

		@Override
		protected void onProgressUpdate(String... values) {
			Log.d("TAG2", values.toString());
			Toast.makeText(getApplicationContext(), "onProgressUpdate", Toast.LENGTH_LONG).show();
		}
		
		
		
	}
	*/
	

}
