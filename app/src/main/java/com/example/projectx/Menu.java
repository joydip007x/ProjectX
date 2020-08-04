package com.example.projectx;


import android.app.Activity;
import android.content.Intent;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Menu extends Fragment {

    private Button buttoncfu,buttoncad, buttonrap;
    ExpandableListView expandableListView;
    static List<String>listgroup;
    ExtendedFloatingActionButton efab;
    static HashMap<String, List<String> > listitem;
    static ArrayList<Pair<String,String>>Store;
    static Set<String> CatName;


    int LAUNCH_SECOND_ACTIVITY = 1;

    static MainAdapter mainAdapter;
    AlertDialog alertDialog;
    DatabaseReference db;
    SingleFoodMenuItem singleFoodMenuItem;




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){


    }



    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_menu,container,false);

        expandableListView=v.findViewById(R.id.expandable_listview);
        efab=v.findViewById(R.id.extended_fab);



        listgroup=new ArrayList<>();
        listitem=new HashMap<>();
        Store=new ArrayList<>();
        CatName=new TreeSet<>();

        db=FirebaseDatabase.getInstance().getReference("Restaurant").child(UtilitiesX.UID).child("FoodMenu");

        efab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), AddItemToCart.class);
                startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
                onActivityResult(1,100,i);
            }
        });
        mainAdapter= new MainAdapter(getContext());
        expandableListView.setAdapter(mainAdapter);


        final OnGetDataListener OGD=new OnGetDataListener() {
            @Override
            public void onSuccess(String reg) {
                initListData(0, reg);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure() {

            }
        };
        readData(db,OGD);


        return  v;
    }



    public void readData(DatabaseReference ref, final OnGetDataListener listener) {
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                CatName.clear();
                Store.clear();
                for( DataSnapshot dataSnapshot: snapshot.getChildren() ){


                    singleFoodMenuItem= new SingleFoodMenuItem((HashMap) dataSnapshot.getValue());

                   if(singleFoodMenuItem.getSize()!=0) {
                       String a = singleFoodMenuItem.getCat();
                       String b = singleFoodMenuItem.getName();
                       CatName.add(a);
                       Store.add(new Pair<>(a, b));
                   }
//                    Log.println(Log.DEBUG, "LAAL", b);
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

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){

            }
            if (resultCode == Activity.RESULT_CANCELED) {

                readData(FirebaseDatabase.getInstance().getReference("Restaurant").
                        child(UtilitiesX.UID).child("FoodMenu"), new OnGetDataListener() {

                    @Override
                    public void onSuccess(String reg) {
                        initListData(0, reg);
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure() {

                    }
                });

            }
        }
    }
    static void initListData(int p, String reg) {

            if(p==1){

                Iterator hmIterator = listitem.entrySet().iterator();
                ArrayList<String>L= new ArrayList<>() ;
                String key=null;
                while (hmIterator.hasNext()) {

                    Map.Entry mapElement = (Map.Entry)hmIterator.next();
                    ArrayList<String> marks = (ArrayList<String>) mapElement.getValue();
                    if(marks.contains(reg)){
                         key=mapElement.getKey().toString();
                         marks.remove(reg);
                         L=marks;
                         hmIterator.remove();
                         break;
                    }
                }
                if(L.size()!=0) listitem.put(key,L);
                else  listgroup.remove(key);
                if(listitem.size()==0 || listgroup.size()==0 ){listgroup.clear(); listgroup.clear();}
                mainAdapter.notifyDataSetChanged();
                return;
            }
            if(CatName.size()==0)return;
            listgroup.clear();
            listitem.clear();
            for(String i:CatName){

               ArrayList<String >L=new ArrayList<>();
               for( Pair<String,String>P : Store){
                   if(P.first.equals(i)) L.add(P.second);
               }
               listitem.put(i,L);
               listgroup.add(i);
            }
        ///    System.out.println("LAAL"+listitem);
            mainAdapter.notifyDataSetChanged();

    }
    

}

