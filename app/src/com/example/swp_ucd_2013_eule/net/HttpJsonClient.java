package com.example.swp_ucd_2013_eule.net;

import java.io.IOException;
import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
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

/**
 * This class can be used to make HTTP-requests containing JSON-objects as
 * payload.
 * 
 * @author MKay
 * 
 */
public class HttpJsonClient {
	private static final int RETRY_COUNT = 5;

	private Header[] mHeaders;

	/**
	 * Set the headers which will be included in the request.
	 * 
	 * @param headers
	 */
	public void setHeaders(Header[] headers) {
		mHeaders = headers;
	}

	/**
	 * Send a HTTP-GET-request to the specified uri.
	 * 
	 * @param ctx
	 * @param uri
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Response get(Context ctx, URI uri) throws ClientProtocolException,
			IOException {
		HttpGet getReq = new HttpGet();
		return request(ctx, getReq, uri);
	}

	/**
	 * Send a HTTP-PUT-request to the specified uri containing the json-object
	 * as HTTP-payload.
	 * 
	 * @param ctx
	 * @param uri
	 * @param json
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Response put(Context ctx, URI uri, JSONObject json)
			throws ClientProtocolException, IOException {
		HttpPut putReq = new HttpPut();
		putReq.setEntity(new StringEntity(json.toString()));
		return request(ctx, putReq, uri);
	}

	/**
	 * Send a HTTP-PUT-request to the specified uri containing the json-object
	 * as HTTP-payload.
	 * 
	 * @param ctx
	 * @param uri
	 * @param json
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Response post(Context ctx, URI uri, JSONObject json)
			throws ClientProtocolException, IOException {
		// HttpPut putReq = new HttpPut();

		HttpPost postReq = new HttpPost();
		postReq.setEntity(new StringEntity(json.toString()));
		return request(ctx, postReq, uri);
	}

	/**
	 * Send a request to the specified uri.
	 * 
	 * @param ctx
	 * @param req
	 * @param uri
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private Response request(Context ctx, HttpRequestBase req, URI uri)
			throws ClientProtocolException, IOException {

		// check connection availability
		ConnectivityManager cm = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo == null || !networkInfo.isConnected()) {
			System.out.println("APIClient - No connection available!");
			return new Response(); // no connection available
		}

		req.setURI(uri);
		if (mHeaders != null) {
			req.setHeaders(mHeaders);
		}
		System.out.println("APIClient - Request " + req.getMethod() + " "
				+ req.getURI());

		DefaultHttpClient client = new DefaultHttpClient();
		client.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(
				RETRY_COUNT, false));
		HttpResponse res = null;
		// try {
		res = client.execute(req);
		// } catch (Exception e) {
		// System.out.println("APIClient - Execute failed: " + e);
		// }

		HttpEntity entity = res.getEntity();
		String body = entity == null ? null : EntityUtils.toString(entity);
		System.out.println("APIClient - Response from " + req.getMethod() + " "
				+ req.getURI() + ": " + body);

		return new Response(res, body);
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
