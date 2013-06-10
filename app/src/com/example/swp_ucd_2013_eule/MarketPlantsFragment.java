package com.example.swp_ucd_2013_eule;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MarketPlantsFragment extends ListFragment {

	
	@Override
		// Displays a scrollable, clickable list of plants to the screen under 
		// a default formatting.
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    String[] values = new String[] { "Daffodil", "Bush1", "Bush2",
	        "Pine Tree", "Magnolia Tree", "Vine", "Rose", "Grass",
	        "Reeds", "Apple Tree", "Rushes", "Blackberry bush", "Strawberry plants" };
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
	        android.R.layout.simple_list_item_1, values);
	    setListAdapter(adapter);
	  }

	  @Override
	  // Method called when an item in the list is clicked. It opens up a dialogFragment
	  // that will eventually detail the item further and provide a button that allows it
	  // to be bought.
	  public void onListItemClick(ListView l, View v, int position, long id) {
		  FragmentManager manager = getFragmentManager();
	        BuyDialogFragment dialog = BuyDialogFragment.newInstance();
	        dialog.show(manager, "testing, testing 123");
	  }

}