package com.example.swp_ucd_2013_eule.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.View;

public class GearIndicator extends View {
	private static final int GEAR_ROUNDED_CORNER = 20;

	private int mSize;
	private Paint mBorderPaint;
	private Paint mBackgroundPaint;
	private Paint mFillPaint;
	private Paint mGlossyPaint;
	private Paint mGearPaint;
	private Paint mGearTextPaint;

	private RectF mInnerBorderRectF;
	private RectF mOuterBorderRectF;
	private RectF mFillRectF;
	private RectF mBackgroundRectF;
	private RectF mGlossyRectF;
	private Path mGearPath;
	private Point mGearCenter;

	private int mGear = 6; // TODO set dynamically

	public GearIndicator(Context context) {
		super(context);
		initGearIndicator();
	}

	public GearIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGearIndicator();
	}

	private void initGearIndicator() {
		mBackgroundPaint = new Paint();
		mBackgroundPaint.setAntiAlias(true);
		// mBackgroundPaint.setColor(0xFFd5d5d5);
		mBackgroundPaint.setStyle(Style.FILL);

		mBorderPaint = new Paint();
		mBorderPaint.setAntiAlias(true);
		mBorderPaint.setColor(0xFFffffff);
		mBorderPaint.setStyle(Style.STROKE);
		mBorderPaint.setStrokeWidth(2);

		mFillPaint = new Paint();
		mFillPaint.setAntiAlias(true);
		mFillPaint.setColor(0xFF0f93e7);
		mFillPaint.setStyle(Style.STROKE);
		mFillPaint.setStrokeWidth(16);

		mGlossyPaint = new Paint();
		mGlossyPaint.setAntiAlias(true);
		// mGlossyRectF.setColor(0xFFd5d5d5);
		mGlossyPaint.setStyle(Style.FILL);

		mGearPaint = new Paint();
		mGearPaint.setAntiAlias(true);
		mGearPaint.setColor(0x22000000);
		mGearPaint.setStyle(Style.STROKE);
		mGearPaint.setStrokeWidth(8);
		mGearPaint.setStrokeJoin(Join.ROUND);
		mGearPaint.setStrokeCap(Cap.ROUND);
		mGearPaint.setPathEffect(new CornerPathEffect(GEAR_ROUNDED_CORNER));

		mGearTextPaint = new Paint();
		mGearTextPaint.setAntiAlias(true);
		mGearTextPaint.setColor(0xFF333333);
		mGearTextPaint.setTextAlign(Align.CENTER);
		mGearTextPaint.setTextSize(200);
	}

	private void updateDimensions() {
		float hBorderStrokeWidth = mBorderPaint.getStrokeWidth() / 2;
		float curOffset = hBorderStrokeWidth;
		mOuterBorderRectF = new RectF(curOffset, curOffset, mSize - curOffset,
				mSize - curOffset);

		float hFillStrokeWidth = mFillPaint.getStrokeWidth() / 2;
		curOffset += hBorderStrokeWidth + hFillStrokeWidth;
		mFillRectF = new RectF(curOffset, curOffset, mSize - curOffset, mSize
				- curOffset);

		curOffset += hFillStrokeWidth + hBorderStrokeWidth;
		mInnerBorderRectF = new RectF(curOffset, curOffset, mSize - curOffset,
				mSize - curOffset);

		curOffset += hBorderStrokeWidth;
		mBackgroundRectF = new RectF(curOffset, curOffset, mSize - curOffset,
				mSize - curOffset);

		float glossyXOffset = curOffset + 10;
		float glossyTop = curOffset + 10;
		float glossyHeight = ((mSize - 2 * curOffset) / 5 * 2);
		mGlossyRectF = new RectF(curOffset + glossyXOffset, glossyTop, mSize
				- curOffset - glossyXOffset, glossyTop + glossyHeight);

		float bgSize = mSize - 2 * curOffset;
		float gearOff = curOffset + (bgSize / 9 * 1.75f);
		float gearMid = curOffset + (bgSize / 9 * 4.75f);
		float gearBot = curOffset + (bgSize / 9 * 7.5f);
		mGearPath = new Path();
		mGearPath.moveTo(gearOff, gearOff);
		mGearPath.lineTo(gearOff, gearMid);
		mGearPath.lineTo(mSize - gearOff, gearMid);
		mGearPath.lineTo(mSize - gearOff, gearOff);

		float foo = (mSize - gearOff * 2) / 3;
		mGearPath.moveTo(gearOff + foo, gearOff);
		mGearPath.lineTo(gearOff + foo, gearBot);
		mGearPath.moveTo(gearOff + foo * 2, gearOff);
		mGearPath.lineTo(gearOff + foo * 2, gearBot + GEAR_ROUNDED_CORNER);

		mGearCenter = new Point(mSize / 2,
				(int) (mSize / 2 - ((mGearTextPaint.descent() + mGearTextPaint
						.ascent()) / 2)));

		updateColors(glossyHeight, glossyTop);
	}

	private void updateColors(float glossyHeight, float glossyTop) {
		Shader bgShader = new RadialGradient(mSize / 2, mSize, mSize / 3 * 2,
				0xFF8e8e8e, 0xFFd6d6d6, TileMode.CLAMP);
		mBackgroundPaint.setShader(bgShader);

		Shader glossyShader = new RadialGradient(mSize / 2, glossyTop
				+ glossyHeight, 2 * glossyHeight, 0x22ffffff, 0xCCffffff,
				TileMode.CLAMP);
		mGlossyPaint.setShader(glossyShader);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawOval(mBackgroundRectF, mBackgroundPaint);
		canvas.drawOval(mInnerBorderRectF, mBorderPaint);
		canvas.drawOval(mOuterBorderRectF, mBorderPaint);
		canvas.drawArc(mFillRectF, 270, 225, false, mFillPaint);
		canvas.drawPath(mGearPath, mGearPaint);
		canvas.drawOval(mGlossyRectF, mGlossyPaint);
		canvas.drawText("6", mGearCenter.x, mGearCenter.y, mGearTextPaint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mSize = Math.min(MeasureSpec.getSize(widthMeasureSpec),
				MeasureSpec.getSize(heightMeasureSpec));

		updateDimensions();
		setMeasuredDimension(mSize, mSize);
	}

}
