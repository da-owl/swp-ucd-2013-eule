package com.example.swp_ucd_2013_eule.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.swp_ucd_2013_eule.data.ForestItem;

public class Forest extends View {
	private static final int FOREST_ROUNDED_CORNER = 70;
	private static final int FOREST_STROKE_WIDTH = 4;
	private Path mForestPath;
	private Paint mForestPaint;
	private Paint mForestPaintBorder;
	private ArrayList<ForestItem> mForestItems = new ArrayList<ForestItem>();
	private HashMap<ForestItem, RectF> mMoveableItems = new HashMap<ForestItem, RectF>();
	private boolean mInitComplet;
	private Random mRand = new Random();

	private ForestItemListener mForestItemListener;

	public Forest(Context context) {
		super(context);
		initForest();
	}

	public Forest(Context context, AttributeSet attrs) {
		super(context, attrs);
		initForest();
	}

	private void initForest() {
		mForestPaint = new Paint();
		mForestPaint.setAntiAlias(true);
		mForestPaint.setColor(0xFF86b55c);
		mForestPaint.setStyle(Style.FILL);
		mForestPaint.setStrokeWidth(FOREST_STROKE_WIDTH);
		mForestPaint.setStrokeJoin(Join.ROUND);
		mForestPaint.setStrokeCap(Cap.ROUND);
		mForestPaint.setPathEffect(new CornerPathEffect(FOREST_ROUNDED_CORNER));

		mForestPaintBorder = new Paint();
		mForestPaintBorder.setAntiAlias(true);
		mForestPaintBorder.setColor(0xFF36660a);
		mForestPaintBorder.setStyle(Style.STROKE);
		mForestPaintBorder.setStrokeWidth(FOREST_STROKE_WIDTH);
		mForestPaintBorder.setStrokeJoin(Join.ROUND);
		mForestPaintBorder.setStrokeCap(Cap.ROUND);
		mForestPaintBorder.setPathEffect(new CornerPathEffect(
				FOREST_ROUNDED_CORNER));

	}

	private void updateForestSize() {

		int width = getMeasuredWidth();
		int heigth = getMeasuredHeight();

		int width48 = width / 2;
		int width28 = width48 / 2;
		int width18 = width28 / 2;
		int width38 = width28 + width18;
		int width58 = width48 + width18;
		int width68 = width48 + width28;
		int width78 = width48 + width38;

		int heigth48 = heigth / 2;
		int heigth28 = heigth48 / 2;
		int heigth18 = heigth28 / 2;
		int heigth38 = heigth28 + heigth18 - 10;
		int heigth58 = heigth48 + heigth18;
		int heigth68 = heigth48 + heigth28;
		int heigth78 = heigth48 + heigth38;

		mForestPath = new Path();
		// start
		mForestPath.moveTo(width48, FOREST_STROKE_WIDTH);
		// Quadrant I
		mForestPath.lineTo(width58 + 15, heigth18);
		mForestPath.lineTo(width78 + 25, FOREST_STROKE_WIDTH);
		mForestPath.lineTo(width68 + 25, heigth38);
		mForestPath.lineTo(width - FOREST_STROKE_WIDTH, heigth48 - 25);
		// Quadrant IV
		mForestPath.lineTo(width, heigth58);
		mForestPath.lineTo(width78, heigth78);
		mForestPath.lineTo(width58, heigth78);
		mForestPath.lineTo(width48, heigth78 - FOREST_STROKE_WIDTH);
		// Quadrant III
		mForestPath.lineTo(width38, heigth - FOREST_STROKE_WIDTH);
		mForestPath.lineTo(FOREST_STROKE_WIDTH, heigth78);
		mForestPath.lineTo(width18, heigth68);
		mForestPath.lineTo(FOREST_STROKE_WIDTH, heigth48);
		// Quadrant II
		mForestPath.lineTo(width18, heigth38);
		mForestPath.lineTo(width18, heigth28);
		mForestPath.lineTo(FOREST_STROKE_WIDTH, FOREST_STROKE_WIDTH);
		mForestPath.lineTo(width48, FOREST_STROKE_WIDTH);
		// End
		mForestPath.close();

	}

	public void placeItemsInForest() {
		if (!mInitComplet) {
			ForestItem[] items = ForestItem.getExamples(getContext());

			items[0].setCoordinates(getMeasuredWidth() / 4,
					(getMeasuredHeight() / 4) / 2);
			items[1].setCoordinates(
					getMeasuredWidth() - getMeasuredWidth() / 4,
					getMeasuredHeight() - getMeasuredHeight() / 2);
			items[2].setCoordinates(getMeasuredWidth() / 6, getMeasuredHeight()
					- getMeasuredHeight() / 4);
			items[3].setCoordinates(getMeasuredWidth() / 6, getMeasuredHeight()
					- getMeasuredHeight() / 2);
			items[4].setCoordinates(getMeasuredWidth() - 40
					- getMeasuredWidth() / 4, getMeasuredHeight() - 30
					- getMeasuredHeight() / 4);
			items[5].setCoordinates(getMeasuredWidth() - 40
					- getMeasuredWidth() / 2, (getMeasuredHeight() / 4) + 50);

			mForestItems = new ArrayList<ForestItem>();
			for (ForestItem item : items) {
				mForestItems.add(item);
			}

			// frog gordon is moveable
			ForestItem gordon = items[5];
			RectF bounds = new RectF(gordon.getX() - 10, gordon.getY() - 20,
					gordon.getX() + 72 + 30, gordon.getY() + 48 + 30);
			mMoveableItems.put(items[5], bounds);

			mInitComplet = true;
		}

	}

	public void setForestItemListener(ForestItemListener forestItemListener) {
		this.mForestItemListener = forestItemListener;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawPath(mForestPath, mForestPaint);
		canvas.drawPath(mForestPath, mForestPaintBorder);
		placeItemsInForest();
		for (ForestItem item : mForestItems) {
			canvas.drawBitmap(item.getImage(), item.getX(), item.getY(), null);
		}

	}

	public void moveItems() {
		for (ForestItem item : mMoveableItems.keySet()) {
			RectF bounds = mMoveableItems.get(item);
			int x = (int) (mRand.nextInt((int) (bounds.right - bounds.left)) + bounds.left);
			int y = (int) (mRand.nextInt((int) (bounds.bottom - bounds.top)) + bounds.top);
			item.setCoordinates(x, y);
		}
		invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
				MeasureSpec.getSize(heightMeasureSpec));
		updateForestSize();
	}

	long mLastTouchDown;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean handled = false;

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			handled = true;
			mLastTouchDown = SystemClock.elapsedRealtime();
			break;

		case MotionEvent.ACTION_UP:
			handled = true;
			if (SystemClock.elapsedRealtime() - mLastTouchDown <= 500) {
				resolveItemClick(event.getX(), event.getY());
			}
			break;

		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_OUTSIDE:
			mLastTouchDown = 0;
		}

		return handled;
	}

	private void resolveItemClick(float x, float y) {
		for (ForestItem item : mForestItems) {
			if (isItemClicked(item, x, y)) {
				if (mForestItemListener != null) {
					mForestItemListener.onForestItemClicked(item);
					return;
				}
			}
		}
	}

	private boolean isItemClicked(ForestItem i, float x, float y) {
		return x >= i.getX() && x <= i.getX() + i.getImageWidth()
				&& y >= i.getY() && y <= i.getY() + i.getImageHeight();
	}

	public interface ForestItemListener {
		public void onForestItemClicked(ForestItem item);
	}
}
