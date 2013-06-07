package com.example.swp_ucd_2013_eule.view;

import java.util.concurrent.ConcurrentSkipListSet;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.swp_ucd_2013_eule.R;

public class SocialList extends TableLayout {

	private ConcurrentSkipListSet<RowElement> mRows = new ConcurrentSkipListSet<RowElement>();
	private LayoutInflater mInflater;
	private Context mContext;

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

	public void drawList() {
		removeAllViews();
		TableRow header = (TableRow) mInflater.inflate(
				R.layout.social_table_header, null);
		addView(header, 0);

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
			level.setText(String.valueOf(elem.mLevel));
			// set Position
			int playerPosition = elem.mPosition;
			TextView position = (TextView) friendRow
					.findViewById(R.id.social_friend_position);
			position.setText(String.valueOf(playerPosition));
			
			friendRow.addRowElement(elem);
			addView(friendRow, playerPosition);

		}

	}

	public void addRow(int position, String userName, int level) {
		mRows.add(new RowElement(position, userName, level));
	}


	public void updateUser(String userName, int level, int position) {
		for (RowElement elem : mRows) {
			if (elem.mUserName.equals("userName")) {
				elem.mLevel = level;
				elem.mPosition = position;
				return;
			}
		}

	}

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
