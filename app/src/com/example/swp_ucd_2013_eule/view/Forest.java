package com.example.swp_ucd_2013_eule.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class Forest extends View {
	private Paint mMeadowPaint;
	private RectF mMeadowBounds;

	public Forest(Context context) {
		super(context);
		initForest();
	}

	public Forest(Context context, AttributeSet attrs) {
		super(context, attrs);
		initForest();
	}

	private void initForest() {
		mMeadowPaint = new Paint();
		mMeadowPaint.setAntiAlias(true);
		mMeadowPaint.setColor(0xFF228B22);
		mMeadowPaint.setStyle(Style.FILL);

	}

	private void updateForestSize() {

		int level = 1;
		int multiplicator = 50;
		int forestWidth = multiplicator * level - 30;
		int forestHeight = multiplicator * level;
		// int fragmentWidth =
		// mContext.getResources().getDisplayMetrics().widthPixels;
		// int fragmentHeight =
		// mContext.getResources().getDisplayMetrics().heightPixels;
		int xCenter = 10;
		int yCenter = 0;
		// mMeadowBounds = new RectF(xCenter, yCenter, forestWidth,
		// forestHeight);
		int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
		mMeadowBounds = new RectF(0, 0, size, size - 50);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawOval(mMeadowBounds, mMeadowPaint);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
				MeasureSpec.getSize(heightMeasureSpec));
		updateForestSize();
	}

}
