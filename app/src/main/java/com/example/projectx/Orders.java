package com.example.projectx;


import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Orders extends Fragment {

    private Button buttoncfu,buttoncad, buttonrap;
    ExpandableListView expandableListView;
     List<String>listgroup,L0,L2,L1;
    ExtendedFloatingActionButton efab;
    HashMap<String, List<String> > listitem;
    ArrayList<Pair<String,String>>Store;
    Set<String> CatName;




    int LAUNCH_SECOND_ACTIVITY = 1;

    static OrderAdapter orderAdapter;
    AlertDialog alertDialog;
    DatabaseReference db;
    OrderReceiver orderReceiver;


    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.activity_order,container,false);

        expandableListView=v.findViewById(R.id.expandable_listvieworder);

       // alertDialog=startLoading2(v,alertDialog);

        listgroup=new ArrayList<>();
        listgroup.add("Awaiting");
        listgroup.add("Received");
        listgroup.add("Completed");

        listitem=new HashMap<>();
        Store=new ArrayList<>();
        CatName=new TreeSet<>();

        db=FirebaseDatabase.getInstance().getReference("Restaurant").child(UtilitiesX.UID).child("Orders");
        orderAdapter = new OrderAdapter(listgroup,listitem,getContext());
        expandableListView.setAdapter(orderAdapter);


        readData(db, new OnGetDataListener() {

            @Override
            public void onSuccess(String reg) {
                //alertDialog.dismiss();
                initListData(0, reg);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        });
        return  v;
    }


    public void readData(DatabaseReference ref, final OnGetDataListener listener) {
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                L0=new ArrayList<>(); L1=new ArrayList<>();L2=new ArrayList<>();
                for( DataSnapshot dataSnapshot: snapshot.getChildren() ){

                    orderReceiver= new OrderReceiver((HashMap) dataSnapshot.getValue());

                    if(orderReceiver.getSize()!=0) {

                          if(orderReceiver.orderType()==0){
                               L0.add(orderReceiver.titleDisplay());
                          }
                         else  if(orderReceiver.orderType()==1){
                               L1.add(orderReceiver.titleDisplay());
                          }
                         else L2.add(orderReceiver.titleDisplay());

                    }
                }
                listener.onSuccess(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
     void initListData(int p, String reg) {


        listitem.put(listgroup.get(0),L0);
        listitem.put(listgroup.get(1),L1);
        listitem.put(listgroup.get(2),L2);

        orderAdapter.notifyDataSetChanged();
    }

}

