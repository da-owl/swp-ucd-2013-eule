package com.example.swp_ucd_2013_eule.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.swp_ucd_2013_eule.view.SocialList.RowElement;

public class SocialRow extends TableRow {
	private boolean mDown;
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
		if (mElement != null) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				mDown = true;
				setBackgroundColor(Color.parseColor("#4178dc"));
				return true;

			} else if (event.getAction() == MotionEvent.ACTION_UP && mDown) {
				setBackgroundColor(Color.TRANSPARENT);
				mDown = false;
				Toast.makeText(this.getContext(),
						mElement.mUserName + " clicked", Toast.LENGTH_SHORT)
						.show();
				return true;
			} else {
				mDown = false;

			}
		}
		setBackgroundColor(Color.TRANSPARENT);
		return false;
	}

}
