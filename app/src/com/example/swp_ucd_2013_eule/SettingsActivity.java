package com.example.swp_ucd_2013_eule;

import com.example.swp_ucd_2013_eule.R;
import android.app.Activity;
import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SettingsActivity extends Activity {
	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		listview = (ListView) findViewById(R.id.list);
	    String[] items = new String[] {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7"};
	    ArrayAdapter<String> adapter =
	      new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
	    
	    listview.setAdapter(adapter);
	}

}