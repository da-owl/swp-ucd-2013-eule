package com.example.swp_ucd_2013_eule.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.swp_ucd_2013_eule.R;

public class ReferenceBar extends View {
	private boolean mVertical = true;
	private int mWeight;

	private Paint mBorderPaint;
	private Paint mGoodPaint;
	private Paint mBadPaint;

	private int mGoodGradient1 = 0xFFa3d618;
	private int mGoodGradient2 = 0xFF3bb50b;
	private int mBadGradient1 = 0xFFdb9b0f;
	private int mBadGradient2 = 0xFFdb0f0f;

	private Rect mContentDimensions = new Rect();
	private float mBorderTop, mBorderLeft, mBorderRight, mBorderBottom;
	private float mGoodBarTop, mGoodBarLeft, mGoodBarRight, mGoodBarBottom;
	private float mBadBarTop, mBadBarLeft, mBadBarRight, mBadBarBottom;

	private float mMax = 100;
	private float mValue = 50;

	public ReferenceBar(Context context) {
		super(context);
		mWeight = dpToPx(90);
		initBar();
	}

	public ReferenceBar(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.BenchmarkBar);

		mVertical = !a.getBoolean(R.styleable.ReferenceBar_horizontal, false);
		mWeight = dpToPx(a.getInt(R.styleable.ReferenceBar_weight, 90));

		a.recycle();

