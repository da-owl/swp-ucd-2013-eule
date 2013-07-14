package com.example.swp_ucd_2013_eule.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.swp_ucd_2013_eule.R;

/**
 * The BenchmarkBar shows a (horizontal or vertical) ProgressBar with an
 * optional Reference value.
 * 
 * @author MKay
 * 
 */
public class BenchmarkBar extends View {
	private boolean mVertical = true;
	private int mWeight = 40;
	private int mReferenceWeight = 10;
	private int mWidth, mHeight;

	private Paint mBorderPaint;
	private Paint mGoodPaint;
	private Paint mBadPaint;
	private Paint mReferencePaint;

	private int mGoodGradient1 = 0xFF0f93e7;
	private int mGoodGradient2 = 0xFF0a51b3;
	private int mBadGradient1 = 0xFF0a51b3;
	private int mBadGradient2 = 0xFFca199e;

	private float mMax = 100;
	private float mReferenceValue = 65;
	private float mValue = 85;

	private float mBorderTop, mBorderLeft, mBorderRight, mBorderBottom;
	private float mGoodBarTop, mGoodBarLeft, mGoodBarRight, mGoodBarBottom;
	private float mBadBarTop, mBadBarLeft, mBadBarRight, mBadBarBottom;
	private float mReferenceX1, mReferenceX2, mReferenceY1, mReferenceY2;
	private Path mReferencePath;

	public BenchmarkBar(Context context) {
		super(context);
		initBenchmarkBar();
	}

	public BenchmarkBar(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.BenchmarkBar);

		mVertical = !a.getBoolean(R.styleable.BenchmarkBar_horizontal, false);
		mWeight = a.getInt(R.styleable.BenchmarkBar_weight, 40);
		mWeight = dpToPx(mWeight);

		a.recycle();

