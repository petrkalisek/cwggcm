package cz.gcm.cwg.comm;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Binder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Xml;
import cz.gcm.cwg.constants.Comm;
import cz.gcm.cwg.constants.Exceptions;
import cz.gcm.cwg.constants.ValueNames;
import cz.gcm.cwg.exceptions.DialogException;
import cz.gcm.cwg.exceptions.LoginException;
import cz.gcm.cwg.exceptions.data.DataFailed;
import cz.gcm.cwg.exceptions.login.InvalidUsernameOrPassword;
import cz.gcm.cwg.exceptions.login.YouMustFirstLogIn;


public class CwgApi extends BaseCwgApi{

	
	private static final String LOG_TAG = CwgApi.class.getName();
	private Boolean disableSession = false;
	

	public JSONObject callUrl(String Url, List<NameValuePair> params)
			throws DataFailed, DialogException {

		JSONObject responseData = null;
		Log.d(LOG_TAG, params.toString());

		try {
			if (isLogged()) {
				Log.d(LOG_TAG, "IS LOGED:" + isLogged().toString());
				responseData = call(Url, params);
			} else {
				login();
				Log.d(LOG_TAG, "AFTER LOGIN LOGED:" + isLogged().toString());
				responseData = call(Url, params);
				/*
				if (isLogged()) {
					Log.d(LOG_TAG, "AFTER LOGIN IS LOGED:" + params.toString());
					json = call(Url, params);
				} else {
					throw new DialogException("My exception Invalid login");
				}*/
			}
		} catch (DataFailed e) {
			throw new DataFailed(e.getMessage());
		} catch (DialogException e) {
			throw new DialogException(e.getMessage());
		}

		return responseData;
	}

	public JSONObject call(String Url, List<NameValuePair> params)
			throws DataFailed, YouMustFirstLogIn, InvalidUsernameOrPassword {

		Uri.Builder UriBuilder = Uri.parse(Url).buildUpon();
		//Log.d(LOG_TAG, "disableSession: "+disableSession.toString());
		if (!disableSession) {
			//Log.d(LOG_TAG, "applySession " + Url);
			UriBuilder.appendQueryParameter(ValueNames.SETTING_SESSION,
					getSession());
		}
		disableSession = false;

		String responseData = null;
		JSONObject json = null;

		Log.d(LOG_TAG, "UriBuilder: "+UriBuilder.toString());
		Log.d(LOG_TAG, "params: "+params.toString());

		try {
			if (params.size() > 0) {
				//Log.d(LOG_TAG, "executeHttpPost");
				responseData = Communicator.executeHttpPost(UriBuilder, params);
			} else {
				//Log.d(LOG_TAG, "executeHttpGet");
				responseData = Communicator.executeHttpGet(UriBuilder);
				//Log.d(LOG_TAG, "after executeHttpGet");
			}
		} catch (Exception e) {
			Log.d(LOG_TAG, "call Exception:"+e.getMessage());
			throw new DataFailed(e.getMessage());
		}

		//Log.d(LOG_TAG, "responseData:"+responseData.toString());

		if (responseData != null) {
			try {
				json = new JSONObject(responseData);
				Log.i(LOG_TAG, json.toString());
				if (!json.getBoolean(Comm.API_SUCCESS_NAME)) {
					//Log.d(LOG_TAG, "UNSUCCESS");

					switch (json.getInt("ErrorCode")) {
					case Exceptions.API_ERROR_103:
						throw new YouMustFirstLogIn(
								json.getString(Comm.API_ERROR_NAME));
					case Exceptions.API_ERROR_101:
						throw new InvalidUsernameOrPassword(
								json.getString(Comm.API_ERROR_NAME));
					default:
						throw new UnknownError(
								json.getString(Comm.API_ERROR_NAME));
					}
				} else {
					Log.d(LOG_TAG, "SUCCESS");
				}
			} catch (JSONException e) {
				Log.d(LOG_TAG, "ZKOUSIM PASER");
				// e.printStackTrace();
				JSON jsonString = null;
				try{
					String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><cwgexport><user>PetrAJana</user><profile>http://test.cwg.gcm.cz/users/index/PetrAJana</profile><cwg id=\"3899\"><cat_no>cwg03511</cat_no><name>PetrAJana</name><category id=\"1\">Osobni CWG</category><version>1</version><image>http://cwg.gcm.cz/img/cwg03511.jpg</image><collection><entry><date>2011-05-05</date><year>2011</year><comment></comment><pieces>40</pieces></entry></collection><offers><pieces>5</pieces><comment></comment></offers></cwg></cwgexport>";
					Log.d(LOG_TAG, "xmlString:"+xmlString.toString());
					//XMLSerializer serializer = new XMLSerializer();  
					//jsonString = serializer.read(xmlString);
				}catch(Exception ex){
					throw new DataFailed("XMLSerializer:"+ex.getMessage());
				}
				
				
				Log.w(LOG_TAG, "JSONException:"+e.toString());
				Log.d(LOG_TAG, "jsonString:"+jsonString.toString());
				throw new DataFailed("JSONException");
			}
		}

		return json;
	}