		initBar();
	}

	private int dpToPx(int value) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				value, getResources().getDisplayMetrics());
	}

	private void initBar() {
		mBorderPaint = new Paint();
		mBorderPaint.setAntiAlias(true);
		mBorderPaint.setColor(0xFFffffff);
		mBorderPaint.setStyle(Style.STROKE);
		mBorderPaint.setStrokeWidth(2);

		mGoodPaint = new Paint();
		mGoodPaint.setAntiAlias(true);

		mBadPaint = new Paint();
		mBadPaint.setAntiAlias(true);

		// TODO required? updateGradients();
	}

	private void updateGradients() {
		float strokeWidth = mBorderPaint.getStrokeWidth();
		float gsx1 = 0, gsy1 = 0, gsx2 = 0, gsy2 = 0;
		float bsx1 = 0, bsy1 = 0, bsx2 = 0, bsy2 = 0;

		if (mVertical) {
			float centerY = mContentDimensions.top
					+ ((float) mContentDimensions.height()) / 2;
			gsy1 = centerY;
			gsy2 = mContentDimensions.top + strokeWidth;

			bsy1 = centerY;
			bsy2 = mContentDimensions.bottom - strokeWidth;
		} else {
			float centerX = mContentDimensions.left
					+ ((float) mContentDimensions.width()) / 2;
			gsx1 = centerX;
			gsx2 = mContentDimensions.right - strokeWidth;

			bsx1 = centerX;
			bsx2 = mContentDimensions.left + strokeWidth;
		}

		Shader goodShader = new LinearGradient(gsx1, gsy1, gsx2, gsy2,
				mGoodGradient1, mGoodGradient2, TileMode.CLAMP);
		mGoodPaint.setShader(goodShader);

		Shader badShader = new LinearGradient(bsx1, bsy1, bsx2, bsy2,
				mBadGradient1, mBadGradient2, TileMode.CLAMP);
		mBadPaint.setShader(badShader);
	}

	private void updateContentDimensions() {
		int availWidth = getMeasuredWidth(), availHeight = getMeasuredHeight();

		// initial values, respecting paddings
		int top = getPaddingTop(), bottom = availHeight - getPaddingBottom();
		int left = getPaddingLeft(), right = availWidth - getPaddingRight();

		if (mVertical) {
			right = Math.min(right, left + mWeight);
			if (right < left) {
				right = left;
			}
		} else {
			bottom = Math.min(bottom, top + mWeight);
			if (bottom < top) {
				bottom = top;
			}
		}
		mContentDimensions = new Rect(left, top, right, bottom);
	}

	private void updateBarFillDimensions() {
		float strokeWidth = mBorderPaint.getStrokeWidth();
		if (mVertical) {
			float centerY = mContentDimensions.top
					+ ((float) mContentDimensions.height()) / 2;

			if (mValue >= 0) {
				// Good-Rect
				float goodHeight = mValue / mMax
						* (mContentDimensions.height() / 2 - strokeWidth);

				mGoodBarLeft = mContentDimensions.left + strokeWidth;
				mGoodBarBottom = centerY;
				mGoodBarTop = centerY - goodHeight;
				mGoodBarRight = mContentDimensions.right - strokeWidth;

			} else {
				// Bad-Rect
				float badHeight = (-mValue) / mMax
						* (mContentDimensions.height() / 2 - strokeWidth);

				mBadBarLeft = mContentDimensions.left + strokeWidth;
				mBadBarBottom = centerY + badHeight;
				mBadBarTop = centerY;
				mBadBarRight = mContentDimensions.right - strokeWidth;
			}

		} else {
			float centerX = mContentDimensions.left
					+ ((float) mContentDimensions.width()) / 2;

			if (mValue >= 0) {
				// Good-Rect
				float goodWidth = mValue / mMax
						* (mContentDimensions.width() / 2 - strokeWidth);

				mGoodBarLeft = centerX;
				mGoodBarBottom = mContentDimensions.top + strokeWidth;
				mGoodBarTop = mContentDimensions.bottom - strokeWidth;
				mGoodBarRight = centerX + goodWidth;

			} else {
				// Bad-Rect
				float badWidth = (-mValue) / mMax
						* (mContentDimensions.width() / 2 - strokeWidth);

				mBadBarLeft = centerX - badWidth;
				mBadBarBottom = mContentDimensions.top + strokeWidth;
				mBadBarTop = mContentDimensions.bottom - strokeWidth;
				mBadBarRight = centerX;
			}
		}
	}

	private void updateDimensions() {
		updateContentDimensions();
		float strokeWidth = mBorderPaint.getStrokeWidth(), hStrokeWidth = strokeWidth / 2;

		// Border
		mBorderLeft = mContentDimensions.left + hStrokeWidth;
		mBorderTop = mContentDimensions.top + hStrokeWidth;
		mBorderRight = mContentDimensions.right - hStrokeWidth;
		mBorderBottom = mContentDimensions.bottom - hStrokeWidth;

		updateBarFillDimensions();
		updateGradients();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = measureWidth(widthMeasureSpec);
		int height = measureHeight(heightMeasureSpec);
		setMeasuredDimension(width, height);
		updateDimensions();
	}

	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			if (mVertical) {
				result = (int) mWeight + getPaddingLeft() + getPaddingRight();
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
				result = (int) mWeight + getPaddingLeft() + getPaddingRight();
				if (specMode == MeasureSpec.AT_MOST) {
					// Respect AT_MOST value if that was what is called for by
					// measureSpec
					result = Math.min(result, specSize);
				}
			}
		}
		return result;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// Border
		canvas.drawRect(mBorderLeft, mBorderTop, mBorderRight, mBorderBottom,
				mBorderPaint);

		if (mValue >= 0) {
			// Good-Rect
			canvas.drawRect(mGoodBarLeft, mGoodBarTop, mGoodBarRight,
					mGoodBarBottom, mGoodPaint);
		} else {
			// Bad-Rect
			canvas.drawRect(mBadBarLeft, mBadBarTop, mBadBarRight,
					mBadBarBottom, mBadPaint);
		}
	}

	public void setValue(float val) {
		if (val > 0) {
			mValue = Math.min(val, mMax);
		} else if (val < 0) {
			mValue = Math.max(val, -mMax);
		} else {
			mValue = val;
		}

		updateBarFillDimensions();
		invalidate();
	}

	public void setGradientColors(int good1, int good2, int bad1, int bad2) {
		mGoodGradient1 = good1;
		mGoodGradient2 = good2;
		mBadGradient1 = bad1;
		mBadGradient2 = bad2;
		updateGradients();
		invalidate();
	}
}
