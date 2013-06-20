package com.example.swp_ucd_2013_eule.view;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Shader;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.example.swp_ucd_2013_eule.R;
import com.example.swp_ucd_2013_eule.data.ForestItem;
import com.example.swp_ucd_2013_eule.data.UserForestItem;

public class Forest extends View {
	private static final float FOREST_ROUNDED_CORNER = 50;
	private static final float FOREST_STROKE_WIDTH = 2;

	private float mTileSize;
	private int mCols, mRows;

	private int mLevel = 7;

	private Path mForestPath;
	private Paint mForestPaint;
	private Paint mForestPaintBorder;
	private ArrayList<ForestItemWrapper> mForestItems = new ArrayList<ForestItemWrapper>();
	private boolean mInitComplet;
	private Random mRand = new Random();
	private SlideUpContainer mSlideUpContainer;

	private UserForestItemListener mForestItemListener;

	public Forest(Context context) {
		super(context);
		initForest();
	}

	public Forest(Context context, AttributeSet attrs) {
		super(context, attrs);
		initForest();
	}

	private int dpToPx(int value) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				value, getResources().getDisplayMetrics());
	}

	private void initForest() {
		mTileSize = dpToPx(95);

		mForestPaint = new Paint();
		mForestPaint.setAntiAlias(true);
		// mForestPaint.setColor(0xFF86b55c);
		mForestPaint.setStyle(Style.FILL);
		mForestPaint.setStrokeWidth(FOREST_STROKE_WIDTH);
		mForestPaint.setStrokeJoin(Join.ROUND);
		mForestPaint.setStrokeCap(Cap.ROUND);
		mForestPaint.setPathEffect(new CornerPathEffect(FOREST_ROUNDED_CORNER));

		Bitmap grassBMP = BitmapFactory.decodeResource(getContext()
				.getResources(), R.drawable.grass_tile);
		Shader grassShader = new BitmapShader(grassBMP, Shader.TileMode.REPEAT,
				Shader.TileMode.REPEAT);
		mForestPaint.setShader(grassShader);

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
		mForestPath = new Path();
		float baseX = FOREST_STROKE_WIDTH / 2, baseY = FOREST_STROKE_WIDTH / 2;
		float curX = baseX, curY = baseY;
		int rows = 1;

		// start
		mForestPath.moveTo(curX, curY);

		// move right
		int minSquareCols = mCols - 1;
		int tilesLeft = mLevel - minSquareCols * minSquareCols - 1;
		curX += mCols * mTileSize;
		mForestPath.lineTo(curX, curY);
		
		// move 1 down
		curY += mTileSize;
		mForestPath.lineTo(curX, curY);

		// move down
		if (tilesLeft > 0) {
			int vertTiles = Math.min(mCols-1, tilesLeft);
			tilesLeft -= vertTiles;
			rows += vertTiles;
			curY += (vertTiles * mTileSize);
			mForestPath.lineTo(curX, curY);
		}

		// move 1 left
		curX -= mTileSize;
		mForestPath.lineTo(curX, curY);

		// move left
		if (tilesLeft > 0) {
			curX -= tilesLeft * mTileSize;
			tilesLeft = 0;
			mForestPath.lineTo(curX, curY);
		}

		// move up/down
		curY += (mCols - rows - 1)* mTileSize;
		mForestPath.lineTo(curX, curY);

		// move to left end
		curX = baseX;
		mForestPath.lineTo(curX, curY);

		// End
		mForestPath.close();
	}

	public void placeItemsInForest() {
		if (!mInitComplet) {
			UserForestItem[] items = UserForestItem.getExamples(getContext());

			mForestItems.clear();

			float x, y;
			for (UserForestItem item : items) {
				int iw2 = item.getForestItem().getImage().getWidth() / 2;
				int ih2 = item.getForestItem().getImage().getHeight() / 2;
				x = FOREST_STROKE_WIDTH + (item.getTileX()) * mTileSize
						+ item.getOffsetX() * mTileSize - iw2;
				y = FOREST_STROKE_WIDTH + (item.getTileY()) * mTileSize
						+ item.getOffsetY() * mTileSize - ih2;
				mForestItems.add(new ForestItemWrapper(item, x, y));
			}

			mInitComplet = true;
		}

	}

	public void setForestItemListener(UserForestItemListener forestItemListener) {
		this.mForestItemListener = forestItemListener;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawPath(mForestPath, mForestPaint);
		canvas.drawPath(mForestPath, mForestPaintBorder);
		placeItemsInForest();
		for (ForestItemWrapper item : mForestItems) {
			canvas.drawBitmap(item.mItem.getForestItem().getImage(), item.mX,
					item.mY, null);
		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mCols = (int) Math.ceil(Math.sqrt(mLevel));
		mRows = (int) Math.ceil(((float) mLevel) / mCols);
		int width = (int) (mCols * mTileSize + FOREST_STROKE_WIDTH);
		int height = (int) (mRows * mTileSize + FOREST_STROKE_WIDTH);
		setMeasuredDimension(width, height);
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
				if (!resolveSliderClick(event.getX(), event.getY())) {
					resolveItemClick(event.getX(), event.getY());
				}
			}
			break;

		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_OUTSIDE:
			mLastTouchDown = 0;
		}

		return handled;
	}

	private boolean resolveSliderClick(float x, float y) {
		if (mSlideUpContainer.getVisibility() == android.view.View.VISIBLE) {
			// slider visible handling necessary
			if (x >= mSlideUpContainer.getX()
					&& x <= mSlideUpContainer.getX()
							+ mSlideUpContainer.getWidth()
					&& y >= mSlideUpContainer.getY()
					&& y <= mSlideUpContainer.getY()
							+ mSlideUpContainer.getHeight()) {
				return true;
				// click was inside slider, no handling necessary
			} else {
				// click was outside slider, close slider
				mSlideUpContainer.slideClose();
			}
		}
		// Slider invisible no handling necessary
		return false;
	}

	private void resolveItemClick(float x, float y) {
		for (ForestItemWrapper item : mForestItems) {
			if (isItemClicked(item, x, y)) {
				if (mForestItemListener != null) {
					mForestItemListener.onForestItemClicked(item.mItem);
					return;
				}
			}
		}
	}

	private boolean isItemClicked(ForestItemWrapper i, float x, float y) {
		ForestItem item = i.mItem.getForestItem();
		return x >= i.mX && x <= i.mX + item.getImageWidth() && y >= i.mY
				&& y <= i.mY + item.getImageHeight();
	}

	public interface UserForestItemListener {
		public void onForestItemClicked(UserForestItem item);
	}

	public void setSlideUpContainer(SlideUpContainer container) {
		mSlideUpContainer = container;
	}

	private class ForestItemWrapper {
		public UserForestItem mItem;
		public float mX, mY;

		public ForestItemWrapper(UserForestItem item, float x, float y) {
			mItem = item;
			mX = x;
			mY = y;
		}
	}
}
