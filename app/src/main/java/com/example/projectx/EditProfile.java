package com.example.projectx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.projectx.Profile.prof;
import static com.example.projectx.UtilitiesX.DP;

public class EditProfile extends AppCompatActivity {

    private TextView TVownerName,TVnumber,TVemail,TVdescription,TVaddress,adaTV;
    ImageView maincov,addan;
    MaterialButton mbt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        TVownerName = findViewById(R.id.TVaOwnerNamex);
        TVnumber = findViewById(R.id.TVaNumberx);
        TVemail = findViewById(R.id.TVaEmailx);
        TVdescription = findViewById(R.id.TVaDescriptionx);
        TVaddress = findViewById(R.id.TVaAddressx);
        maincov=findViewById(R.id.IVadpx);
        addan=findViewById(R.id.IVadp2x);
        adaTV=findViewById(R.id.TVaAgencyNamex);

        mbt=findViewById(R.id.textView2x);


        TVownerName.setText(prof.getOwnerName());
        TVnumber.setText(prof.getNumber());
        TVemail.setText(prof.getEmail());
        TVdescription.setText(prof.getDes());
        TVaddress.setText(prof.getAddress());
        adaTV.setText(prof.getResName());

        mbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference d= FirebaseDatabase.getInstance().getReference().child("Restaurant")
                        .child(UtilitiesX.UID).child("Profile");
                d.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {


                        ProfileModelClass p=new ProfileModelClass();
                        p.setAddress(TVaddress.getText().toString());
                        p.setNumber(TVnumber.getText().toString());
                        p.setOwnerName(TVownerName.getText().toString());
                        p.setDes(TVdescription.getText().toString());
                        p.setEmail(TVemail.getText().toString());
                        p.setDP(DP);
                        p.setResName(adaTV.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("Restaurant")
                                .child(UtilitiesX.UID).child("Profile").setValue(p.getMyprf());

                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_CANCELED, returnIntent);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
            }
        });




    }
}