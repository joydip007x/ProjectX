package com.example.projectx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class RiderRegister extends AppCompatActivity {


    Button log;
    TextInputEditText ridername,phone ;

    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rider_register);

        ridername=findViewById(R.id.rr_name);
        phone=findViewById(R.id.rr_price);
        log=findViewById(R.id.rr_sign);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String riderNamez=ridername.getText().toString();
                String tele= phone.getText().toString();
                String postUID=FirebaseDatabase.getInstance().getReference("Restaurant").child(UtilitiesX.UID)
                        .child("Rider")
                        .push().getKey().toString();

                 HashMap M=new HashMap();
                 M.put("Rider", riderNamez);M.put("Phone",tele);M.put("STATUS","unlocked");
                 M.put("riderUID",postUID);
                databaseReference= FirebaseDatabase.getInstance().getReference().child("Restaurant").child(UtilitiesX.UID).child("Rider").child(postUID);
                databaseReference.setValue(M);
                Toasty.info(RiderRegister.this, "Registered !", Toast.LENGTH_SHORT, true).show();

                RiderClassItem RCI=new RiderClassItem(M.get("Rider").toString(),M.get("Phone").toString());
                RiderManagement.Riders.add(RCI);
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();

                RiderRegister.super.onBackPressed();


            }
        });




    }
}
