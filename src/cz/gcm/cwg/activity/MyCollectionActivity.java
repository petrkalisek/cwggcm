package cz.gcm.cwg.activity;

import java.util.List;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cz.gcm.cwg.R;
import cz.gcm.cwg.comm.ActivityComm;
import cz.gcm.cwg.comm.MyCollection;
import cz.gcm.cwg.database.items.Cwg;
import cz.gcm.cwg.layouts.SimpleCwgItem;

public class MyCollectionActivity extends BaseActivity {

	
	private Cwg cwg = null;
	private Cursor databaseResult = null;
	
	private Boolean dataLoaded = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_collection);
		
		
		
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d("MyCollectionActivity::onStart", "onStart");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		//Log.d("MyCollectionActivity::onResume", "onResume");
		loadData(false);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		//Log.d("MyCollectionActivity::onPause", "onPause");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_cwg, menu);
		return true;
	}

	public void refreshData(View view) {
		dataLoaded = false;
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

		if ( !useCacheData || !dataLoaded) {
			try {
				ActivityComm activityCommInstance = ActivityComm.getInstance(MyCollectionActivity.this);
				JSONObject result = activityCommInstance.callObject(new MyCollection(MyCollectionActivity.this));
				Log.d("MyCollectionActivity::callObject", "result:"+result.toString());
			} catch (Exception e) {
				Log.w("MyCollectionActivity", "Async.execute exception 2:"
						+ e.toString());
			}
		}
		dataLoaded = true;
		
		ListView listenersList = (ListView) findViewById(R.id.cwgList);
		
		try{
			List<Cwg> List = getCwgDao().queryForAll();
			
			SimpleCwgItem SimpleListItem = new SimpleCwgItem(this, List);
			listenersList.setAdapter(SimpleListItem);
			listenersList.setOnItemClickListener(new OnItemClickListener(){
	            @Override
	            public void onItemClick(AdapterView<?> parent, View v , int position, long id){
	            	Intent i = new Intent(MyCollectionActivity.this , DetailCwgActivity.class);
	            	i.putExtra("id", id);
	                startActivity(i);
	            }
	        });	
		}catch(Exception e){
			Log.w("MyCollectionActivity::loadData", e.getMessage());
		}
		

	}
	

	
	protected void showDialog(){
		ProgressDialog progress = new ProgressDialog(this);
		progress.setMessage("Loading...");
		progress.show();
	}
	
	@Override
	protected void onDestroy() {
		
		Log.i("MyCollectionActivity", "onDestroy");
		super.onDestroy();

	}
	/*
	@Override
	protected void onPause() {
		cwg.onClose();
		Log.i("MyCollectionActivity", "onPause");
		super.onPause();
	}*/

}
