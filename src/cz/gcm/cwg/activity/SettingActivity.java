package cz.gcm.cwg.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.example.cwggmc.R;



public class SettingActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
    }
}