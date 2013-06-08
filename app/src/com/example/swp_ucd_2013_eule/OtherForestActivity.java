package com.example.swp_ucd_2013_eule;

import java.util.Timer;
import java.util.TimerTask;

import com.example.swp_ucd_2013_eule.view.Forest;
import com.example.swp_ucd_2013_eule.view.Level;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class OtherForestActivity extends Activity {
	private Handler mHandler;
	private Forest mForest;
	private Timer mTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other_forest);
		
		TextView userName =((TextView) findViewById(R.id.txt_other_forest_name));
		userName.setText(getIntent().getStringExtra("userName")+"'s forest");
		
		mForest = (Forest) findViewById(R.id.forest);
		((TextView) findViewById(R.id.txtLevelNumber)).setText("80");

		((Level) findViewById(R.id.level)).setLevel(17);

		// Test-Only Animation
		mHandler = new Handler() {

			public void handleMessage(Message msg) {
				mForest.moveItems();
			}
		};
		mTimer = new Timer();
		mTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				Message msg = mHandler.obtainMessage();
				msg.sendToTarget();
			}
		}, 0, 2000);


	}
}
