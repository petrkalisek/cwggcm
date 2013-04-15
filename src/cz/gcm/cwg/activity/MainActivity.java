package cz.gcm.cwg.activity;

import android.os.Bundle;
import android.view.View;

import com.example.cwggmc.R;

public class MainActivity extends BaseActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void clickDefault(View view){
		
		new AsyncTaskActivity().execute("");
		/*
		Button b = (Button)view;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(b.getText().toString());
        builder.setTitle("clickDefault").show();
        */
        
	}
}
