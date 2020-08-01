package com.example.projectx;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class UtilitiesX {

    static String UID,DP="https://firebasestorage.googleapis.com/v0/b/projectx-a026c.appspot.com/o/Restaurant%20Profile%20pictures%2FXyHd5RK0pJejWs00a5kiz51BVaf1.jpg?alt=media&token=3ae26079-80f1-4a1f-b61c-283c25a46dda";

    static void showError(String Err, Context context){

        new MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme)
                .setTitle("Info")
                .setMessage(Err)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setNeutralButton("Skip", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }

    static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
     static boolean isPhoneValid(String s)
    {

        Pattern p = Pattern.compile("(01)?[0-9]{9}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

    static Intent CloseAllPreviousCallStack(Intent intent){
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }


    static AlertDialog startLoading(View v, AlertDialog alertDialog) {

        alertDialog = new AlertDialog.Builder(v.getContext()).create();
        alertDialog.setCancelable(false);
        LayoutInflater factory = LayoutInflater.from(v.getContext());
        final View view = factory.inflate(R.layout.custom_load_dialog, null);
        alertDialog.setView(view);
        alertDialog.show();
        return alertDialog;
    }
    static AlertDialog startLoading2(View v, AlertDialog alertDialog) {

        alertDialog = new AlertDialog.Builder(v.getContext()).create();
        alertDialog.setCancelable(false);
        LayoutInflater factory = LayoutInflater.from(v.getContext());
        final View view = factory.inflate(R.layout.custom_load_dialog2, null);
        alertDialog.setView(view);
        alertDialog.show();
        return alertDialog;
    }
    static AlertDialog startLoading3(View v, AlertDialog alertDialog) {

        alertDialog = new AlertDialog.Builder(v.getContext()).create();
        alertDialog.setCancelable(false);
        LayoutInflater factory = LayoutInflater.from(v.getContext());
        final View view = factory.inflate(R.layout.custom_load_dialog3, null);
        alertDialog.setView(view);
        alertDialog.show();
        return alertDialog;
    }
    static  void CheckProfile(){

        DatabaseReference d=FirebaseDatabase.getInstance().getReference().child("Restaurant")
                .child(UtilitiesX.UID).child("Profile");

        d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if(snapshot.exists()) return;
                ProfileModelClass p=new ProfileModelClass();
                p.setAddress("N/A");
                p.setNumber("N/A");
                p.setOwnerName("N/A");
                p.setDes("N/A");
                p.setTime("N/A");
                p.setDP(DP);
                p.setResName("Scrolldown to Edit details");
                FirebaseDatabase.getInstance().getReference().child("Restaurant")
                        .child(UtilitiesX.UID).child("Profile").setValue(p.getMyprf());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}

