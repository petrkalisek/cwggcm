package cz.gcm.cwg.comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;

import android.net.Uri;
import android.util.Log;

public class Communicator {

	private static final String LOG_TAG = Communicator.class.getName();
	private static final int contentLength = 16384;

	static public String executeHttpPost(Uri.Builder URL, List<NameValuePair> params)
			throws Exception {
		BufferedReader in = null;
		Log.d(LOG_TAG, "executeHttpPost:"+URL.toString());
		try {
			
			HttpPost post = new HttpPost(URL.toString());
			
			StringEntity se = new StringEntity(params.toString());
			se.setContentType("text/plain;charset=UTF-8");
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "charset=UTF-8")); 
			post.setEntity(new UrlEncodedFormEntity(params));

			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(post);

			HttpEntity responseEntity = response.getEntity();
	        Log.d(LOG_TAG, responseEntity.getContentType().toString());
	        
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()), contentLength);

			StringBuffer sb = new StringBuffer("");
			String line = "";

			// String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line // + NL
				);
			}
			
			in.close();
			String page = sb.toString();
			//Log.d(LOG_TAG, page);
			return page;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					Log.d(LOG_TAG, e.toString());
				}
			}
		}
	}

	static public String executeHttpGet(Uri.Builder URL) throws Exception {
		BufferedReader in = null;
		
		try {
		
			HttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
					"android");
			
			HttpGet request = new HttpGet();
			request.setHeader("Content-Type", "text/plain; charset=utf-8");
			request.setURI(new URI(URL.toString()));
		
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()), contentLength);
			
			
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				sb.append(line // + NL
				);
			}
			in.close();
			String page = sb.toString();
			return page;
		}catch(Exception e ){
			Log.d(LOG_TAG, "Exception:"+e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					Log.d(LOG_TAG, "IOException:"+e.toString());
				}
			}
		}
	}

}
