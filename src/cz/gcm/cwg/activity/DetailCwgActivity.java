package cz.gcm.cwg.activity;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import cz.gcm.cwg.R;
import cz.gcm.cwg.database.items.Cwg;

public class DetailCwgActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_cwg);
		Bundle extras = getIntent().getExtras();
		
		renderDetail(extras.getLong("id"));
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail_cwg, menu);
		return true;
	}

	private void renderDetail(long id){
		/*
		Cwg cwgInstance = Cwg.getInstance(getApplicationContext());
		Cursor cwg = cwgInstance.getCwg(id);
		
		TextView textViewId = (TextView) findViewById(R.id.cwgId);
		textViewId.setText(""+id);
		
		TextView textViewDetailTitle = (TextView) findViewById(R.id.detailTitle);
		textViewDetailTitle.setText(cwg.getString(cwg.getColumnIndexOrThrow(Cwg.COLUMN_NAME)));
		
		cwgInstance.close();
		*/
	}
	
	@Override
	protected void onDestroy() {
		//Cwg.getInstance(getApplicationContext()).close();
		Log.i("DetailCwgActivity", "onDestroy");
		super.onStop();

	}
	
}
