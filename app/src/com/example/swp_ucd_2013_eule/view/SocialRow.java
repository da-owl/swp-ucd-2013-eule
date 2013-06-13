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
		mContext = context;

	}

	public SocialRow(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

	}

	public void addRowElement(RowElement element) {
		mElement = element;
	}
}
