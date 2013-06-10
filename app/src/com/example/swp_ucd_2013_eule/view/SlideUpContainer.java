package com.example.swp_ucd_2013_eule.view;

import com.example.swp_ucd_2013_eule.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class SlideUpContainer extends RelativeLayout {
	private Animation mShowAnimation;
	private Animation mCloseAnimation;

	public SlideUpContainer(Context context) {
		super(context);
		init();
	}

	public SlideUpContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		setVisibility(INVISIBLE);
		mShowAnimation = AnimationUtils.loadAnimation(getContext(),
				R.anim.slide_up);
		mCloseAnimation = AnimationUtils.loadAnimation(getContext(),
				R.anim.slide_down);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		System.out.println(MeasureSpec.getSize(heightMeasureSpec) * 0.5);
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(
				(int) (MeasureSpec.getSize(heightMeasureSpec) * 0.5),
				MeasureSpec.getMode(heightMeasureSpec));
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public void slideOpen() {
		startAnimation(mShowAnimation);
		setVisibility(android.view.View.VISIBLE);
	}

	public void slideClose() {
		startAnimation(mCloseAnimation);
		setVisibility(android.view.View.INVISIBLE);
	}
}
