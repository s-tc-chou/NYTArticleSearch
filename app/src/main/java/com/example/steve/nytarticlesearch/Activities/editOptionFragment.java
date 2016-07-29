package com.example.steve.nytarticlesearch.Activities;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.steve.nytarticlesearch.R;
import com.example.steve.nytarticlesearch.databinding.FragmentEditOptionBinding;

import java.util.Calendar;


//utilize databinding for fragment view options.

public class editOptionFragment extends DialogFragment {

//    private OnFragmentInteractionListener mListener;
    private FragmentEditOptionBinding binding;

    // Required empty public constructor
    public editOptionFragment() {}

    public static editOptionFragment newInstance(String param1, String param2) {
        editOptionFragment fragment = new editOptionFragment();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_option, container, false);
        View view = binding.getRoot();


        // Inflate the layout for this fragment
        //initBtnSaveOnClickListener();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    public void initBtnSaveOnClickListener(View v)
    {

    }

    public void checkRadioButtonState()
    {

    }

    /*    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    //initialize date picker dialog and set to current date
    /*public void initDatePickerDialog(View v)
    {
        DatePicker dateFilter = (DatePicker)v.findViewById(R.id.datePicker);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        dateFilter.init(year,month,day,null);
    }*/


}
