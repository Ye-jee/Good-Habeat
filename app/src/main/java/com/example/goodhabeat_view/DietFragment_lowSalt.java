package com.example.goodhabeat_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class DietFragment_lowSalt extends Fragment {

    TextView tv_example;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diet_low_salt, container, false);

        /*tv_example = (TextView) view.findViewById(R.id.tv_lowSalt_ex);

        tv_example.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "저염 식단 목록", Toast.LENGTH_SHORT).show();
            }
        });*/

        return view;
    }
}