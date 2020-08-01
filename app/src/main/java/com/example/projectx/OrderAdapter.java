package com.example.projectx;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.HashMap;
import java.util.List;

public class OrderAdapter extends BaseExpandableListAdapter {


    List<String> listgroup;
    HashMap<String, List<String> > listitem;
    Context context;
    String term;

    public OrderAdapter(List<String> listgroup, HashMap<String, List<String>> listitem, Context context) {
        this.listgroup = listgroup;
        this.listitem = listitem;
        this.context = context;
    }

    @Override
    public int getGroupCount() {

        if(Menu.listitem.size()==0)return 0;
        return listgroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        if(Menu.listitem.size()==0)return 0;
        return this.listitem.get(this.listgroup.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listgroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listitem.get(this.listgroup.get(groupPosition)).get(childPosition);
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
            convertView= layoutInflater.inflate(R.layout.list_group_order,null);
        }

        TextView textView=convertView.findViewById(R.id.list_parent_order);

        textView.setText(group);

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String basic= (String) getChild(groupPosition,childPosition),child=null;
        String child2=  basic.substring(basic.indexOf("|")+1).trim();

        child=basic.substring(0, basic.indexOf("|"));
        final String postUID=child2.split("[|]")[1];

        //System.out.println("LAAL "+postUID);
        child2=child2.split("[|]")[0];

        if(convertView==null){

            LayoutInflater layoutInflater=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= layoutInflater.inflate(R.layout.list_item_order,null);
        }

        TextView textView=convertView.findViewById(R.id.list_chlid_order);
        TextView textView2=convertView.findViewById(R.id.list_chlid2_order);
        MaterialButton mbtn=convertView.findViewById(R.id.li_cart_order2);
        textView.setText(child);
        textView2.setText(child2);

        ImageButton i=convertView.findViewById(R.id.li_cart_order);

        if(groupPosition==0){
            i.setVisibility(View.INVISIBLE);
            mbtn.setVisibility(View.VISIBLE);
        }
        if(groupPosition==2){
            i.setImageDrawable(convertView.getResources().getDrawable(R.drawable.ic_baseline_check_24_comp));
            mbtn.setVisibility(View.GONE);
        }
        if(groupPosition==1) {
            i.setImageDrawable(convertView.getResources().getDrawable(R.drawable.ic_baseline_add_to_queue_24));
            mbtn.setVisibility(View.GONE);
        }
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(v.getContext(),EmptyDemo.class);
                i.putExtra("postUID",postUID);
                context.startActivity(i);
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }

}
