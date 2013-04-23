package cz.gcm.cwg.activity;

import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import com.example.cwggmc.R;

import cz.gcm.cwg.comm.MyCollection;
import cz.gcm.cwg.exceptions.LoginException;



public class MyCollectionActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_collection);
		
		ListView listenersList = (ListView)findViewById(R.id.cwgList);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_cwg, menu);
		return true;
	}

}
