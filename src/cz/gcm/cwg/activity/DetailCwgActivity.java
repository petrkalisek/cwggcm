package cz.gcm.cwg.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import cz.gcm.cwg.R;

public class DetailCwgActivity extends Activity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_cwg);
		Bundle extras = getIntent().getExtras();
		
		long cwgId = extras.getLong("id");
		
		TextView textViewId = (TextView) findViewById(R.id.cwgId);
		textViewId.setText(""+cwgId);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail_cwg, menu);
		return true;
	}

}
