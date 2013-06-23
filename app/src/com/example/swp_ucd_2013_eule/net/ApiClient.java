package com.example.swp_ucd_2013_eule.net;

import java.io.IOException;
import java.net.URI;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;

import android.content.Context;

import com.example.swp_ucd_2013_eule.net.HttpJsonClient.Response;

/**
 * Example for making a POST-request from an Activity (in this case
 * MainActivity):
 * 
 * <pre>
 * // API-endpoint to send the request to
 * String apiEndpoint = &quot;/?test&quot;;
 * 
 * new AsyncTask&lt;String, Void, String&gt;() {
 * 	&#064;Override
 * 	protected String doInBackground(String... apiEndpoint) {
 * 		Response r = null;
 * 		try {
 * 			ApiClient c = ApiClient.getInstance();
 * 			// required only once!
 * 			c.setServer(&quot;10.0.2.2:8080&quot;);
 * 			c.setAuthToken(&quot;4208b520528611010299d5135d46c7c3c6979a5b&quot;);
 * 			// sending a JSON-object to the specified API-endpoint using
 * 			// a POST-request
 * 			r = c.post(MainActivity.this, apiEndpoint[0], new JSONObject(
 * 					&quot;{forest:123, action:\&quot;updateLevel\&quot;, newLevel:2}&quot;));
 * 		} catch (Exception e) {
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
 * }.execute(apiEndpoint);
 * </pre>
 * 
 * @author MKay
 * 
 */
public class ApiClient {
	private static final ApiClient INSTANCE = new ApiClient();

	private String mServer;
	private String mAuthToken;

	/**
	 * Singleton-constructor
	 */
	private ApiClient() {
	}

	/**
	 * Returns the singleton-instance.
	 * 
	 * @return
	 */
	public static ApiClient getInstance() {
		return INSTANCE;
	}

	/**
	 * Set the API-server/port.
	 * 
	 * @param server
	 */
	public void setServer(String server) {
		mServer = server;
	}

	/**
	 * Set the token which will be used on every request for account-
	 * authentication. If token is null (or empty) then further requests will
	 * not contain any authentication-information.
	 * 
	 * @param token
	 */
	public void setAuthToken(String token) {
		mAuthToken = token;
	}

	/**
	 * Send a HTTP-GET-request to the specified API-endpoint.
	 * 
	 * @param ctx
	 * @param apiEndpoint
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Response get(Context ctx, String apiEndpoint)
			throws ClientProtocolException, IOException {
		URI uri = getEndpointUri(apiEndpoint);
		return createHttpJsonClient().get(ctx, uri);
	}

	/**
	 * Send a HTTP-POST-request to the specified API-endpoint containing the
	 * json-object as HTTP-payload.
	 * 
	 * @param ctx
	 * @param apiEndpoint
	 * @param json
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Response post(Context ctx, String apiEndpoint, JSONObject json)
			throws ClientProtocolException, IOException {
		URI uri = getEndpointUri(apiEndpoint);
		return createHttpJsonClient().put(ctx, uri, json);
	}

	private HttpJsonClient createHttpJsonClient() {
		HttpJsonClient c = new HttpJsonClient();
		c.setHeaders(generateHeaders());
		return c;
	}

	private URI getEndpointUri(String endpoint) {
		return URI.create("http://" + mServer + endpoint);
	}

	private Header[] generateHeaders() {
		if (mAuthToken == null || mAuthToken.isEmpty()) {
			return new Header[] {};
		}
		return new Header[] { new BasicHeader("Authorization", "Token "	+ mAuthToken), 
							new BasicHeader("Content-Type", "application/json")};
	}
}
