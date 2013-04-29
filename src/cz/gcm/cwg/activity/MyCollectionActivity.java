package cz.gcm.cwg.activity;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cwggmc.R;

import cz.gcm.cwg.comm.ActivityComm;
import cz.gcm.cwg.comm.MyCollection;
import cz.gcm.cwg.database.items.Cwg;
import cz.gcm.cwg.layouts.SimpleListItem;

public class MyCollectionActivity extends BaseActivity {

	private ProgressDialog progressDialog;
	private Cwg cwg = null;
	private Cursor databaseResult = null;
	
	private Boolean dataLoaded = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_collection);
		cwg = new Cwg(this);
		loadData(false);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d("MyCollectionActivity::onStart", "onStart");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d("MyCollectionActivity::onResume", "onResume");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.d("MyCollectionActivity::onPause", "onPause");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_cwg, menu);
		return true;
	}

	public void refreshData(View view) {
		Toast.makeText(getApplicationContext(), "refreshData",
				Toast.LENGTH_LONG).show();
		loadData(true);
	}

	public void onBackPressed() {
		super.onBackPressed();
		Log.d("MyCollectionActivity::onBackPressed", "CLICK BACK");
	};

	public void startActivityForResult() {
		Log.d("MyCollectionActivity::startActivityForResult", "CLICK BACK");
	}

	private void loadData(Boolean force) {
				
		SharedPreferences prefsRead = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		Boolean useCacheData = prefsRead.getBoolean("pref_useCacheData", false);
		Log.d("useCacheData", useCacheData.toString());
		Log.d("dataLoaded", dataLoaded.toString());

		//showProcessDialog(this);

		if ( (!useCacheData || force) && !dataLoaded) {
			
			try {
				Log.d("loadData", this.getApplicationContext().toString());
				ActivityComm activityCommInstance = ActivityComm.getInstance(this);
				
				JSONObject myCollectionResult = activityCommInstance.callObject(new MyCollection());
				Log.w("MyCollectionActivity", "loadData::MyCollection"
						+ myCollectionResult.toString());
						
				
				/*
				AsyncTaskActivity Async = new AsyncTaskActivity( );

				try {
					JSONObject myCollectionResult = Async.execute(this)
							.get();

					//Log.d("MyCollectionActivity::jsonResult",
					//		myCollectionResult.toString());

					if (myCollectionResult.optJSONArray("Export").length() > 0) {
						JSONArray exportArray = myCollectionResult
								.getJSONArray("Export");
						Cursor c = null;
						
						for (int i = 0; i < exportArray.length(); i++) {
							JSONObject t = (JSONObject) exportArray.get(i);
							JSONArray collection = t.optJSONArray("collection");
							if (collection != null) {
								Log.i("MyCollectionActivity",
										"ID:" + t.getInt("id"));
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
							values.put(Cwg.COLUMN_VERSION,	t.optString("version"));
							values.put(Cwg.COLUMN_IMAGE, t.optString("image"));
							
							long id = cwg.addCwg(values);
							
							Log.i("after getCwg","" + t.getInt("id")+ " ins id:"+id);
						}
					}

				} catch (Exception e) {
					Log.w("MyCollectionActivity", "Async.execute exception 1:"
							+ e.toString());
				}*/
			} catch (Exception e) {
				Log.w("MyCollectionActivity", "Async.execute exception 2:"
						+ e.toString());
			}
		}
		dataLoaded = true;
		
		
		ListView listenersList = (ListView) findViewById(R.id.cwgList);
		Cursor databaseResult = cwg.getAllCwg();
		listenersList.setAdapter(new SimpleListItem(this, databaseResult));
		Log.i("loadData::cursor", databaseResult.toString()+" "+databaseResult.getCount());
		
		//hideProcessDialog();

	}
	/*
	@Override
	protected void onStop() {
		
		cwg.onClose();
		Log.i("MyCollectionActivity", "onStop");
		super.onStop();

	}

	@Override
	protected void onPause() {
		cwg.onClose();
		Log.i("MyCollectionActivity", "onPause");
		super.onPause();
	}*/

}
