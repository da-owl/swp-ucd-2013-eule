package com.example.swp_ucd_2013_eule.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableRow;

import com.example.swp_ucd_2013_eule.view.SocialList.RowElement;

/**
 * SocialRow is a custom implementation of the TableRow. It is used to define a
 * row according to our specification with can be dynamically added or removed
 * to/from the SocialList.
 * 
 * @author Marc
 * 
 */
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

}
