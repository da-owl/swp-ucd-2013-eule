package com.example.swp_ucd_2013_eule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swp_ucd_2013_eule.model.Forest;
import com.example.swp_ucd_2013_eule.model.MyForest;
import com.example.swp_ucd_2013_eule.net.ApiClient;

/**
 * InitActivity is the entry point of the APP which prepares the connection to
 * the back-end (e.g. auth token) and then tries to connect (and login) to load
 * the user's forest for the first time. If the login was successfully and the
 * forest was loaded, then the user is redirected to the MainActivity.
 * 
 */
public class InitActivity extends Activity {
	private EditText mAddress;
	private EditText mPort;
	private ProgressBar mBar;
	private TextView mInit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init);

		ApiClient.getInstance().setAuthToken(
				"c515f179da3f768d6802709fbd98aa5c8e60d9a1");

		ApiClient.getInstance().setContext(this);

		mAddress = (EditText) findViewById(R.id.eTxtAddress);
		mPort = (EditText) findViewById(R.id.eTxtPort);
		mBar = (ProgressBar) findViewById(R.id.prgInit);
		mInit = (TextView) findViewById(R.id.txtInit);

		Button but = (Button) findViewById(R.id.butInit);
		but.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mBar.setVisibility(View.VISIBLE);
				mInit.setVisibility(View.VISIBLE);
				ApiClient.getInstance().setServer(
						mAddress.getText().toString() + ":"
								+ mPort.getText().toString());

				/*
				 * Try to load the forest (incl. login) and redirect the user to
				 * MainActivity on success.
				 */
				new AsyncTask<Void, Void, Boolean>() {

					@Override
					protected Boolean doInBackground(Void... params) {
						// TODO first start: register
						Forest forest = MyForest.getInstance().loadForest();
						if (forest == null) {
							return false;
						}
						return true;
					}

					@Override
					protected void onPostExecute(Boolean success) {
						super.onPostExecute(success);

						if (success) {
							Intent intent = new Intent(InitActivity.this,
									MainActivity.class);
							startActivityForResult(intent, 1);
						} else {
							CharSequence text = "Failed to login!";
							Context context = getApplicationContext();
							Toast toast = Toast.makeText(context, text,
									Toast.LENGTH_LONG);
							toast.show();
						}
						mBar.setVisibility(View.INVISIBLE);
						mInit.setVisibility(View.INVISIBLE);
					}
				}.execute(null, null);
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		finish();
	}

}
