package com.example.swp_ucd_2013_eule.view;

import java.util.ArrayList;

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
import android.view.ViewParent;

import com.example.swp_ucd_2013_eule.R;
import com.example.swp_ucd_2013_eule.data.ForestItem;
import com.example.swp_ucd_2013_eule.model.APIModel;
import com.example.swp_ucd_2013_eule.model.Forest;
import com.example.swp_ucd_2013_eule.model.UserForestItem;
import com.example.swp_ucd_2013_eule.net.APIException;

public class ForestView extends View {
	
	private static final Integer FOREST_ID = 1;
	
	private static final float FOREST_ROUNDED_CORNER = 50;
	private static final float FOREST_STROKE_WIDTH = 2;

	private float mTileSize;
	private int mCols, mRows;

	private int mLevel = 17;
	
	private APIModel<Forest, Forest> mForestAPI;	
	private APIModel<UserForestItem, Forest> mUserItemAPI;
	
	private Forest mForest;

	private Path mForestPath;
	private Paint mForestPaint;
	private Paint mForestPaintBorder;
	private ArrayList<ForestItemWrapper> mForestItems = new ArrayList<ForestItemWrapper>();
	private boolean mInitComplet;
	private SlideUpContainer mSlideUpContainer;

	private float mCurX, mCurY;

	// used to eliminate straight lines
	private float[][][] mDistortTop;
	private float[][][] mDistortBottom;
	private float[][][] mDistortLeft;
	private float[][][] mDistortRight;

	private int mCurDistortTop;
	private int mCurDistortBottom;
	private int mCurDistortRight;
	private int mCurDistortLeft;

	private float mCurTouchX;
	private float mCurTouchY;

	private UserForestItemListener mForestItemListener;

	public ForestView(Context context) {
		super(context);		
		initForest();
		

		/**
		 * init the API and retrieve a forest
		 * 
		try {
			mForestAPI = new APIModel<Forest, Forest>(Forest.class, context);
			mUserItemAPI = new APIModel<UserForestItem, Forest>(UserForestItem.class, context);
			mForest = mForestAPI.get(new Forest(), FOREST_ID);
			mLevel = mForest.getLevel();
			System.out.println("APITest - init() level " + mForest.getLevel());
		} catch (APIException e) {
			// TODO: display error message
		}	
		*/
		
		/**
		 * try to add useritem, save it and create relation in the database
		 * 
		try {
			UserForestItem item = new UserForestItem(null);
			
			if(mForest.addItem(item)){
				// oder: mForest.addItem(item, 1, 1);
				mUserItemAPI.save(item);
				mUserItemAPI.addToParent(item, mForest, "userforestitems");
			}		
		} catch (APIException e) {
			// TODO: display error message
		}
		
		
		/**
		 * retrieve and set UserForestItems
		 * 
		try {
			ListUserForestItem> items = mUserItemAPI.getAllByParent(mForest, new UserForestItem(null), "userforestitems");
			mForest.setUserforestitems(items);
		} catch (APIException e) {
			// TODO: display error message
		}
		*/
		
		
	}

	public ForestView(Context context, AttributeSet attrs) {
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

		float[][] topTile1 = new float[][] { new float[] { 0.1f, 0.1f },
				new float[] { 0.5f, 0f }, new float[] { 0.8f, 0.2f } };
		float[][] topTile2 = new float[][] { new float[] { 0.2f, 0.1f },
				new float[] { 0.5f, 0.12f }, new float[] { 0.8f, 0.1f } };
		float[][] topTile3 = new float[][] { new float[] { 0.3f, 0.1f },
				new float[] { 0.4f, 0.12f }, new float[] { 0.5f, 0.1f },
				new float[] { 0.7f, 0f }, new float[] { 0.95f, 0.02f } };
		mDistortTop = new float[][][] { topTile1, topTile2, topTile3 };

		mDistortBottom = new float[mDistortTop.length][][];
		for (int i = 0; i < mDistortTop.length; i++) {
			float[][] baseDistort = mDistortTop[mDistortTop.length - i - 1];
			mDistortBottom[i] = new float[baseDistort.length][];
			for (int j = 0; j < baseDistort.length; j++) {
				mDistortBottom[i][j] = new float[] { -baseDistort[j][0],
						-baseDistort[j][1] };
			}
		}

		float[][] rightTile1 = new float[][] { new float[] { 0f, 0.4f },
				new float[] { -0.1f, 0.9f } };
		float[][] rightTile2 = new float[][] { new float[] { -0.13f, 0.3f },
				new float[] { -0.04f, 0.3f }, new float[] { -0.13f, 0.4f } };
		float[][] rightTile3 = new float[][] { new float[] { -0.05f, 0.1f },
				new float[] { 0f, 0.3f }, new float[] { -0.07f, 0.7f } };
		mDistortRight = new float[][][] { rightTile1, rightTile2, rightTile3 };

		mDistortLeft = new float[mDistortRight.length][][];
		for (int i = 0; i < mDistortRight.length; i++) {
			float[][] baseDistort = mDistortRight[mDistortRight.length - i - 1];
			mDistortLeft[i] = new float[baseDistort.length][];
			for (int j = 0; j < baseDistort.length; j++) {
				mDistortLeft[i][j] = new float[] { -baseDistort[j][0],
						-baseDistort[j][1] };
			}
		}
	}

