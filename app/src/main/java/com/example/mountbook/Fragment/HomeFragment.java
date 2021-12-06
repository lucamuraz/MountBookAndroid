package com.example.mountbook.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.example.mountbook.Activity.ResultActivity;
import com.example.mountbook.R;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    Button buttonAll;
    Button submit;
    Spinner hostNr;
    EditText name;
    EditText date;
    List<String> hosts;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        buttonAll=rootView.findViewById(R.id.button_all);
        submit=rootView.findViewById(R.id.button_go);
        hostNr=rootView.findViewById(R.id.spinner2);
        name=rootView.findViewById(R.id.editTextName);
        date=rootView.findViewById(R.id.editTextDate);

        MaterialDatePicker.Builder<Pair<Long, Long>> materialDateBuilder = MaterialDatePicker.Builder
                .dateRangePicker();
        materialDateBuilder.setTitleText("Seleziona le date");
        materialDateBuilder.setTheme(R.style.MaterialCalendarTheme);

        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        date.setOnClickListener(
                v -> materialDatePicker.show(requireActivity().getSupportFragmentManager(),
                        "MATERIAL_DATE_PICKER"));

        materialDatePicker.addOnPositiveButtonClickListener(
                selection -> date.setText(materialDatePicker.getHeaderText()));

        hosts = new ArrayList<>(Arrays.asList("1 ospite","2 ospiti","3 ospiti","4 ospiti","5 ospiti",
                "6 ospiti","7 ospiti","8 ospiti","9 ospiti","10 ospiti"));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireActivity().getApplicationContext(), android.R.layout.simple_spinner_item, hosts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hostNr.setAdapter(adapter);

        buttonAll.setOnClickListener(view -> {
            Intent i = new Intent(rootView.getContext(), ResultActivity.class);
            startActivity(i);
            requireActivity().finish();
        });

        submit.setOnClickListener(view -> {
            //todo fare check info inserite e passarle
            Intent i = new Intent(rootView.getContext(), ResultActivity.class);
            startActivity(i);
            requireActivity().finish();
        });


        return rootView;

    }
}