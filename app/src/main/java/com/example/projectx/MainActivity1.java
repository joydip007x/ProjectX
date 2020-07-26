package com.example.projectx;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class MainActivity1 extends AppCompatActivity {


    Button log,reg,forgot;
    TextInputEditText username,pass;
    FirebaseAuth auth;
    String email,password;
    static String UID="";

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main1);

        log=findViewById(R.id.ma_sign);
        reg=findViewById(R.id.ma_reg);
        username=findViewById(R.id.ma_username);
        pass=findViewById(R.id.ma_pass);
        progressBar=findViewById(R.id.progressBar);
        forgot=findViewById(R.id.ma_forgot);

        auth=FirebaseAuth.getInstance();
        progressBar.setVisibility(View.INVISIBLE);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 email=username.getText().toString().trim();
                 password=pass.getText().toString().trim();

                if(!Utilities.isEmailValid(email)){

                    username.setText(" ");
                    username.setError("Invalid mail");
                    return;
                }
                if(password.length()<8){
                    Utilities.showError("Password length must be 8 character long ",MainActivity1.this);
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                signInUser();
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i=new Intent(  MainActivity1.this, RegisterAccount.class);
                // i=Utilities.CloseAllPreviousCallStack(i);
                 startActivity(i);
             }
         });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),ForgotPass.class));
            }
        });



    }

    private void signInUser() {


        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    Toasty.success(MainActivity1.this, "Logged in !", Toast.LENGTH_SHORT, true).show();
                    UID=FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Intent i=new Intent(  MainActivity1.this, NavigationHolder.class);
                    i=Utilities.CloseAllPreviousCallStack(i);
                    startActivity(i);


                }
                else {
                    Toasty.error(MainActivity1.this, "Error:"+task.getException().getMessage(), Toast.LENGTH_SHORT, true).show();
                }
            }
        });

    }



}