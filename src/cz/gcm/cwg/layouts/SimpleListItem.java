package cz.gcm.cwg.layouts;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import cz.gcm.cwg.R;
import cz.gcm.cwg.database.items.Cwg;

//OnLongClickListener
public class SimpleListItem implements ListAdapter   {

	
	public LayoutInflater mInflater=null;
	public int type = 0;
	private Context context = null;
	private Cursor data;

	public SimpleListItem(Context contextIncome, Cursor dataIncome) {
		context = contextIncome;
		data = dataIncome;
		data.moveToFirst();
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View row = convertView;
		if (row == null) {
			row = mInflater.inflate(R.layout.simple_list_item,	null);
		}
		
		if( data.moveToPosition(position) ){
			TextView txtTitle = (TextView) row.findViewById(R.id.detailTitle);
			TextView txtDesc = (TextView) row.findViewById(R.id.desc);
			TextView id = (TextView) row.findViewById(R.id.viewId);
			TextView version = (TextView) row.findViewById(R.id.version);
			
			txtTitle.setText(""+data.getString(data.getColumnIndexOrThrow(Cwg.COLUMN_NAME)));
			txtDesc.setText(""+data.getString(data.getColumnIndexOrThrow(Cwg.COLUMN_CWGNO)));
			id.setText(""+data.getInt(data.getColumnIndexOrThrow(Cwg.COLUMN_ID)));
			version.setText(""+data.getInt(data.getColumnIndexOrThrow(Cwg.COLUMN_VERSION)));
		}
	
		return row;
	}
		
	@Override
	public long getItemId(int position) {
		data.moveToFirst();
		data.moveToPosition(position);
		
		return data.getInt(data.getColumnIndexOrThrow(Cwg.COLUMN_ID));
	}
	
	public Cursor getItem(int position){
		data.moveToFirst();
		data.moveToPosition(position);
		return data;
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
		return data.getCount();
	}
	
	// interface
	
	
	@Override
	public boolean isEmpty() {
		if(data.getCount() > 0){
			return false;
		}else{
			return true;
		}
	}
		
	@Override
	public int getItemViewType( int number) {
		
		return number;
	}


}
