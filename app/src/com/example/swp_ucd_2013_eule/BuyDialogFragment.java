package com.example.swp_ucd_2013_eule;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class BuyDialogFragment extends DialogFragment {

    private EditText mEditText;

    public BuyDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buy_dialog, container);
        getDialog().setTitle("Buy Item");

        return view;
    }
    
    public static BuyDialogFragment newInstance() {
        BuyDialogFragment dialog = new BuyDialogFragment();
        Bundle bundle = new Bundle();
        dialog.setArguments(bundle);
        return dialog;
    }

}