	protected String getSession() {
		//Log.d(LOG_TAG, "getSession");
		SharedPreferences prefsRead = PreferenceManager
				.getDefaultSharedPreferences(getContext());
		String session = prefsRead.getString(ValueNames.SETTING_SESSION, "");
		//Log.d(LOG_TAG, "getSession return: "+session);
		return session;
	}

	private Boolean isLogged() throws LoginException, DialogException {

		//Log.d(LOG_TAG, "isLogged");
		
		Boolean isLogged = false;

		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			Log.w(LOG_TAG, "isLogged:call");
			JSONObject json = call(Comm.API_URL_LOGININFO, params);
			
			Log.w(LOG_TAG, "isLogged:json"+json.toString());
			if (json.has(Comm.API_SUCCESS_NAME)) {
				Log.d(LOG_TAG, "isLogged:return:"+json.optBoolean(Comm.API_SUCCESS_NAME));
				return json.optBoolean(Comm.API_SUCCESS_NAME);
			}
		} catch (Exception e) {
			Log.d(LOG_TAG, "isLogged:Exception"+e.toString());
			Log.w(LOG_TAG, e.getMessage());
		}

		Log.d(LOG_TAG, "isLogged:return"+isLogged.toString());
		return isLogged;

	}

	@Override
	public void login() throws DataFailed, DialogException {

		Uri.Builder UriBuilder = Uri.parse(Comm.API_URL_LOGIN).buildUpon();
		SharedPreferences prefsRead = PreferenceManager
				.getDefaultSharedPreferences(getContext());
		
		UriBuilder.appendQueryParameter(Comm.API_USERNAME_NAME, new String(
				prefsRead.getString("pref_login", "")));
		UriBuilder.appendQueryParameter(Comm.API_PASSWORD_NAME, new String(
				prefsRead.getString("pref_password", "")));
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONObject jsonLogin = null;

		try {
			disableSession = true;
			jsonLogin = call(UriBuilder.toString(), params);
			
			if (jsonLogin.has(Comm.API_SESSION_NAME)) {
				Log.d(LOG_TAG,
						"JSONLOGIN "
								+ jsonLogin.optString(Comm.API_SESSION_NAME));
				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(getContext());
				SharedPreferences.Editor preferencesEditor = prefs.edit();
				preferencesEditor.putString(ValueNames.SETTING_SESSION,
						jsonLogin.optString(Comm.API_SESSION_NAME));
				preferencesEditor.commit();
			}
		} catch (DataFailed e) {
			Log.w(LOG_TAG, "DataFailed EXCEPTION");
			throw new DataFailed(e.getMessage());
		} catch (InvalidUsernameOrPassword e) {
			throw new DialogException(e.getMessage());
		} catch (DialogException e) {
			Log.w(LOG_TAG, "JSONLOGIN DialogException");
			throw new DialogException(e.getMessage());
		}

	}

	public class ListenBinder extends Binder {
		public CwgApi getService() {
			return CwgApi.this;
		}
	}

}
