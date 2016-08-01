package com.example.steve.nytarticlesearch.Activities;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.steve.nytarticlesearch.Models.editOptions;
import com.example.steve.nytarticlesearch.R;
import com.example.steve.nytarticlesearch.databinding.FragmentEditOptionBinding;


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

        //variables from settings
        String sortOrder = settings.getSortOrder();
        int year = settings.getYear();
        int month = settings.getMonth();
        int day = settings.getDay();
        boolean useBeginDate = settings.getUseBeginDate();
        boolean arts = settings.isArts();
        boolean fashionStyle = settings.isFashionStyle();
        boolean sports = settings.isSports();

        //set spinner
        initializeOrderSpinner(sortOrder);

        //set date picker for begin date & checkbox
        binding.dpBeginDate.updateDate(year, month, day);
        binding.cbUseBeginDate.setChecked(useBeginDate);

        //set newsdesk checkboxes
        if(arts)
        {
            binding.cbArts.setChecked(true);
        }
        if (fashionStyle)
        {
            binding.cbFashionStyle.setChecked(true);
        }
        if (sports)
        {
            binding.cbSports.setChecked(true);
        }

    }

    public void initializeOrderSpinner(String sortOrder)
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.sort_order_array, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.spOrderSpinner.setAdapter(adapter);

        if (sortOrder.equals(getString(R.string.oldest)))
        {
            int index = 0;
            for (int i = 0; i < adapter.getCount(); i++) {
                String spinnerItem = adapter.getItem(i).toString();
                if (spinnerItem.equals(getString(R.string.spinner_oldest))) {
                    index = i;
                    break; // terminate loop
                }
            }
            binding.spOrderSpinner.setSelection(index);
        }

        //set text color to white.
        binding.spOrderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int position, long id) {

                TextView tmpView = (TextView) binding.spOrderSpinner.getSelectedView().findViewById(android.R.id.text1);
                tmpView.setTextColor(Color.WHITE);
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

    }

    public void initBtnSaveOnClickListener() {
        //initialize object.
        settings = new editOptions();

        binding.btnSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //save all of our options
                    editOptions currentOption = getEverything();
                    onEditFinishedListener listener = (onEditFinishedListener) getActivity();
                    listener.onEditFinish(currentOption);
                    dismiss();
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
    //set all our variables in the settings menu
    private editOptions getEverything()
    {

        String spinnerItem = binding.spOrderSpinner.getSelectedItem().toString();

        //set sort order based on spinner
        String sortOrder;
        if (spinnerItem.equals(getString(R.string.spinner_newest)))
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

        boolean art = binding.cbArts.isChecked();
        boolean fashionStyle = binding.cbFashionStyle.isChecked();
        boolean sports = binding.cbSports.isChecked();

        //instantiate the constructor with params we used.
        editOptions currentSettings = new editOptions(art, fashionStyle, sports, sortOrder,year,month,day,useBeginDate,true);

        return currentSettings;
    }

    //interface for killing fragment.
    public interface onEditFinishedListener
    {
        void onEditFinish(editOptions editedSettings);
    }

}
