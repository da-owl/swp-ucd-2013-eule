package com.example.swp_ucd_2013_eule;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.swp_ucd_2013_eule.model.Forest;
import com.example.swp_ucd_2013_eule.model.MyForest;
import com.example.swp_ucd_2013_eule.view.OnClickNotHandledListener;
import com.example.swp_ucd_2013_eule.view.SlideUpContainer;
/**
 * OtherForestActivity is started when a user clicked on a name in the top/friend list.
 * It starts a new full screen activity containing the view of the other users forest.
 *
 */
public class OtherForestActivity extends Activity implements
		OnClickNotHandledListener {

	private SlideUpContainer mSlideUpContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_forest);

		setTitle(getIntent().getStringExtra("userName") + "'s forest");

		// TODO User other's forest
		Forest forest = MyForest.getInstance().getForest();
		ItemBehaviour itemBehav = new ItemBehaviour(getWindow().getDecorView()
				.getRootView());
		itemBehav.attachForest(forest);
		itemBehav.attachPointTextViews((TextView) findViewById(R.id.txtDrops),
				(TextView) findViewById(R.id.txtForestSize));
		itemBehav.attachForestView();
	}

	@Override
	public void clickNotHandled() {
		mSlideUpContainer.slideClose();
	}
}
