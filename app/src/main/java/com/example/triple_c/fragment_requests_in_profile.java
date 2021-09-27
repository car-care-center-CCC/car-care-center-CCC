package com.example.triple_c;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_requests_in_profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_requests_in_profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "name";
    private static final String ARG_PARAM2 = "car";

    // TODO: Rename and change types of parameters
    private String mName;
    private String mCar;

    public fragment_requests_in_profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param mName Parameter 1.
     * @param mCar Parameter 2.
     * @return A new instance of fragment fragment_requests_in_profile.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_requests_in_profile newInstance(String mName, String mCar) {
        fragment_requests_in_profile fragment = new fragment_requests_in_profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, mName);
        args.putString(ARG_PARAM2, mCar);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString(ARG_PARAM1);
            mCar = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_requests_in_profile, container, false);
    }
}