package cz.gcm.cwg.activity;

import java.util.List;

import com.j256.ormlite.dao.Dao;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import cz.gcm.cwg.R;
import cz.gcm.cwg.database.items.Cwg;

public class DetailCwgActivity extends BaseActivity {

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
		
		int intId = (int) id;
		try{
			Cwg Cwg = getCwgDao().queryForId(intId);

			TextView textViewId = (TextView) findViewById(R.id.cwgId);
			textViewId.setText(""+id);
			
			TextView textViewDetailTitle = (TextView) findViewById(R.id.detailTitle);
			textViewDetailTitle.setText(Cwg.getName());
			
			
		}catch(Exception e){
			
		}
		
		
		
		
		
	}
	
	@Override
	protected void onDestroy() {
		//Cwg.getInstance(getApplicationContext()).close();
		Log.i("DetailCwgActivity", "onDestroy");
		super.onStop();

	}
	
}
