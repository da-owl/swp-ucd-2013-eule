package com.example.swp_ucd_2013_eule.net;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

/**
 * 
 * Example for making a POST-request from an Activity (in this case
 * MainActivity):
 * 
 * <pre>
 * URL url = null;
 * try {
 * 	url = new URL(&quot;http://10.0.2.2/?q=test&quot;);
 * } catch (MalformedURLException e) {
 * }
 * 
 * new AsyncTask&lt;URL, Void, String&gt;() {
 * 
 * 	&#064;Override
 * 	protected String doInBackground(URL... params) {
 * 		Response r = null;
 * 		try {
 * 			Client c = Client.getInstance();
 * 			c.init(&quot;MKay&quot;, &quot;topSecret&quot;);
 * 			// sending a JSON-object using a POST-request
 * 			r = c.post(MainActivity.this, params[0], new JSONObject(
 * 					&quot;{forest:123, action:\&quot;updateLevel\&quot;, newLevel:2}&quot;));
 * 		} catch (IOException e) {
 * 			return e.getLocalizedMessage();
 * 		} catch (JSONException e) {
 * 			return e.getLocalizedMessage();
 * 		}
 * 		if (!r.wasConnectionAvailable()) {
 * 			return &quot;No connection available!&quot;;
 * 		} else {
 * 			// instead you may use r.getJsonResponse() !
 * 			String body = r.getResponseBody();
 * 			return body == null ? &quot;NULL&quot; : body.toString();
 * 		}
 * 	}
 * 
 * 	protected void onPostExecute(String result) {
 * 		Toast.makeText(MainActivity.this, &quot;Result: &quot; + result,
 * 				Toast.LENGTH_LONG).show();
 * 	}
 * 
 * }.execute(url);
 * </pre>
 * 
 * @author MKay
 * 
 */
public class Client {
	private static final Client INSTANCE = new Client();
	private static final int RETRY_COUNT = 5;

	private String mUsername = "";
	private String mPassword = "";

	/**
	 * Singleton-Constructor
	 */
	private Client() {
	}

	/**
	 * Returns the singleton-instance.
	 * 
	 * @return
	 */
	public static Client getInstance() {
		return INSTANCE;
	}

	/**
	 * Set the user name and password which will be used for every request.
	 * 
	 * @param username
	 * @param password
	 */
	public void init(String username, String password) {
		mUsername = username;
		mPassword = password;
	}

	/**
	 * Send a HTTP-GET-request to the specified url.
	 * 
	 * @param ctx
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Response get(Context ctx, URL url) throws ClientProtocolException,
			IOException {
		HttpGet getReq = new HttpGet();
		return request(ctx, getReq, url);
	}

	/**
	 * Send a HTTP-POST-request to the specified url containing the json-object
	 * as HTTP-payload.
	 * 
	 * @param ctx
	 * @param url
	 * @param json
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Response post(Context ctx, URL url, JSONObject json)
			throws ClientProtocolException, IOException {
		HttpPost postReq = new HttpPost();
		postReq.setEntity(new StringEntity(json.toString()));
		return request(ctx, postReq, url);
	}

	/**
	 * Execute the specified request for the URL which will be extended with
	 * user-credentials automatically.
	 * 
	 * @param ctx
	 * @param req
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private Response request(Context ctx, HttpRequestBase req, URL url)
			throws ClientProtocolException, IOException {

		// check connection availability
		ConnectivityManager cm = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo == null || !networkInfo.isConnected()) {
			return new Response(); // no connection available
		}

		req.setURI(URI.create(decorateUrl(url)));
		DefaultHttpClient client = new DefaultHttpClient();
		client.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(
				RETRY_COUNT, false));
		HttpResponse res = client.execute(req);

		HttpEntity entity = res.getEntity();
		String body = entity == null ? null : EntityUtils.toString(entity);

		return new Response(res, body);
	}

	/**
	 * Add additional information to the request-URL. For example
	 * user-credentials.
	 * 
	 * @param url
	 * @return
	 */
	private String decorateUrl(URL url) {
		Uri.Builder ub = Uri.parse(url.toString()).buildUpon();
		ub.appendQueryParameter("u", mUsername);
		ub.appendQueryParameter("p", mPassword);
		return ub.build().toString();
	}

	/**
	 * Holds the results of a request.
	 * 
	 * @author MKay
	 * 
	 */
	public class Response {
		private HttpResponse mResponse;
		private String mResponseBody;
		private JSONObject mJson;
		private boolean mConnectionAvailable;

		public Response() {
			mConnectionAvailable = false;
		}

		public Response(HttpResponse res, String body) {
			mConnectionAvailable = true;
			mResponse = res;
			mResponseBody = body;
			if (body != null && !body.isEmpty()) {
				try {
					mJson = new JSONObject(body);
				} catch (JSONException e) {
					// ignore non-JSON response
				}
			}
		}

		public HttpResponse getResponse() {
			return mResponse;
		}

		public JSONObject getJsonResponse() {
			return mJson;
		}

		public String getResponseBody() {
			return mResponseBody;
		}

		/**
		 * Returns true if a connection was available during the request.
		 * Returns false otherwise (no request was made!).
		 * 
		 * @return
		 */
		public boolean wasConnectionAvailable() {
			return mConnectionAvailable;
		}
	}
}