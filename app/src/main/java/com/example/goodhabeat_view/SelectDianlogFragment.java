package com.example.goodhabeat_view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

public class SelectDianlogFragment extends DialogFragment {
    String[] foods = {"김치찌개","김치볶음밥","김치말이국수","김치전"};
    String[] foodss = {"100","200","150","135.5"};
    RecyclerView plus_recyclerView;
    MenuSelectAdapter adapter;
    Button plus_btton;
    private View view;
    RadioButton fast_radio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.menu_plus_btn, container, false);

        plus_btton = view.findViewById(R.id.plus_btn);

        plus_recyclerView = view.findViewById(R.id.plus_recyclerView);
        plus_recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        fast_radio = (RadioButton) view.findViewById(R.id.fast_radio);

        adapter = new MenuSelectAdapter(this.getActivity(), foods,foodss);
        plus_recyclerView.setAdapter(adapter);

        plus_recyclerView.setVisibility(View.INVISIBLE);


        fast_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plus_recyclerView.setVisibility(View.VISIBLE);
            }
        });


        this.getDialog().setTitle("Food names");

        String TAG = "PurchaseConfirmationDialog";
        plus_btton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectDianlogFragment.this.dismiss();

            }
        });


        return view;

    }
}