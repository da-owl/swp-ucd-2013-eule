package com.example.swp_ucd_2013_eule;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.swp_ucd_2013_eule.data.ForestItem;
import com.example.swp_ucd_2013_eule.data.UserForestItem;
import com.example.swp_ucd_2013_eule.view.Forest;
import com.example.swp_ucd_2013_eule.view.Forest.UserForestItemListener;
import com.example.swp_ucd_2013_eule.view.Level;
import com.example.swp_ucd_2013_eule.view.SlideUpContainer;

public class ForestFragment extends Fragment {
	private Handler mHandler;
	private Timer mTimer;
	private Forest mForest;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_forest, container,
				false);

		final SlideUpContainer slideUpContainer = (SlideUpContainer) rootView
				.findViewById(R.id.forestSlideUp);
		Button btnClose = (Button) rootView.findViewById(R.id.btnSlideUpClose);

		mForest = (Forest) rootView.findViewById(R.id.forest);
		mForest.setSlideUpContainer(slideUpContainer);
		((TextView) rootView.findViewById(R.id.txtLevelNumber)).setText("80");

		mForest.setForestItemListener(new UserForestItemListener() {
			@Override
			public void onForestItemClicked(UserForestItem item) {
				ForestItem fItem = item.getForestItem();

				// set label
				TextView name = (TextView) slideUpContainer
						.findViewById(R.id.txt_label);
				name.setText("Detailansicht " + fItem.getName());
				// set description
				TextView des = (TextView) slideUpContainer
						.findViewById(R.id.txt_details);
				des.setText(fItem.getDescription());
				// set Picture
				ImageView pic = (ImageView) slideUpContainer
						.findViewById(R.id.imgItem);
				pic.setImageBitmap(fItem.getImage());
				// set amount
				TextView amount = (TextView) slideUpContainer
						.findViewById(R.id.txt_amount);
				amount.setText("Anzahl: " + fItem.getAmount());

				slideUpContainer.slideOpen();
			}
		});
		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				slideUpContainer.slideClose();
			}
		});

		((Level) rootView.findViewById(R.id.level)).setLevel(17);

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

		return rootView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mTimer.cancel();
	}

}
