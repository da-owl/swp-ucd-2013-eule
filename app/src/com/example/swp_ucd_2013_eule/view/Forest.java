package com.example.swp_ucd_2013_eule.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class Forest extends View {

	private Paint mMeadowPaint;
	private RectF mMeadowBounds;

	public Forest(Context context) {
		super(context);
		initForest();

	}

	public Forest(Context context, AttributeSet attrs) {
		super(context, attrs);
		initForest();
	}

	private void initForest() {
		mMeadowPaint = new Paint();
		mMeadowPaint.setAntiAlias(true);
		mMeadowPaint.setColor(0xFF228B22);
		mMeadowPaint.setStyle(Style.FILL);
		calculateForestSize();

	}

	private void calculateForestSize() {
		int level = 1;
		mMeadowBounds = new RectF(level * 4, level * 4, level * 4, level * 4);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawOval(mMeadowBounds, mMeadowPaint);

	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		 setMeasuredDimension(800,600);
		
	}
	
	
	

}
