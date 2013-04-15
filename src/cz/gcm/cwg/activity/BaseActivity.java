package cz.gcm.cwg.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cwggmc.R;

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
	
	protected class AsyncTaskActivity extends AsyncTask<String, Void, String> {
		
		ProgressDialog progressDialog = new ProgressDialog(BaseActivity.this); 
		
		@Override
		protected void onPreExecute() {
	        this.progressDialog.setMessage("Please wait...");
	        this.progressDialog.show();
	    }
		
		
	    @Override
	    protected String doInBackground(String... params) {
	    	
	          for(int i=0;i<5;i++) {
	              try {
	                  Thread.sleep(1000);
	                  Log.d("TAG", ""+i);
	              } catch (InterruptedException e) {
	                  // TODO Auto-generated catch block
	                  e.printStackTrace();
	              }
	          }
	          
	          return null;
	    }      

	    @Override
	    protected void onPostExecute(String result) {  
	    	progressDialog.dismiss();
	    	TextView txt = (TextView) findViewById(R.id.response);
          txt.setText("Executed:"+result);
	    }

	    @Override
	    protected void onProgressUpdate(Void... values) {
	    }
	}

}
