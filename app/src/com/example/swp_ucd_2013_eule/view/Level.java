package com.example.swp_ucd_2013_eule.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.swp_ucd_2013_eule.R;

public class Level extends View {
	private Bitmap mLevelBitmap = BitmapFactory.decodeResource(getResources(),
			R.drawable.ico_level);

	private int mSize;
	private Rect mSrcRect = new Rect(0, 0, 48, 48);
	private Rect mDstRect = new Rect();
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
	}

	private void updateDimensions() {
		mTextPos = new Point(
				mSize / 2,
				(int) (mSize / 2 - ((mTextPaint.descent() + mTextPaint.ascent()) / 2)));
		mDstRect.set(0, 0, mSize, mSize);
	}

	public void setLevel(int level) {
		mLevel = String.valueOf(level);
		invalidate();
	}

	protected int dpToPx(int val) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				val, getResources().getDisplayMetrics());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(mLevelBitmap, mSrcRect, mDstRect, null);
		canvas.drawText(mLevel, mTextPos.x, mTextPos.y, mTextPaint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mSize = dpToPx(32);
		updateDimensions();
		setMeasuredDimension(mSize, mSize);
	}
}
