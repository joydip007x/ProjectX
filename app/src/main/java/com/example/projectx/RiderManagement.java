package com.example.projectx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class RiderManagement extends Fragment {

    static ArrayList<RiderClassItem> Riders;
    private DatabaseReference db;
    private RecyclerView recyclerView;
    static  RecyclerView.Adapter Adapter;
    private RecyclerView.LayoutManager layoutManager;

    private Button newRider;
    private AlertDialog alertDialog;
    private int LAUNCH_RIDER_ACTIVITY=2;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_rider_management, container, false);

        newRider=v.findViewById(R.id.rider_register_btn);
        recyclerView=v.findViewById(R.id.rider_recycle);

       // alertDialog=startLoading3(v,alertDialog);
        newRider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), RiderRegister.class);
                startActivityForResult(i, LAUNCH_RIDER_ACTIVITY);
                onActivityResult(1,100,i);
            }
        });

       /// System.out.println("LAAL "+UtilitiesX.UID);
        db= FirebaseDatabase.getInstance().getReference("Restaurant").child(UtilitiesX.UID).child("Rider");
        readData(db, new OnGetDataListener() {

            @Override
            public void onSuccess(String reg) {

              //  alertDialog.dismiss();
                InitRecycleView();

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });




        return v;
    }

    private void InitRecycleView() {

        // recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        Adapter=new RiderRecycleAdapter(Riders);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(Adapter);
    }

    public void readData(DatabaseReference ref, final OnGetDataListener listener) {
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Riders = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    HashMap M = new HashMap();
                    M = (HashMap) dataSnapshot.getValue();
                    Riders.add(new RiderClassItem(M.get("Rider").toString(), M.get("Phone").toString()));
                }
                listener.onSuccess(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_RIDER_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Adapter.notifyDataSetChanged();
            }
        }
    }}

