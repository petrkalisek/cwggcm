package cz.gcm.cwg.activity;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.cwggmc.R;

import cz.gcm.cwg.comm.CwgInfo;


public class MainActivity extends BaseActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	public void clickMyCollection(View view){
		try{
			Intent intent = new Intent(this, MyCollectionActivity.class);
			startActivity(intent);	
		}catch( Exception e){
			Log.w("MainActivity::clickMyCollection", e.getMessage());
		}
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.setting:
	        	Intent intent = new Intent(this, SettingActivity.class);
	        	Log.d("MainActivity::onOptionsItemSelected", "settying");
	    		startActivity(intent);
	    		finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void settingActivity(View view){
		Intent intent = new Intent(this, SettingActivity.class);
    	Log.d("MainActivity::openSettingActivity", "settying");
		startActivity(intent);
	}
	
	
	public void startActivityForResult(){
		Log.d("MainActivity::startActivityForResult", "CLICK BACK");
	}


	public void clickDefault(View view){
		
		ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Loading, please wait");
		progressDialog.show();
		
		try{
			CwgInfo cwgInfo = new CwgInfo("petrajana");
			AsyncTaskActivity Async = new AsyncTaskActivity();
			JSONObject cwgInfoResult = Async.execute(cwgInfo).get();
			
			
			Log.w("MainActivity::clickDefault", cwgInfoResult.toString());
			
		}catch( Exception e){
			Log.w(this.getClass().toString(), e.getMessage());
		}
		
		
		
		/*
		Button b = (Button)view;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(b.getText().toString());
        builder.setTitle("clickDefault").show();
        */
        
	}
}
