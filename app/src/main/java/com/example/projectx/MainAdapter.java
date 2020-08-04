package com.example.projectx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static com.example.projectx.Menu.initListData;
import static com.example.projectx.Menu.listgroup;
import static com.example.projectx.Menu.listitem;

public class MainAdapter extends BaseExpandableListAdapter {


    Context context;
    String term;

    public MainAdapter(Context context) {

        this.context = context;
    }

    @Override
    public int getGroupCount() {

        if(Menu.listitem.size()==0)return 0;
        return listgroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        if(Menu.listitem.size()==0 || Menu.listgroup.size()==0 )return 0;
        return listitem.get(listgroup.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listgroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listitem.get(listgroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {

        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String group= (String) getGroup(groupPosition);
        if(convertView==null){

            LayoutInflater layoutInflater=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= layoutInflater.inflate(R.layout.list_group,null);
        }

        TextView textView=convertView.findViewById(R.id.list_parent);

        textView.setText(group);

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


        String child= (String) getChild(groupPosition,childPosition);
        if(child.equals("null")) return convertView;
        String child2=  child.substring(child.indexOf("|")+1).trim().concat(" BDT ");
        child=child.substring(0, child.indexOf("|"));

        if(convertView==null){

            LayoutInflater layoutInflater=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= layoutInflater.inflate(R.layout.list_item,null);

        }

        TextView textView=convertView.findViewById(R.id.list_chlid);
        TextView textView2=convertView.findViewById(R.id.list_chlid2);
        textView.setText(child);
        textView2.setText(child2);

        final ImageButton ibutton=convertView.findViewById(R.id.li_cart);

        ibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                term= (String) getChild(groupPosition,childPosition);
                System.out.println(" LAAL TERM "+term);
                readData(FirebaseDatabase.getInstance().
                        getReference("Restaurant").
                        child(UtilitiesX.UID).child("FoodMenu"), new OnGetDataListener() {
                    @Override
                    public void onSuccess(String reg) {
                        initListData(1,reg);
                    }
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    public void readData(DatabaseReference ref, final OnGetDataListener listener) {
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                String toremove = null;
                String reg= null;
                for( DataSnapshot dataSnapshot: snapshot.getChildren() ){

                    SingleFoodMenuItem singleFoodMenuItem= new SingleFoodMenuItem((HashMap) dataSnapshot.getValue());
                    if(singleFoodMenuItem.getName().equals(term)){
                           toremove=singleFoodMenuItem.getUID();
                           reg=singleFoodMenuItem.getName();
                           break;
                    }
                }
               if(toremove!=null) FirebaseDatabase.getInstance().
                        getReference("Restaurant").
                        child(UtilitiesX.UID).child("FoodMenu").child(toremove).removeValue();
                listener.onSuccess(reg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}