	private void applyDistort(float[][] distorts, float targetX, float targetY) {
		float oldX = mCurX, oldY = mCurY;

		for (float[] distort : distorts) {
			mCurX = oldX + distort[0] * mTileSize;
			mCurY = oldY + distort[1] * mTileSize;
			mForestPath.lineTo(mCurX, mCurY);
		}

		mCurX = targetX;
		mCurY = targetY;
		mForestPath.lineTo(mCurX, mCurY);
	}

	private void moveRight() {
		float targetX = mCurX + mTileSize, targetY = mCurY;
		float[][] distorts = mDistortTop[mCurDistortTop];
		applyDistort(distorts, targetX, targetY);
		mCurDistortTop = ++mCurDistortTop % mDistortTop.length;
	}

	private void moveLeft() {
		float targetX = mCurX - mTileSize, targetY = mCurY;
		float[][] distorts = mDistortBottom[mCurDistortBottom];
		applyDistort(distorts, targetX, targetY);
		mCurDistortBottom = mCurDistortBottom == 0 ? mDistortBottom.length - 1
				: --mCurDistortBottom;
	}

	private void moveRightDown() {
		float targetX = mCurX, targetY = mCurY + mTileSize;
		float[][] distorts = mDistortRight[mCurDistortRight];
		applyDistort(distorts, targetX, targetY);
		mCurDistortRight = ++mCurDistortRight % mDistortRight.length;
	}

	private void moveLeftUp() {
		float targetX = mCurX, targetY = mCurY - mTileSize;
		float[][] distorts = mDistortLeft[mCurDistortLeft];
		applyDistort(distorts, targetX, targetY);
		mCurDistortLeft = mCurDistortLeft == 0 ? mDistortLeft.length - 1
				: --mCurDistortLeft;
	}

	private void updateForestSize() {
		mForestPath = new Path();
		mCurDistortTop = 0;
		mCurDistortRight = 0;
		mCurDistortBottom = (mCols - 1) % mDistortBottom.length;

		float baseX = FOREST_STROKE_WIDTH / 2, baseY = FOREST_STROKE_WIDTH / 2;
		mCurX = baseX;
		mCurY = baseY;

		// start
		mForestPath.moveTo(mCurX, mCurY);

		// move right
		int minSquareCols = mCols - 1;
		int tilesLeft = mLevel - minSquareCols * minSquareCols - 1;
		for (int i = 0; i < mCols; ++i) {
			moveRight();
		}

		// move 1 down
		int rows = 1;
		moveRightDown();

		// move down
		if (tilesLeft > 0) {
			int vertTiles = Math.min(mCols - 1, tilesLeft);
			tilesLeft -= vertTiles;
			rows += vertTiles;
			for (int i = 0; i < vertTiles; ++i) {
				moveRightDown();
			}
		}

		// move 1 left
		int leftMoves = 1;
		moveLeft();

		// move left
		if (tilesLeft > 0) {
			leftMoves += tilesLeft;
			for (int i = 0; i < tilesLeft; ++i) {
				moveLeft();
			}
		}

		// move up/down
		int leftRowsDown = mCols - rows - 1;
		if (leftRowsDown >= 0) {
			rows += leftRowsDown;
			for (int i = 0; i < leftRowsDown; i++) {
				moveRightDown();
			}
			mCurDistortLeft = (rows - 1) % mDistortLeft.length;

		} else {
			mCurDistortLeft = (rows - 1) % mDistortLeft.length;
			moveLeftUp();
			--rows;
		}

		// move to left end
		for (int i = 0; i < mCols - leftMoves; i++) {
			moveLeft();
		}

		// move up
		for (int i = 0; i < rows; i++) {
			moveLeftUp();
		}

		// End
		mForestPath.close();
	}

	public void placeItemsInForest() {
		if (!mInitComplet) {
			UserForestItem[] items = UserForestItem.getExamples(getContext());
			
			/**
			mForest.getItems();
			*/
			
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
		setMeasuredDimension(
				Math.max(width, MeasureSpec.getSize(widthMeasureSpec)),
				Math.max(height, MeasureSpec.getSize(heightMeasureSpec)));
		updateForestSize();
	}

	long mLastTouchDown;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// e.g. viewpager should not scroll horizontal
		ViewParent parent = getParent();
		if (parent != null) {
			parent.requestDisallowInterceptTouchEvent(true);
		}

		boolean handled = false;

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			handled = true;
			mCurTouchX = (int) event.getRawX();
			mCurTouchY = (int) event.getRawY();
			mLastTouchDown = SystemClock.elapsedRealtime();
			break;

		case MotionEvent.ACTION_UP:
			handled = true;
			if (SystemClock.elapsedRealtime() - mLastTouchDown <= 200) {
				float clickX = event.getX() + getScrollX();
				float clickY = event.getY() + getScrollY();
				if (!resolveSliderClick(clickX, clickY)) {
					resolveItemClick(clickX, clickY);
				}
			}
			break;

		case MotionEvent.ACTION_MOVE: {
			handled = true;
			float x2 = event.getRawX();
			float y2 = event.getRawY();
			scrollBy((int) (mCurTouchX - x2), (int) (mCurTouchY - y2));
			mCurTouchX = x2;
			mCurTouchY = y2;
			break;
		}

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
