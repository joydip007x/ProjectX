package com.example.projectx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddItemToCart extends AppCompatActivity {


    Button log;
    TextInputEditText foodname,price,catergory,desc;
    SingleFoodMenuItem fdb;
    String postUID;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_itemtocart);

        foodname=findViewById(R.id.aitc_name);
        price=findViewById(R.id.aitc_price);
        log=findViewById(R.id.aitc_sign);
        catergory=findViewById(R.id.aitc_cat);
        desc=findViewById(R.id.aitc_catdesc);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 String food=foodname.getText().toString();
                 String pri= (price.getText().toString());
                 String cat=catergory.getText().toString();
                 String desc2=desc.getText().toString();
                 postUID=FirebaseDatabase.getInstance().getReference("Restaurant").child(UtilitiesX.UID)
                        .child("FoodMenu")
                        .push().getKey().toString();

                 fdb=new SingleFoodMenuItem();
                 fdb.addSingleItem("Food",food);
                 fdb.addSingleItem("Price",pri);
                 fdb.addSingleItem("Category",cat);
                 fdb.addSingleItem("postUID",postUID);
                 fdb.addSingleItem("Desc",desc2);
                 
                databaseReference= FirebaseDatabase.getInstance().getReference().child("Restaurant").child(UtilitiesX.UID).child("FoodMenu").child(postUID);
                databaseReference.setValue(fdb.getMenucard());

                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();

            }
        });




    }
}
