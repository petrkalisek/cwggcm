package cz.gcm.cwg.comm;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Binder;
import android.preference.PreferenceManager;
import android.util.Log;
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

		try {
			if (isLogged()) {
				responseData = call(Url, params);
			} else {
				login();
				responseData = call(Url, params);
				/*
				 * if (isLogged()) { Log.d(LOG_TAG, "AFTER LOGIN IS LOGED:" +
				 * params.toString()); json = call(Url, params); } else { throw
				 * new DialogException("My exception Invalid login"); }
				 */
			}
		} catch (DataFailed e) {
			throw new DataFailed(e.getMessage());
		} catch (DialogException e) {
			throw new DialogException(e.getMessage());
		}

		return responseData;
	}

	private JSONObject call(String Url, List<NameValuePair> params)
			throws DataFailed, YouMustFirstLogIn, InvalidUsernameOrPassword {

		Uri.Builder UriBuilder = Uri.parse(Url).buildUpon();
		if (!disableSession) {
			UriBuilder.appendQueryParameter(ValueNames.SETTING_SESSION,
					getSession());
		}
		disableSession = false;

		String responseData = null;
		JSONObject json = null;

		//Log.d(LOG_TAG, "UriBuilder: " + UriBuilder.toString());
		//Log.d(LOG_TAG, "params: " + params.toString());

		try {
			if (params.size() > 0) {
				responseData = Communicator.executeHttpPost(UriBuilder, params);
			} else {
				responseData = Communicator.executeHttpGet(UriBuilder);
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, "call Exception:" + e.getMessage());
			throw new DataFailed(e.getMessage());
		}

		// Log.d(LOG_TAG, "responseData:"+responseData.toString());

		if (responseData != null) {
			try {
				json = new JSONObject(responseData);
				//Log.i(LOG_TAG, json.toString());
				if (!json.getBoolean(Comm.API_SUCCESS_NAME)) {
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
				throw new DataFailed("JSONException:" + e.getMessage());
			}
		}

		return json;
	}

	protected String getSession() {
		// Log.d(LOG_TAG, "getSession");
		SharedPreferences prefsRead = PreferenceManager
				.getDefaultSharedPreferences(getContext());
		String session = prefsRead.getString(ValueNames.SETTING_SESSION, "");
		// Log.d(LOG_TAG, "getSession return: "+session);
		return session;
	}

	private Boolean isLogged() throws LoginException, DialogException {

		// Log.d(LOG_TAG, "isLogged");

		Boolean isLogged = false;

		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject json = call(Comm.API_URL_LOGININFO, params);

			if (json.has(Comm.API_SUCCESS_NAME)) {
				return json.optBoolean(Comm.API_SUCCESS_NAME);
			}
		} catch (InvalidUsernameOrPassword e) {
			throw new LoginException(e.getMessage());
		} catch (Exception e) {
			Log.e(LOG_TAG, e.getMessage());
		}
		return isLogged;

	}

	private void login() throws DataFailed, DialogException {

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
				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(getContext());
				SharedPreferences.Editor preferencesEditor = prefs.edit();
				preferencesEditor.putString(ValueNames.SETTING_SESSION,
						jsonLogin.optString(Comm.API_SESSION_NAME));
				preferencesEditor.commit();
			}
		} catch (DataFailed e) {
			Log.e(LOG_TAG, "DataFailed EXCEPTION");
			throw new DataFailed(e.getMessage());
		} catch (InvalidUsernameOrPassword e) {
			throw new DialogException(e.getMessage());
		} catch (DialogException e) {
			Log.e(LOG_TAG, "JSONLOGIN DialogException");
			throw new DialogException(e.getMessage());
		}

	}

	public class ListenBinder extends Binder {
		public CwgApi getService() {
			return CwgApi.this;
		}
	}

}
