package com.example.swp_ucd_2013_eule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

public class MarketAnimalsFragment extends Fragment {


	@Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market_grid,container,false);
        GridView gridView = (GridView) view.findViewById(R.id.fragment_market_clothes);
        gridView.setAdapter(new MarketAnimalsTextImageAdapter(view.getContext())); // uses the view to get the context instead of getActivity().
        return view;
    }

	 @Override
	 public void onActivityCreated(Bundle savedInstanceState) {
	     super.onActivityCreated(savedInstanceState);
	 }

//	@Override
	// Method called when an item in the list is clicked. It opens up a
	// dialogFragment that will eventually detail the item further and provide a button that
	// allows it to be bought.
//	public void onGridItemClick(ListView l, View v, int position, long id) {
//		FragmentManager manager = getFragmentManager();
//		BuyDialogFragment dialog = BuyDialogFragment.newInstance();
//		dialog.show(manager, "testing, testing 123");
//	}
	 
}