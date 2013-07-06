package com.example.swp_ucd_2013_eule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.swp_ucd_2013_eule.view.Chart;

public class ChartFragment extends Fragment {

	private Chart mChart;
	private ToggleButton tg_btn_week;
	private ToggleButton tg_btn_month;
	private ToggleButton tg_btn_year;

	float[] ptsw, ptsm, ptsj, conw, conm, conj;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_chart, container,
				false);

		mChart = (Chart) rootView.findViewById(R.id.chart);

		// set DummyValues to create View
		mChart.setDrops(new float[] { 0, 1, 2, 3, 4 });
		mChart.setConsumption(new float[] { 0.0f, 1.0f, 2.0f, 3.0f, 4.0f });

		tg_btn_week = (ToggleButton) rootView.findViewById(R.id.btn_chart_week);
		tg_btn_month = (ToggleButton) rootView
				.findViewById(R.id.btn_chart_month);
		tg_btn_year = (ToggleButton) rootView.findViewById(R.id.btn_chart_year);

		((TextView) rootView.findViewById(R.id.txtbtm))
				.setVisibility(View.VISIBLE);
		((TextView) rootView.findViewById(R.id.txtPts))
				.setVisibility(View.INVISIBLE);
		((TextView) rootView.findViewById(R.id.txtChart_divider))
				.setVisibility(View.INVISIBLE);
		((TextView) rootView.findViewById(R.id.txtConsumption))
				.setVisibility(View.INVISIBLE);

		tg_btn_week.setOnCheckedChangeListener(changeChecker);
		tg_btn_month.setOnCheckedChangeListener(changeChecker);
		tg_btn_year.setOnCheckedChangeListener(changeChecker);

		ptsw = new float[] { 10, 11, 18, 28, 30, 31, 32 };
		conw = new float[] { 5.5f, 6.5f, 5.5f, 3.2f, 3.1f, 4.0f, 4.0f };

		ptsm = new float[] { 10, 11, 18, 28, 30, 31, 32, 40, 50, 70, 80, 90,
				100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 200, 210,
				210, 215, 215, 220, 230, 240, 260, 290, 300, 310, 320 };
		conm = new float[] { 6.5f, 6.4f, 6.5f, 6.3f, 6.3f, 6.2f, 6.2f, 6.3f,
				6.0f, 5.9f, 5.8f, 5.7f, 5.7f, 5.6f, 5.6f, 5.5f, 5.4f, 5.3f,
				5.3f, 5.3f, 5.2f, 5.1f, 5.3f, 5.4f, 5.4f, 5.3f, 5.2f, 4.9f,
				4.8f, 4.7f, 4.6f, 4.5f, 4.3f };

		ptsj = new float[] { 10, 75, 150, 250, 350, 500, 650 };
		conj = new float[] { 5.5f, 5.3f, 5.0f, 4.7f, 4.6f, 4.5f, 4.6f };

		return rootView;
	}

	OnCheckedChangeListener changeChecker = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (isChecked) {
				((TextView) getView().findViewById(R.id.txtbtm))
						.setVisibility(View.INVISIBLE);
				((TextView) getView().findViewById(R.id.txtPts))
						.setVisibility(View.VISIBLE);
				((TextView) getView().findViewById(R.id.txtChart_divider))
						.setVisibility(View.VISIBLE);
				((TextView) getView().findViewById(R.id.txtConsumption))
						.setVisibility(View.VISIBLE);
				if (buttonView == tg_btn_week) {
					tg_btn_month.setChecked(false);
					tg_btn_year.setChecked(false);
					mChart.setDrops(ptsw);
					mChart.setConsumption(conw);
					tg_btn_year.setEnabled(true);
					tg_btn_month.setEnabled(true);
					tg_btn_week.setEnabled(false);
				}
				if (buttonView == tg_btn_month) {
					tg_btn_week.setChecked(false);
					tg_btn_year.setChecked(false);
					mChart.setDrops(ptsm);
					mChart.setConsumption(conm);
					tg_btn_year.setEnabled(true);
					tg_btn_month.setEnabled(false);
					tg_btn_week.setEnabled(true);
				}
				if (buttonView == tg_btn_year) {
					tg_btn_week.setChecked(false);
					tg_btn_month.setChecked(false);
					mChart.setDrops(ptsj);
					mChart.setConsumption(conj);
					tg_btn_year.setEnabled(false);
					tg_btn_month.setEnabled(true);
					tg_btn_week.setEnabled(true);
				}
			}
		}
	};

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

}
