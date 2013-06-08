package com.example.swp_ucd_2013_eule.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.swp_ucd_2013_eule.OtherForestActivity;
import com.example.swp_ucd_2013_eule.view.SocialList.RowElement;

public class SocialRow extends TableRow {
	private boolean mDown;
	private RowElement mElement;
	private Context mContext;

	public SocialRow(Context context) {
		super(context);
		mContext =context;

	}

	public SocialRow(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext =context;

	}

	public void addRowElement(RowElement element) {
		mElement = element;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mElement != null) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				mDown = true;
				setBackgroundColor(Color.parseColor("#4178dc"));
				return true;

			} else if (event.getAction() == MotionEvent.ACTION_UP && mDown) {
				setBackgroundColor(Color.TRANSPARENT);
				mDown = false;
			    //show other forest
				Intent intent = new Intent(mContext, OtherForestActivity.class);
				intent.putExtra("userName", mElement.mUserName); //Optional parameters
			    mContext.startActivity(intent);
				
				return true;
			} else {
				mDown = false;

			}
		}
		setBackgroundColor(Color.TRANSPARENT);
		return false;
	}

}
