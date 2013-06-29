package com.example.swp_ucd_2013_eule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.swp_ucd_2013_eule.car_data.CarDataLogic;
import com.example.swp_ucd_2013_eule.model.Forest;
import com.example.swp_ucd_2013_eule.model.MyForest;
import com.example.swp_ucd_2013_eule.net.ApiClient;

public class InitActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init);

		ApiClient.getInstance().setServer("10.0.2.2:8080");
		ApiClient.getInstance().setAuthToken(
				"c515f179da3f768d6802709fbd98aa5c8e60d9a1");

		ApiClient.getInstance().setContext(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

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
					startActivity(intent);
				} else {
					CharSequence text = "Failed to login!";
					Context context = getApplicationContext();
					Toast toast = Toast.makeText(context, text,
							Toast.LENGTH_LONG);
					toast.show();
				}

			}
		}.execute(null, null);
	}
}
