package com.example.swp_ucd_2013_eule.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.View;

public class Chart extends View {

	private static final int ROUNDED_CORNER = 20;
	private static final int MIN_LINES = 3;
	private static final int MAX_LINES = 8;
	private static final int[] DISTANCES = { 1, 2, 5 };

	private int mWidth, mHeight;

	private float[] dropsValues;
	private float[] consValues;

	private Path mPathDrops;
	private Paint dropsPaint;
	private Path mPathConsumption;
	private Paint consumptionPaint;

	private Paint mDropsLinePaint;
	private Paint mConsLinePaint;

	public Chart(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGraph();
	}

	private void initGraph() {
		mDropsLinePaint = new Paint();
		mDropsLinePaint.setStyle(Style.STROKE);
		mDropsLinePaint.setColor(0xff2A83C0);
		mDropsLinePaint.setTextAlign(Align.LEFT);
		mDropsLinePaint.setTextSize(16);
		mDropsLinePaint.setStrokeWidth(1);
		mDropsLinePaint.setAntiAlias(false);
		mConsLinePaint = new Paint();
		mConsLinePaint.setStyle(Style.STROKE);
		mConsLinePaint.setColor(Color.RED);
		mConsLinePaint.setTextAlign(Align.RIGHT);
		mConsLinePaint.setTextSize(16);
		mConsLinePaint.setStrokeWidth(1);
		mConsLinePaint.setAntiAlias(false);

		dropsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		dropsPaint.setColor(0xff2A83C0);
		dropsPaint.setStyle(Paint.Style.STROKE);
		dropsPaint.setStrokeWidth(6);
		dropsPaint.setStrokeJoin(Join.ROUND);
		dropsPaint.setStrokeCap(Cap.ROUND);
		dropsPaint.setPathEffect(new CornerPathEffect(ROUNDED_CORNER));

		consumptionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		consumptionPaint.setColor(Color.RED);
		consumptionPaint.setStyle(Paint.Style.STROKE);
		consumptionPaint.setStrokeWidth(6);
		consumptionPaint.setStrokeJoin(Join.ROUND);
		consumptionPaint.setStrokeCap(Cap.ROUND);
		consumptionPaint.setPathEffect(new CornerPathEffect(ROUNDED_CORNER));
	}

	public void setDrops(float[] drops) {
		this.dropsValues = drops;
		mPathDrops = new Path();
		mPathDrops.moveTo(getXPos(0, drops.length - 1),
				getYPos(drops[0], getMax(drops)));
		for (int i = 0; i < drops.length; i++) {
			mPathDrops.lineTo(getXPos(i, drops.length),
					getYPos(drops[i], getMax(drops)));
			System.out.println("drops");
		}
		invalidate();
	}

	public void setConsumption(float[] cons) {
		this.consValues = cons;
		mPathConsumption = new Path();
		mPathConsumption.moveTo(getXPos(0, cons.length - 1),
				getYPos(cons[0], getMax(cons)));
		for (int i = 0; i < cons.length; i++) {
			mPathConsumption.lineTo(getXPos(i, cons.length),
					getYPos(cons[i], getMax(cons)));
			System.out.println("consumption");
		}
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (dropsValues.length != 0) {
			drawBackground(canvas, getMax(consValues), 1);
			drawBackground(canvas, getMax(dropsValues), 0);
		}
		canvas.drawPath(mPathDrops, dropsPaint);
		canvas.drawPath(mPathConsumption, consumptionPaint);
	}

	private void drawBackground(Canvas canvas, float maxValue, int diff) {
		int range = getLineDistance(maxValue);
		if (diff == 0) {
			for (int y = 0; y < maxValue; y += range) {
				final int yPos = (int) getYPos(y, maxValue);
				canvas.drawLine(0, yPos, getWidth() - 30, yPos, mDropsLinePaint);
				canvas.drawText(String.valueOf(y), getPaddingLeft(), yPos - 2,
						mDropsLinePaint);
			}
		} else {
			for (int y = 0; y < maxValue; y += range) {
				final int yPos = (int) getYPos(y, maxValue);
				canvas.drawLine(30, yPos, getWidth(), yPos, mConsLinePaint);
				canvas.drawText(String.valueOf(y), mWidth, yPos - 2,
						mConsLinePaint);
			}
		}
	}

	private int getLineDistance(float maxValue) {
		long distance;
		int distanceIndex = 0;
		long distanceMultiplier = 1;
		int numberOfLines = MIN_LINES;

		do {
			distance = DISTANCES[distanceIndex] * distanceMultiplier;
			numberOfLines = (int) FloatMath.ceil(maxValue / distance);

			distanceIndex++;
			if (distanceIndex == DISTANCES.length) {
				distanceIndex = 0;
				distanceMultiplier *= 10;
			}
		} while (numberOfLines < MIN_LINES || numberOfLines > MAX_LINES);
		return (int) distance;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mWidth = measureWidth(widthMeasureSpec);
		mHeight = measureHeight(heightMeasureSpec);
		setMeasuredDimension(mWidth, mHeight);
	}

	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			result = specSize;
		}
		return result;
	}

	private int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			result = specSize;
		}
		return result;
	}

	public float getMax(float[] v) {
		float largest = v[0];
		for (int i = 0; i < v.length; i++)
			if (v[i] > largest)
				largest = v[i];
		return largest;
	}

	public float getMin(float[] v) {
		float smallest = v[0];
		for (int i = 0; i < v.length; i++)
			if (v[i] < smallest)
				smallest = v[i];
		return smallest;
	}

	private float getYPos(float value, float maxValue) {
		float height = mHeight;
		value = (value / maxValue) * height;
		value = height - value;
		return value;
	}

	private float getXPos(float value, float maxValue) {
		float width = mWidth;
		value = (value / (maxValue - 1)) * width;
		return value;
	}
}