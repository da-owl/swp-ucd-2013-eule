package com.example.swp_ucd_2013_eule.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.swp_ucd_2013_eule.view.SocialList.RowElement;

public class SocialRow extends TableRow {

	private RowElement mElement;

	public SocialRow(Context context) {
		super(context);

	}

	public SocialRow(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public void addRowElement(RowElement element) {
		mElement = element;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN && mElement != null) {

			Toast.makeText(this.getContext(), mElement.mUserName + " clicked",
					Toast.LENGTH_SHORT).show();
			return true;

		}
		return false;
	}

}
