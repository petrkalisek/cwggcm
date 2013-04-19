package cz.gcm.cwg.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import com.example.cwggmc.R;

import cz.gcm.cwg.comm.MyCollection;



public class MyCollectionActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_collection);
		
		ListView listenersList = (ListView)findViewById(R.id.cwgList);
		try{
			MyCollection myCollection = new MyCollection();
			AsyncTaskActivity Async = new AsyncTaskActivity();
			
			/*
			 * PROBLEM S XML a JSON */
			try{
				Async.execute(myCollection);
			}catch( Exception e){
				Log.w("MyCollectionActivity","Async.execute exception:" + e.toString());
			}
			
			/*
			JSONObject result = myCollection.getResult();
			listenersList.setAdapter(new SimpleListItem(this, myCollection.getResult()));
			Log.d("MyCollectionActivity","result:" + result.toString());
			*/
			//listenersList.setAdapter(new SimpleListItem(this, CwgInfo.getCwgInfo("petrajana")));
		}catch(Exception e){
			
		}
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_cwg, menu);
		return true;
	}

}
