package com.example.projectx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.HashMap;


public class Profile extends Fragment {

    SNavigationDrawer sNavigationDrawer;
    Class fragmentClass;
    public static Fragment fragment;
    private TextView TVownerName,TVnumber,TVemail,TVdescription,TVaddress,adaTV;
    MaterialButton mbedit;
    static  ProfileModelClass prof;
    DatabaseReference db;
    ImageView maincov,addan;
    OnGetDataListener OG;
    private int LAUNCH_EDIT_ACTIVITY=100;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_profile, container, false);

        TVownerName = view.findViewById(R.id.TVaOwnerName);
        TVnumber = view.findViewById(R.id.TVaNumber);
        TVemail = view.findViewById(R.id.TVaEmail);
        TVdescription = view.findViewById(R.id.TVaDescription);
        TVaddress = view.findViewById(R.id.TVaAddress);
        maincov=view.findViewById(R.id.IVadp);
        addan=view.findViewById(R.id.IVadp2);
        adaTV=view.findViewById(R.id.TVaAgencyName);

        mbedit=view.findViewById(R.id.textView2);

        db= FirebaseDatabase.getInstance().getReference().child("Restaurant").child(UtilitiesX.UID).child("Profile");

        OG= new OnGetDataListener() {
            @Override
            public void onSuccess(String reg) {

                TVownerName.setText(prof.getOwnerName());
                TVnumber.setText(prof.getNumber());
                TVemail.setText(prof.getEmail());
                TVdescription.setText(prof.getDes());
                TVaddress.setText(prof.getAddress());
                adaTV.setText(prof.getResName());

                Glide.with(getContext())
                        .load(prof.getDP())
                        .into(maincov);
            }
            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        };
        readData(db,OG);
        mbedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), EditProfile.class);
                startActivityForResult(i, LAUNCH_EDIT_ACTIVITY);
                onActivityResult(1,1000,i);
            }
        });


        return view;
    }
    protected void OnViewCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    public void readData(DatabaseReference ref, final OnGetDataListener listener) {
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                prof=new ProfileModelClass((HashMap) snapshot.getValue());
                listener.onSuccess(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
}
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_EDIT_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){

            }
            if (resultCode == Activity.RESULT_CANCELED) {

                readData(FirebaseDatabase.getInstance().getReference("Restaurant").
                        child(UtilitiesX.UID).child("Profile"), OG);

            }
        }
    }
}