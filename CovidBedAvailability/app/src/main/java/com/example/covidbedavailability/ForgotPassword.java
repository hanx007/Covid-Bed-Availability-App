package com.example.covidbedavailability;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class ForgotPassword extends AppCompatActivity {
    private EditText etemail;
    private Button resetBtn;
    private ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etemail=(EditText)findViewById(R.id.email);
        resetBtn=(Button)findViewById(R.id.btnUpdate);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        auth=FirebaseAuth.getInstance();

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private  void resetPassword(){
        String email=etemail.getText().toString().trim();
        if(email.isEmpty()){
            etemail.setError("Email is required!");
            etemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etemail.setError("Please provide a valid email!");
            etemail.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this,"Check your email to reset your password!",Toast.LENGTH_LONG).show();


                }else{
                    Toast.makeText(ForgotPassword.this,"Try again! Something wrong happened!",Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
                startActivity(new Intent(ForgotPassword.this,MainActivity.class));
            }
        });

    }
}