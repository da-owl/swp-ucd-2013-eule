package com.example.swp_ucd_2013_eule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.swp_ucd_2013_eule.model.Forest;
import com.example.swp_ucd_2013_eule.model.MyForest;
import com.example.swp_ucd_2013_eule.view.ForestView;

/**
 * ForestFragment creates a view containing the ForestView class which is responsible
 * for drawing the forest.
 *
 */
public class ForestFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_forest, container,
				false);

		Forest forest = MyForest.getInstance().getForest();
		ItemBehaviour itemBehav = new ItemBehaviour(rootView);
		itemBehav.attachForest(forest);
		itemBehav.attachPointTextViews(
				(TextView) rootView.findViewById(R.id.txtDrops),
				(TextView) rootView.findViewById(R.id.txtForestSize));
		itemBehav.attachForestView();

		// TODO move animation to ForestView (and timer is not stopped ...)
		final ForestView forestView = (ForestView) rootView
				.findViewById(R.id.forest);

		return rootView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

}
