package com.example.swp_ucd_2013_eule.view;

import java.util.concurrent.ConcurrentSkipListSet;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.swp_ucd_2013_eule.ListActivity;
import com.example.swp_ucd_2013_eule.OtherForestActivity;
import com.example.swp_ucd_2013_eule.R;

/**
 * SocialList is a custom implementation of the TableLayout. It is used to
 * define a list with dynamically added and removed content according to our
 * specification. The content is the SoicalRow class.
 * 
 * @author Marc
 * 
 */
public class SocialList extends TableLayout {

	private ConcurrentSkipListSet<RowElement> mRows = new ConcurrentSkipListSet<RowElement>();
	private LayoutInflater mInflater;
	private Context mContext;
	private String listName = "";

	public SocialList(Context context) {
		super(context);
		mContext = context;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public SocialList(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	/**
	 * draws the list elements which were previously added with the addRow()
	 * method.
	 */
	public void drawList() {
		removeAllViews();
		TableRow header = (TableRow) mInflater.inflate(
				R.layout.social_table_header, null);
		addView(header);

		for (RowElement elem : mRows) {
			// TableRow friendRow = (TableRow)
			// rootView.findViewById(R.id.social_friend_row);
			SocialRow friendRow = (SocialRow) mInflater.inflate(
					R.layout.social_friend_row, null);

			// set Name
			TextView name = (TextView) friendRow
					.findViewById(R.id.social_friend_name);
			name.setText(elem.mUserName);
			// set Level
			TextView level = (TextView) friendRow
					.findViewById(R.id.social_friend_level);
			level.setText(String.valueOf(elem.mLevel * 5) + " m²");
			// set Position
			int playerPosition = elem.mPosition;
			TextView position = (TextView) friendRow
					.findViewById(R.id.social_friend_position);
			position.setText(String.valueOf(playerPosition));

			// friendRow.addRowElement(elem);
			final String userName = elem.mUserName;

			friendRow.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// show other forest
					Intent intent = new Intent(mContext,
							OtherForestActivity.class);
					intent.putExtra("userName", userName); // Optional
															// parameters
					mContext.startActivity(intent);

				}
			});

			TableRow divider = (TableRow) mInflater.inflate(
					R.layout.social_row_divider, null);
			addView(divider);
			addView(friendRow);

		}

		if (listName != "") {
			TableRow divider = (TableRow) mInflater.inflate(
					R.layout.social_row_divider, null);
			addView(divider);

			TableRow footer = (TableRow) mInflater.inflate(
					R.layout.social_table_footer, null);

			footer.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext, ListActivity.class);
					intent.putExtra("listName", listName); // Optional
															// parameters
					mContext.startActivity(intent);
				}
			});
			addView(footer);
		}
	}

	public void setlistName(String name) {
		this.listName = name;
	}

	public void addRow(int position, String userName, int level) {
		mRows.add(new RowElement(position, userName, level));
	}

	/*
	 * public void updateUser(String userName, int level, int position) { for
	 * (RowElement elem : mRows) { if (elem.mUserName.equals("userName")) {
	 * elem.mLevel = level; elem.mPosition = position; return; } }
	 * 
	 * }
	 */

	/**
	 * Wrapper class containing all necessary information about a row in the
	 * list.
	 * 
	 */
	protected class RowElement implements Comparable<RowElement> {
		protected int mPosition;
		protected int mLevel;
		protected String mUserName;

		public RowElement(int position, String userName, int level) {
			mPosition = position;
			mLevel = level;
			mUserName = userName;
		}

		@Override
		public int compareTo(RowElement another) {
			return mPosition - another.mPosition;
		}
	}

}
