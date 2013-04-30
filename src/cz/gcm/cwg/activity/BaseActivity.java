package cz.gcm.cwg.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import cz.gcm.cwg.R;
import cz.gcm.cwg.comm.BaseCwgApi;
import cz.gcm.cwg.database.BaseDatabaseHelper;
import cz.gcm.cwg.database.CwgDatabaseHelper;
import cz.gcm.cwg.database.items.Cwg;

abstract class BaseActivity extends Activity {

	private BaseDatabaseHelper databaseHelper = null;
	private CwgDatabaseHelper cwgDatabaseHelper = null;
	private BaseCwgApi calledObject = null;
	protected ProgressDialog progressDialog = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	protected Dao<Cwg, Integer> getCwgDao(){
		if(cwgDatabaseHelper == null){
			cwgDatabaseHelper = new CwgDatabaseHelper(getApplicationContext());	
		}
		try{
			return cwgDatabaseHelper.getCwgDao();	
		}catch(Exception e){
			return null;
		}
		
	}
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (databaseHelper != null) {
			OpenHelperManager.releaseHelper();
			databaseHelper = null;
		}
	}

	protected BaseDatabaseHelper getDatabaseHelper() {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(this,
					BaseDatabaseHelper.class);
		}
		return databaseHelper;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	/*
	 * 
	 * 
	 * final class AsyncTaskActivity extends AsyncTask<Context, String,
	 * JSONObject> {
	 * 
	 * private BaseCwgApi action; private Context context;
	 * 
	 * public AsyncTaskActivity(BaseCwgApi action) { this.action = action; }
	 * 
	 * 
	 * @Override protected JSONObject doInBackground(Context... context){
	 * //this.progress.show();
	 * 
	 * this.context = context[0];
	 * 
	 * JSONObject jsonResult = new JSONObject();
	 * 
	 * try{ calledObject = this.action; //TODO: hardcore for shared prefs
	 * calledObject.setContext(this.context); Log.d("TAG1",
	 * calledObject.toString()); jsonResult = calledObject.getResult();
	 * Log.d("TAG2", jsonResult.toString()); //return calledObject.getResult();
	 * }catch( Exception e){
	 * 
	 * } return jsonResult; }
	 * 
	 * @Override protected void onPostExecute(JSONObject result) {
	 * Toast.makeText(getApplicationContext(), "onPostExecute",
	 * Toast.LENGTH_LONG).show(); }
	 * 
	 * @Override protected void onProgressUpdate(String... values) {
	 * Log.d("TAG2", values.toString()); Toast.makeText(getApplicationContext(),
	 * "onProgressUpdate", Toast.LENGTH_LONG).show(); }
	 * 
	 * 
	 * 
	 * }
	 */

}