		initBenchmarkBar();
	}

	/**
	 * Converts dp to px.
	 * 
	 * @param value
	 * @return the value in px
	 */
	private int dpToPx(int value) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				value, getResources().getDisplayMetrics());
	}

	public void setReferenceValue(float val) {
		mReferenceValue = val;
		updateDimensions();
		invalidate();
	}

	public void setValue(float val) {
		mValue = val;
		updateDimensions();
		invalidate();
	}

	public void setMax(float max) {
		mMax = max;
		updateDimensions();
		invalidate();
	}

	/**
	 * Initializes the BenchmarkBar. That is creating paints.
	 */
	private void initBenchmarkBar() {
		mBorderPaint = new Paint();
		mBorderPaint.setAntiAlias(true);
		mBorderPaint.setColor(0xFFffffff);
		mBorderPaint.setStyle(Style.STROKE);
		mBorderPaint.setStrokeWidth(dpToPx(2));

		mGoodPaint = new Paint();
		mGoodPaint.setAntiAlias(true);
		// mGoodPaint.setColor(0xFF1689bd);

		mBadPaint = new Paint();
		mBadPaint.setAntiAlias(true);
		// mBadPaint.setColor(0xFFbd1616);

		mReferencePaint = new Paint();
		mReferencePaint.setAntiAlias(true);
		mReferencePaint.setStyle(Style.FILL);
		mReferencePaint.setColor(0xFFffffff);

		updateColors();
	}

	/**
	 * Update the color-positions of the gradients.
	 */
	private void updateColors() {
		float gsx1 = 0, gsy1 = 0, gsx2 = 0, gsy2 = 0;
		float bsx1 = 0, bsy1 = 0, bsx2 = 0, bsy2 = 0;

		if (mVertical) {
			gsy1 = mGoodBarBottom;
			gsy2 = mGoodBarTop;

			bsy1 = mBadBarBottom;
			bsy2 = mBadBarTop;
		} else {
			gsx1 = mGoodBarLeft;
			gsx2 = mGoodBarRight;

			bsx1 = mBadBarLeft;
			bsx2 = mBadBarRight;
		}

		Shader goodShader = new LinearGradient(gsx1, gsy1, gsx2, gsy2,
				mGoodGradient1, mGoodGradient2, TileMode.CLAMP);
		mGoodPaint.setShader(goodShader);

		Shader badShader = new LinearGradient(bsx1, bsy1, bsx2, bsy2,
				mBadGradient1, mBadGradient2, TileMode.CLAMP);
		mBadPaint.setShader(badShader);
	}

	/**
	 * Recalculate the dimensions and positions for the boxes and borders to
	 * draw.
	 */
	private void updateDimensions() {
		Rect barDimensions = getBarDimensions();
		float strokeWidth = mBorderPaint.getStrokeWidth(), hStrokeWidth = strokeWidth / 2;

		if (mVertical) {
			float referenceHeight = (barDimensions.height() - (2 * strokeWidth));
			if (mReferenceValue >= 0) {
				referenceHeight = mReferenceValue / mMax
						* (barDimensions.height() - (2 * strokeWidth));
			}
			float currentHeight = mValue / mMax
					* (barDimensions.height() - (2 * strokeWidth));

			float goodHeight = currentHeight;
			if (goodHeight > referenceHeight) {
				goodHeight = referenceHeight;
			}

			// Border
			mBorderLeft = barDimensions.left + hStrokeWidth;
			mBorderTop = barDimensions.top + hStrokeWidth;
			mBorderRight = barDimensions.right - hStrokeWidth;
			mBorderBottom = barDimensions.bottom - hStrokeWidth;

			// Reference Indicator
			mReferenceY1 = mReferenceY2 = barDimensions.bottom
					- referenceHeight - hStrokeWidth;
			mReferenceX1 = mBorderRight + hStrokeWidth;
			mReferenceX2 = mReferenceX1 + mReferenceWeight;

			mReferencePath = new Path();
			mReferencePath.moveTo(mReferenceX1, mReferenceY1);
			mReferencePath.lineTo(mReferenceX2, mReferenceY1 - mReferenceWeight
					/ 2);
			mReferencePath.lineTo(mReferenceX2, mReferenceY1 + mReferenceWeight
					/ 2);
			mReferencePath.close();

			// Good-Rect
			mGoodBarLeft = barDimensions.left + strokeWidth;
			mGoodBarBottom = barDimensions.bottom - strokeWidth;
			mGoodBarTop = mGoodBarBottom - goodHeight;
			mGoodBarRight = barDimensions.right - strokeWidth;

			// Bad-Rect
			if (currentHeight > referenceHeight) {
				mBadBarTop = mGoodBarBottom - currentHeight;
				mBadBarLeft = mGoodBarLeft;
				mBadBarRight = mGoodBarRight;
				mBadBarBottom = mGoodBarTop;
			} else {
				mBadBarTop = mBadBarLeft = mBadBarRight = mBadBarBottom = 0;
			}
		} else {
			float referenceWidth = (barDimensions.width() - (2 * strokeWidth));
			if (mReferenceValue >= 0) {
				referenceWidth = mReferenceValue / mMax
						* (barDimensions.width() - (2 * strokeWidth));
			}
			float currentWidth = mValue / mMax
					* (barDimensions.width() - (2 * strokeWidth));

			float goodWidth = currentWidth;
			if (goodWidth > referenceWidth) {
				goodWidth = referenceWidth;
			}

			// Border
			mBorderLeft = barDimensions.left + hStrokeWidth;
			mBorderTop = barDimensions.top + hStrokeWidth;
			mBorderRight = barDimensions.right - hStrokeWidth;
			mBorderBottom = barDimensions.bottom - hStrokeWidth;

			// Reference Indicator
			mReferenceX1 = mReferenceX2 = barDimensions.left + referenceWidth
					+ hStrokeWidth;
			mReferenceY1 = mBorderBottom + hStrokeWidth;
			mReferenceY2 = mReferenceY1 + mReferenceWeight;

			mReferencePath = new Path();
			mReferencePath.moveTo(mReferenceX1, mReferenceY1);
			mReferencePath.lineTo(mReferenceX2 - mReferenceWeight / 2,
					mReferenceY2);
			mReferencePath.lineTo(mReferenceX2 + mReferenceWeight / 2,
					mReferenceY2);
			mReferencePath.close();

			// Good-Rect
			mGoodBarLeft = barDimensions.left + strokeWidth;
			mGoodBarBottom = barDimensions.bottom - strokeWidth;
			mGoodBarTop = barDimensions.top + strokeWidth;
			mGoodBarRight = barDimensions.left + strokeWidth + goodWidth;

			// Bad-Rect
			if (currentWidth > referenceWidth) {
				mBadBarTop = mGoodBarTop;
				mBadBarLeft = mGoodBarRight;
				mBadBarRight = mGoodBarLeft + currentWidth;
				mBadBarBottom = mGoodBarBottom;
			} else {
				mBadBarTop = mBadBarLeft = mBadBarRight = mBadBarBottom = 0;
			}

		}

		updateColors();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// Reference Indicator
		if (mReferenceValue >= 0) {
			canvas.drawPath(mReferencePath, mReferencePaint);
		}

		// Border
		canvas.drawRect(mBorderLeft, mBorderTop, mBorderRight, mBorderBottom,
				mBorderPaint);

		// Good-Rect
		canvas.drawRect(mGoodBarLeft, mGoodBarTop, mGoodBarRight,
				mGoodBarBottom, mGoodPaint);

		// Bad-Rect
		if (mBadBarLeft != 0 || mBadBarBottom != 0 || mBadBarRight != 0
				|| mBadBarTop != 0) {
			canvas.drawRect(mBadBarLeft, mBadBarTop, mBadBarRight,
					mBadBarBottom, mBadPaint);
		}

	}

	/**
	 * Returns the calculated dimensions for the BenchmarkBar.
	 * 
	 * @return
	 */
	private Rect getBarDimensions() {
		int barWidth = Math.min(mWeight, mVertical ? mWidth : mHeight);

		// initial values, respecting paddings
		int top = getPaddingTop(), bottom = mHeight - getPaddingBottom();
		int left = getPaddingLeft(), right = mWidth - getPaddingRight();

		if (mVertical) {
			right = Math.min(right, left + barWidth);
			if (right < left) {
				right = left;
			}
		} else {
			bottom = Math.min(bottom, top + barWidth);
			if (bottom < left) {
				bottom = left;
			}
		}
		return new Rect(left, top, right, bottom);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mWidth = measureWidth(widthMeasureSpec);
		mHeight = measureHeight(heightMeasureSpec);
		updateDimensions();
		setMeasuredDimension(mWidth, mHeight);
	}

	/**
	 * Measure the width.
	 * 
	 * Called inside onMeasure.
	 * 
	 * @param measureSpec
	 * @return
	 */
	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			if (mVertical) {
				result = (int) mWeight + getPaddingLeft() + getPaddingRight()
						+ mReferenceWeight;
				if (specMode == MeasureSpec.AT_MOST) {
					// Respect AT_MOST value if that was what is called for by
					// measureSpec
					result = Math.min(result, specSize);
				}

			} else {
				result = specSize;
			}
		}
		return result;
	}

	/**
	 * Measure the height.
	 * 
	 * Called inside onMeasure.
	 * 
	 * @param measureSpec
	 * @return
	 */
	private int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			if (mVertical) {
				result = specSize;

			} else {
				result = (int) mWeight + getPaddingLeft() + getPaddingRight()
						+ mReferenceWeight;
				if (specMode == MeasureSpec.AT_MOST) {
					// Respect AT_MOST value if that was what is called for by
					// measureSpec
					result = Math.min(result, specSize);
				}
			}
		}
		return result;
	}

	public void setGradientColors(int good1, int good2, int bad1, int bad2) {
		mGoodGradient1 = good1;
		mGoodGradient2 = good2;
		mBadGradient1 = bad1;
		mBadGradient2 = bad2;
		updateColors();
		invalidate();
	}
}
