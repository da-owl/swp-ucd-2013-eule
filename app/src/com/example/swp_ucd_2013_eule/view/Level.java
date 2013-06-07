package com.example.swp_ucd_2013_eule.view;

import com.example.swp_ucd_2013_eule.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.View;

public class Level extends View {
	private Bitmap mLevelBitmap = BitmapFactory.decodeResource(getResources(),
			R.drawable.ico_level);

	private Paint mTextPaint;
	private Point mTextPos;

	private String mLevel = "0";

	public Level(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public Level(Context context) {
		super(context);
		init();
	}

	public void init() {
		mTextPaint = new Paint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setColor(0xFFffffff);
		mTextPaint.setTextAlign(Align.CENTER);
		mTextPaint.setTextSize(20);

		mTextPos = new Point(24,
				(int) (24 - ((mTextPaint.descent() + mTextPaint.ascent()) / 2)));
	}

	public void setLevel(int level) {
		mLevel = String.valueOf(level);
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(mLevelBitmap, 0, 0, null);
		canvas.drawText(mLevel, mTextPos.x, mTextPos.y, mTextPaint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(48, 48);
	}
}
