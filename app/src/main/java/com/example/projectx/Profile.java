package com.example.projectx;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;


public class Profile extends Fragment {

    SNavigationDrawer sNavigationDrawer;
    Class fragmentClass;
    public static Fragment fragment;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_profile, container, false);
        return view;
    }
    protected void OnViewCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}