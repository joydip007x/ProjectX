package com.example.projectx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class ForgotPass extends AppCompatActivity {


    TextInputEditText username;
    Button submit;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        username=findViewById(R.id.fp_email);
        submit=findViewById(R.id.fp_submit);
        auth=FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=username.getText().toString().trim();

                if(!Utilities.isEmailValid(email)){
                    username.setText(" ");
                    username.setError("Invalid mail");
                    return;
                }
                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        if(task.isSuccessful()){

                            Toasty.success(ForgotPass.this, "Password Reset EMail Sent!", Toast.LENGTH_SHORT, true).show();

                            startActivity(new Intent(getApplicationContext(),MainActivity1.class));
                        }
                        else {
                            Toasty.error(ForgotPass.this, "Error:"+task.getException().getMessage(), Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
            }
        });

    }
}