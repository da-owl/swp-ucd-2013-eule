package com.example.swp_ucd_2013_eule.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.swp_ucd_2013_eule.R;

public class Forest extends View {
	private static final int FOREST_ROUNDED_CORNER = 70;
	private static final int FOREST_STROKE_WIDTH = 4;
	private Path mForestPath;
	private Paint mForestPaint;
	private Paint mForestPaintBorder;
	private Bitmap mTree = BitmapFactory.decodeResource(getResources(),
			R.drawable.forest);
	private ArrayList<ForestItem> mForestItems;

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
		mForestPaint.setColor(0xFF426200);
		mForestPaint.setStyle(Style.FILL);
		mForestPaint.setStrokeWidth(FOREST_STROKE_WIDTH);
		mForestPaint.setStrokeJoin(Join.ROUND);
		mForestPaint.setStrokeCap(Cap.ROUND);
		mForestPaint.setPathEffect(new CornerPathEffect(FOREST_ROUNDED_CORNER));

		mForestPaintBorder = new Paint();
		mForestPaintBorder.setAntiAlias(true);
		mForestPaintBorder.setColor(0xFF9CEF60);
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
		int heigth38 = heigth28 + heigth18;
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

	private void placeItemsInForest() {
		mForestItems = new ArrayList<ForestItem>();
		int x = getMeasuredWidth() / 4;
		int y = (getMeasuredHeight() / 4) / 2;
		mForestItems.add(new ForestItem(mTree, x, y, "Tree 1"));

		int x1 = getMeasuredWidth() - getMeasuredWidth() / 4;
		int y1 = getMeasuredHeight() - getMeasuredHeight() / 2;
		mForestItems.add(new ForestItem(mTree, x1, y1, "Tree 2"));

		int x2 = getMeasuredWidth() / 6;
		int y2 = getMeasuredHeight() - getMeasuredHeight() / 4;
		mForestItems.add(new ForestItem(mTree, x2, y2, "Tree 3"));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// canvas.drawOval(mMeadowBounds, mMeadowPaint);
		canvas.drawPath(mForestPath, mForestPaint);
		canvas.drawPath(mForestPath, mForestPaintBorder);

		placeItemsInForest();
		for (ForestItem item : mForestItems) {
			canvas.drawBitmap(item.getBitmap(), item.getXCoordinate(),
					item.getYCoordinate(), null);
		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
				MeasureSpec.getSize(heightMeasureSpec));
		updateForestSize();
	}

	private class ForestItem {
		private int mX;
		private int mY;
		private int mWidth;
		private int mHeight;
		private Bitmap mBitmap;
		private String mName;

		ForestItem(Bitmap bitmap, int x, int y, String name) {
			mBitmap = bitmap;
			mX = x;
			mY = y;
			mHeight = bitmap.getHeight();
			mWidth = bitmap.getWidth();
			mName = name;
		}

		public Bitmap getBitmap() {
			return mBitmap;
		}

		public int getXCoordinate() {
			return mX;
		}

		public int getYCoordinate() {
			return mY;
		}

		public boolean isClicked(float x, float y) {
			if (mX <= x && x <= mX + mWidth) {
				if (mY <= y && y <= mY + mHeight) {
					return true;
				}
			}
			return false;
		}

		public String getName() {
			return mName;
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX(), y = event.getY();

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			for (ForestItem item : mForestItems) {
				if (item.isClicked(x, y)) {
					Toast.makeText(this.getContext(), item.getName(),
							Toast.LENGTH_SHORT).show();
					return true;
				}
			}
		}
		return false;
	}

}
