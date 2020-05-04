package com.tfg.inmobiliariatfg.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tfg.inmobiliariatfg.R;
import com.tfg.inmobiliariatfg.activities.LoginActivity;


public class ErrorFragment extends Fragment {
    Button btnLoginError;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ConstraintLayout cl = (ConstraintLayout) inflater.inflate(R.layout.fragment_error, container, false);
        btnLoginError = cl.findViewById(R.id.btnLoginError);
        btnLoginError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (getActivity(), LoginActivity.class);
                startActivity(i);
            }
        });
        return cl;
    }
}
