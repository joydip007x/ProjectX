package com.example.projectx;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class RegisterAccount extends AppCompatActivity {


    Button reg;
    TextInputEditText username,pass,phone;
    TextInputLayout f1;
    FirebaseAuth auth;
    String email,password,tele;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        reg=findViewById(R.id.ra_submit);
        username=findViewById(R.id.ra_username);
        pass=findViewById(R.id.ra_pass);
        progressBar=findViewById(R.id.progressBar2);
        auth=FirebaseAuth.getInstance();
        phone=findViewById(R.id.ra_phonereg);
        f1=findViewById(R.id.random1);

        progressBar.setVisibility(View.INVISIBLE);
        f1.setBoxStrokeColor(Color.WHITE);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email=username.getText().toString().trim();
                password=pass.getText().toString().trim();
                tele=phone.getText().toString().trim();
                if( !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                    username.setText(" ");
                    username.setError("Invalid mail");
                    return;
                }
                if(password.length()<8){
                    UtilitiesX.showError("Password length must be 8 character long ",RegisterAccount.this);
                    return;
                }
                if( !UtilitiesX.isPhoneValid(tele) || tele.length()>11 ){
                    UtilitiesX.showError("Incorrect Phone info",RegisterAccount.this);
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                registerNewUser();
            }
        });


    }

    private void registerNewUser() {

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

               ////     System.out.println("LAAL CREATE "+ FirebaseAuth.getInstance().getUid());
                    Toasty.success(RegisterAccount.this, "Registered!", Toast.LENGTH_SHORT, true).show();
                    Intent i=new Intent(  RegisterAccount.this, MainActivity1.class);
                    i= UtilitiesX.CloseAllPreviousCallStack(i);
                    FirebaseAuth.getInstance().signOut();
                    startActivity(i);
                }
                else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toasty.error(RegisterAccount.this, "Error:"+task.getException().getMessage(), Toast.LENGTH_SHORT, true).show();
                }
            }
        });
    }
}