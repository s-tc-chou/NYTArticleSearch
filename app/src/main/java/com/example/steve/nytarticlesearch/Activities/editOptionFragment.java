package com.example.steve.nytarticlesearch.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.steve.nytarticlesearch.Models.editOptions;
import com.example.steve.nytarticlesearch.R;
import com.example.steve.nytarticlesearch.databinding.FragmentEditOptionBinding;

import java.util.Calendar;


//utilize databinding for fragment view options.

public class editOptionFragment extends DialogFragment {

    //    private OnFragmentInteractionListener mListener;
    private FragmentEditOptionBinding binding;
    private editOptions settings;

    // Required empty public constructor
    public editOptionFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_option, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeData();
        initBtnSaveOnClickListener();
        initBtnCancelOnClickListener();
    }

    //Initialize all setting datas, read from search activity to keep state.
    public void initializeData() {
        SearchActivity myActivity = (SearchActivity) getActivity();
        settings = myActivity.getCurrentSettings();

        //set all the fields.
        //check if null?

        //variables from settings
        String filterText = settings.getFilter();
        String sortOrder = settings.getSortOrder();
        int year = settings.getYear();
        int month = settings.getMonth();
        int day = settings.getDay();
        Boolean useBeginDate = settings.getUseBeginDate();


        //set filtertext, check for null.
        if (filterText == null) {
            binding.etFilterText.setText("");
        } else {
            binding.etFilterText.setText(filterText);
        }

        //set sort order radio button.
        if (sortOrder.equals(getString(R.string.oldest))) {
            binding.rbOldest.setChecked(true);
            binding.rbNewest.setChecked(false);
        } else {
            binding.rbOldest.setChecked(false);
            binding.rbNewest.setChecked(true);
        }

        //set date picker for begin date & checkbox
        binding.dpBeginDate.updateDate(year, month, day);
        binding.cbUseBeginDate.setChecked(useBeginDate);

    }

    public void initBtnSaveOnClickListener() {

        //initialize object.
        settings = new editOptions();

        binding.btnSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filter = binding.etFilterText.getText().toString();
                if (filter.equals(null) || filter.equals(""))
                {
                    filterAlert();
                }
                else {
                    //save all of our options
                    editOptions currentOption = getEverything();
                    onEditFinishedListener listener = (onEditFinishedListener) getActivity();
                    listener.onEditFinish(currentOption);
                    dismiss();
                }

            }
        });
    }


    public void initBtnCancelOnClickListener() {
        binding.btnCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    //Helper functions -------------------------

    //
    public void filterAlert()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Filter cannot be blank!");
        builder1.setCancelable(true);

        builder1.setNeutralButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    //set all our variables in the settings menu
    private editOptions getEverything()
    {

        String filterText = binding.etFilterText.getText().toString();

        //set sortOrder based on radio button
        String sortOrder;
        if (binding.rbNewest.isChecked())
        {
            sortOrder = getString(R.string.newest);
        }
        else
        {
            sortOrder = getString(R.string.oldest);
        }

        int year = binding.dpBeginDate.getYear();
        int month = binding.dpBeginDate.getMonth();
        int day = binding.dpBeginDate.getDayOfMonth();
        Boolean useBeginDate = binding.cbUseBeginDate.isChecked();

        //instantiate the constructor with params we used.
        editOptions currentSettings = new editOptions(filterText,sortOrder,year,month,day,useBeginDate);

        return currentSettings;
    }

    //interface for killing fragment.
    public interface onEditFinishedListener
    {
        void onEditFinish(editOptions editedSettings);
    }

}
