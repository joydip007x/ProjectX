package com.example.projectx;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class RiderRecycleAdapter extends RecyclerView.Adapter<RiderRecycleAdapter.RiderViewHolder> {


    private  ArrayList<RiderClassItem> riderlist=new ArrayList<>();

    public static  class RiderViewHolder extends RecyclerView.ViewHolder{

        public TextView ridername,riderphone;

        ImageView imagex,cross;
        public RiderViewHolder(@NonNull View itemView) {
            super(itemView);

            ridername=itemView.findViewById(R.id.rider_name);
            riderphone=itemView.findViewById(R.id.rider_phone);
            imagex=itemView.findViewById(R.id.rider_image);
            cross=itemView.findViewById(R.id.rider_delete);


        }


    }

    public RiderRecycleAdapter(ArrayList<RiderClassItem>Riders){
         this.riderlist=Riders;
    }


    @NonNull
    @Override
    public RiderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.rider_recycler_model,parent,false);
        RiderViewHolder riderViewHolder=new RiderViewHolder(v);

        return riderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RiderViewHolder holder, int position) {

        final RiderClassItem currentRider=riderlist.get(position);

         holder.cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new MaterialAlertDialogBuilder(v.getContext(), R.style.AlertDialogTheme)
                        .setTitle("Info")
                        .setMessage("Remove this Rider?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                for(RiderClassItem j:riderlist){

                                    if(j.getPhone().equals(currentRider.getPhone())){
                                        riderlist.remove(j);
                                        break;
                                    }
                                }
                                DatabaseReference db=FirebaseDatabase.getInstance().getReference().child("Restaurant").child(UtilitiesX.UID).child("Rider");
                                db.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        for(DataSnapshot ds:snapshot.getChildren()){

                                            System.out.println("LAAL "+ds.getValue());

                                            HashMap M= (HashMap) ds.getValue();
                                            if(M.get("Phone").equals(currentRider.getPhone())){

                                                FirebaseDatabase.getInstance().
                                                        getReference("Restaurant").
                                                        child(UtilitiesX.UID).child("Rider").child(M.get("riderUID").toString()).removeValue();
                                                break;
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                RiderManagement.Adapter.notifyDataSetChanged();

                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .show();


            }
        });
        holder.imagex.setImageResource(R.drawable.ic_baseline_assignment_ind_24);
        holder.ridername.setText(currentRider.getName());
        holder.riderphone.setText(currentRider.getPhone());

    }



    @Override
    public int getItemCount() {
        return this.riderlist.size();
    }
}
