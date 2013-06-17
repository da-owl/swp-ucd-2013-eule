package com.example.swp_ucd_2013_eule.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
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
import android.graphics.SweepGradient;
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
	private Paint mRPMPaint;

	private RectF mInnerBorderRectF;
	private RectF mOuterBorderRectF;
	private RectF mFillRectF;
	private RectF mBackgroundRectF;
	private RectF mGlossyRectF;
	private Path mGearPath;
	private Point mGearCenter;
	private float mRPM1Angle;
	private float mRPM2Angle;
	private RectF mRPMRectF;

	private List<Point> mMarks = new ArrayList<Point>();
	private List<Point> mMarks2 = new ArrayList<Point>();

	private int mGear = 0;
	private float mRPM = 0;
	private float mReferenceValue1 = 1600;
	private float mReferenceValue2 = 2000;
	private float mRPMMax = 6700;

	// TODO when setting mRPMMax dynamically adjust these values:
	private int mRPMMaxScaled = Math.round(mRPMMax / 1000);
	private double mRPMMaxLog = Math.log10(mRPMMaxScaled + 1);

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
		// mFillPaint.setColor(0xFF0f93e7);
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
		mGearTextPaint.setTextSize(180);

		mRPMPaint = new Paint();
		mRPMPaint.setAntiAlias(true);
		mRPMPaint.setColor(0xFF96e31d);
		mRPMPaint.setStyle(Style.STROKE);
		mRPMPaint.setStrokeWidth(16);
	}

	private void updateDimensions() {

		float hRPMStrokeWidth = mRPMPaint.getStrokeWidth() / 2;
		float curOffset = hRPMStrokeWidth;
		mRPM1Angle = (float) getAngleByRPM(mReferenceValue1) + 90;
		mRPM2Angle = (float) getAngleByRPM(mReferenceValue2) + 90;
		mRPMRectF = new RectF(curOffset, curOffset, mSize - curOffset, mSize
				- curOffset);

		float hBorderStrokeWidth = mBorderPaint.getStrokeWidth() / 2;
		curOffset += hRPMStrokeWidth + hBorderStrokeWidth;
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

		mMarks.clear();
		mMarks2.clear();
		float half = (float) mSize / 2;
		float radius = half - (mRPMPaint.getStrokeWidth() / 2);
		float radius2 = half - mRPMPaint.getStrokeWidth();
		for (float i = 1; i < mRPMMaxScaled; i++) {
			// linear: float angle = i / maxRPM * 360;
			double angle = getAngleByRPM(i * 1000);
			double xAngleSin = Math.sin(Math.toRadians(0 + angle));
			double yAngleSin = Math.sin(Math.toRadians(270 + angle));

			int x = (int) (half - xAngleSin * radius);
			int y = (int) (half - yAngleSin * radius);
			mMarks.add(new Point(x, y));

			int x2 = (int) (half - xAngleSin * radius2);
			int y2 = (int) (half - yAngleSin * radius2);
			mMarks2.add(new Point(x2, y2));
		}

		updateColors(glossyHeight, glossyTop);
	}

	private double getAngleByRPM(float rpm) {
		rpm = rpm / 1000;
		double angle = (Math.log10(rpm + 1) / mRPMMaxLog) * 360;
		return angle;
	}

	private void updateColors(float glossyHeight, float glossyTop) {
		Shader bgShader = new RadialGradient(mSize / 2, mSize, mSize / 3 * 2,
				0xFF8e8e8e, 0xFFd6d6d6, TileMode.CLAMP);
		mBackgroundPaint.setShader(bgShader);

		Shader glossyShader = new RadialGradient(mSize / 2, glossyTop
				+ glossyHeight, 2 * glossyHeight, 0x22ffffff, 0xCCffffff,
				TileMode.CLAMP);
		mGlossyPaint.setShader(glossyShader);

		Shader rpmShader = new SweepGradient(mSize / 2, mSize / 2, 0xFF0f93e7,
				0xFF0a51b3);
		Matrix rpmMatrix = new Matrix();
		rpmMatrix.postRotate(89, mSize / 2, mSize / 2);
		rpmShader.setLocalMatrix(rpmMatrix);
		mFillPaint.setShader(rpmShader);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// TODO repaint Reference-Bar here? (recalc sizes)
		canvas.drawOval(mInnerBorderRectF, mBorderPaint);
		canvas.drawOval(mOuterBorderRectF, mBorderPaint);
		canvas.drawArc(mFillRectF, 90, (float) getAngleByRPM(mRPM), false,
				mFillPaint);

		canvas.drawArc(mRPMRectF, mRPM1Angle, mRPM2Angle - mRPM1Angle, false,
				mRPMPaint);

		for (int i = 0; i < mMarks.size(); i++) {
			Point p = mMarks.get(i);
			Point q = mMarks2.get(i);
			canvas.drawLine(q.x, q.y, p.x, p.y, mBorderPaint);
		}

		canvas.drawOval(mBackgroundRectF, mBackgroundPaint);
		canvas.drawPath(mGearPath, mGearPaint);
		canvas.drawOval(mGlossyRectF, mGlossyPaint);

		canvas.drawText(String.valueOf(mGear), mGearCenter.x, mGearCenter.y,
				mGearTextPaint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mSize = Math.min(MeasureSpec.getSize(widthMeasureSpec),
				MeasureSpec.getSize(heightMeasureSpec));

		updateDimensions();
		setMeasuredDimension(mSize, mSize);
	}

	public void setGear(int gear) {
		mGear = gear;
		invalidate();
	}

	public void setRPM(float rpm) {
		mRPM = rpm;
		invalidate();
	}
}
