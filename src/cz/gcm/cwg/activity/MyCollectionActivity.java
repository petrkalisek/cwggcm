package cz.gcm.cwg.activity;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import com.example.cwggmc.R;

import cz.gcm.cwg.comm.MyCollection;
import cz.gcm.cwg.database.BaseItem;



public class MyCollectionActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_collection);
		
		ListView listenersList = (ListView)findViewById(R.id.cwgList);
		try{
			MyCollection myCollection = new MyCollection();
			AsyncTaskActivity Async = new AsyncTaskActivity();
			
			try{
				JSONObject myCollectionResult = Async.execute(myCollection).get();
				//listenersList.setAdapter(new SimpleListItem(this, myCollection.getResult()));
				Log.d("MyCollectionActivity::jsonResult",myCollectionResult.toString());
				
				if( myCollectionResult.optJSONArray("Export").length() > 0 ){
					JSONArray exportArray = myCollectionResult.getJSONArray("Export");
					
					for(int i = 0; i < exportArray.length(); i++) {
						JSONObject t = (JSONObject) exportArray.get(i);
						JSONArray collection = t.optJSONArray("collection");
						if(collection != null){
							Log.i("MyCollectionActivity","ID:" + t.getInt("id"));
							Log.i("MyCollectionActivity","collection:" + collection.toString());
						}else{
							Log.i("MyCollectionActivity","NOT COLLECTION ID:" + t.getInt("id"));
						}
					}
					
					/*
					listenersList.setAdapter(new SimpleListItem(this, myCollectionResult.optJSONArray("Export")));
					listenersList.postInvalidate();
					listenersList.invalidateViews();
					*/
				}
				
				
			}catch( Exception e){
				Log.w("MyCollectionActivity","Async.execute exception:" + e.toString());
			}
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
