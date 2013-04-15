package cz.gcm.cwg.layouts;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.cwggmc.R;

public class SimpleListItem implements ListAdapter {

	
	public LayoutInflater mInflater=null;
	public int type = 0;
	private Context context = null;
	private JSONObject data = null;

	public SimpleListItem(Context contextIncome, JSONObject dataIncome) {
		context = contextIncome;
		data = dataIncome;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public JSONObject data() {
		return new JSONObject();
	}



	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		//JSONObject bean = getItem(arg0);

		View row = arg1;
		
		if (row == null) {
			row = mInflater.inflate(R.layout.simple_list_item,	null);
		}
		
		TextView txtTitle = (TextView) row.findViewById(R.id.title);
		TextView txtDesc = (TextView) row.findViewById(R.id.desc);
		TextView id = (TextView) row.findViewById(R.id.viewId);
		
		txtTitle.setText("title");
		txtDesc.setText("desc");
		id.setText(1);
		
		return row;
	}
/*
	@Override
	public boolean onLongClick(View v) {
		final String id = (String) ((TextView) v.findViewById(R.id.viewID))
				.getText();

		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					if (type == 0) {
						player.oblibene.remove(id);
					} else {
						player.listeners.remove(id);
					}

					lv.invalidateViews();
					player.savedata();
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					// No button clicked
					break;
				}

			}
		};

		Context c = v.getContext();
		AlertDialog.Builder builder = new AlertDialog.Builder(c);

		try {
			builder.setMessage(
					"Opravdu odstranit radio "
							+ data().get(id).getString("name") + "?")
					.setPositiveButton("Ano", dialogClickListener)
					.setNegativeButton("Ne", dialogClickListener).show();
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		String id = (String) ((TextView) v.findViewById(R.id.viewID))
		.getText();
		player.aktRadio = data().get(id);
		player.play();
		lv.invalidateViews();
		
		Intent intent = new Intent(context.getApplicationContext(), PlayerActivity.class);
		context.startActivity(intent);		
		
	}
*/

	@Override
	public int getViewTypeCount() {
		return 1;
	}
	

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		return true;
	}

	@Override
	public int getCount() {
		return data.length();
	}
	
	// interface
	
	
	@Override
	public boolean isEmpty() {
		return false;
	}
	
	@Override
	public JSONObject getItem(int id) {
		
		return new JSONObject();
		
		/*
		JSONObject country = data.getJSONObject(id);
		
		
		Iterator keys = data.keys();

	    while(keys.hasNext()) {
	        // loop to get the dynamic key
	        String currentDynamicKey = (String)keys.next();

	        // get the value of the dynamic key
	        JSONObject currentDynamicValue = data.getJSONObject(currentDynamicKey);

	        // do something here with the value...
	    }*/
	}

	@Override
	public long getItemId(int arg0) {
		try {
			return getItem(arg0).getLong("id");
		} catch (JSONException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public int getItemViewType( int number) {
		return number;
	}
	
}
