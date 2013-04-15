package cz.gcm.cwg.comm;

import android.util.Log;

public class ApiResult {

	static String xxx = "neco cokoliv jako callback";
	
	static public String resultCallback(){
		Log.i("resultCallback", ApiResult.xxx);
		return ApiResult.xxx;
	}
	
	public void setResult(String text){
		ApiResult.xxx = text;
	}
}
