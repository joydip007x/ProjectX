package com.example.projectx;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Menu extends Fragment {

    private Button buttoncfu,buttoncad, buttonrap;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){



    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_menu,container,false);
    }



}

