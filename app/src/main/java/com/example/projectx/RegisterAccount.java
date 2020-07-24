package com.example.projectx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class RegisterAccount extends AppCompatActivity {


    Button reg;
    TextInputEditText username,pass,phone;
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

        progressBar.setVisibility(View.INVISIBLE);
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
                    Utilities.showError("Password length must be 8 character long ",RegisterAccount.this);
                    return;
                }
                if( !Utilities.isPhoneValid(tele) || tele.length()>11 ){
                    Utilities.showError("Incorrect Phone info",RegisterAccount.this);
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

                    Toasty.success(RegisterAccount.this, "Registered!", Toast.LENGTH_SHORT, true).show();
                    Intent i=new Intent(  RegisterAccount.this, MainActivity1.class);
                    i=Utilities.CloseAllPreviousCallStack(i);
                    startActivity(i);
                }
                else {
                    Toasty.error(RegisterAccount.this, "Error:"+task.getException().getMessage(), Toast.LENGTH_SHORT, true).show();
                }
            }
        });
    }
}