package com.example.covidbedavailability;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.util.Patterns;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


import android.os.Bundle;
import android.view.View;



    public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

        private TextView registerUser;
        private EditText nameUser,phoneUser,emailUser,pwdUser;
        //private Button btnSignIn;
        private ProgressBar progressBar;

        private FirebaseAuth mAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register_user2);

            mAuth=FirebaseAuth.getInstance();

            registerUser=(Button)findViewById(R.id.btnSignIn);
            registerUser.setOnClickListener(this);

            nameUser=(EditText)findViewById(R.id.name);
            phoneUser=(EditText)findViewById(R.id.phone);
            emailUser=(EditText)findViewById(R.id.email);
            pwdUser=(EditText)findViewById(R.id.pwd);
            progressBar=(ProgressBar) findViewById(R.id.progressBar);


        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnSignIn) {
                registerUser();
            }
        }

        private void registerUser(){

            String name=nameUser.getText().toString().trim();
            String phone=phoneUser.getText().toString().trim();
            String email=emailUser.getText().toString().trim();
            String password=pwdUser.getText().toString().trim();
//
            if(name.isEmpty()){
                nameUser.setError("Name is required");
                nameUser.requestFocus();
                return;
            }
            if(phone.isEmpty()){
                phoneUser.setError("Phone number is required");
                phoneUser.requestFocus();
                return;
            }
            if(phone.length()!=10){
                phoneUser.setError("Phone number length should be 10 numbers!");
                phoneUser.requestFocus();
                return;
            }
            if(email.isEmpty()){
                emailUser.setError("Email address is required");
                emailUser.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                emailUser.setError("Please provide valid email!");
                emailUser.requestFocus();
                return;
            }
            if(password.length()<8){
                pwdUser.setError("Minimum password length should be 8 characters!");
                pwdUser.requestFocus();
                return;
            }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user=new User(name,phone,email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<Void> task) {


                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this,"User has been registered successfully!",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(RegisterUser.this,MainActivity.class));
                                    }else{
                                        Toast.makeText(RegisterUser.this,"Failed to register! Try again!",Toast.LENGTH_LONG).show();
                                    }
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        }else{
                            Toast.makeText(RegisterUser.this,"Failed to register!",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
        }
    }