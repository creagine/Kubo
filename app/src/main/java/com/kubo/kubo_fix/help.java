package com.kubo.kubo_fix;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class help extends Fragment {


    public help() {
        // Required empty public constructor
    }

    RelativeLayout view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = (RelativeLayout) inflater.inflate( R.layout.fragment_help, container, false );

        getActivity().setTitle("Help");
        Log.e("Help", "Help");

        return view ;
    }

    }

