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

import cz.gcm.cwg.comm.MyCollection;
import cz.gcm.cwg.database.items.Cwg;
import cz.gcm.cwg.layouts.SimpleListItem;

public class MyCollectionActivity extends BaseActivity {

	private ProgressDialog progressDialog;
	private Cwg cwg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_collection);

	}

	@Override
	protected void onStart() {
		cwg = new Cwg(this);
		super.onStart();
		cwg.onClose();
		cwg.onStart();
		loadData(false);
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
	};

	public void startActivityForResult() {
		Log.d("MyCollectionActivity::startActivityForResult", "CLICK BACK");
	}

	private void loadData(Boolean force) {
		SharedPreferences prefsRead = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		Boolean useCacheData = prefsRead.getBoolean("pref_useCacheData", false);
		Log.d("useCacheData", useCacheData.toString());

		if (!useCacheData || force) {
			try {
				MyCollection myCollection = new MyCollection();
				AsyncTaskActivity Async = new AsyncTaskActivity();

				try {
					JSONObject myCollectionResult = Async.execute(myCollection)
							.get();

					Log.d("MyCollectionActivity::jsonResult",
							myCollectionResult.toString());

					if (myCollectionResult.optJSONArray("Export").length() > 0) {
						JSONArray exportArray = myCollectionResult
								.getJSONArray("Export");

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
							values.put(Cwg.COLUMN_VERSION,
									t.optString("version"));
							values.put(Cwg.COLUMN_IMAGE, t.optString("image"));

							long id = 0;
							if (cwg.getCwg(t.getInt("id")).getCount() > 0) {
								id = cwg.updateCwg(t.getInt("id"), values);
								Log.i("MyCollectionActivity",
										"update database:" + String.valueOf(id));
							} else {
								id = cwg.addCwg(values);
								Log.i("MyCollectionActivity", "add database:"
										+ String.valueOf(id));
							}
						}
					}

				} catch (Exception e) {
					Log.w("MyCollectionActivity", "Async.execute exception:"
							+ e.toString());
				}
			} catch (Exception e) {

			}
		}

		ListView listenersList = (ListView) findViewById(R.id.cwgList);
		Cursor c = cwg.getAllCwg();
		listenersList.setAdapter(new SimpleListItem(this, c));

	}

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
	}

}
