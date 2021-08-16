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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {
   private TextView register,forgotPassword;
   private EditText etemail,etpassword;
   private Button btnLogIn;

   private FirebaseAuth mAuth;
   private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register=(TextView)findViewById(R.id.register);
        register.setOnClickListener(this);

        btnLogIn=(Button)findViewById(R.id.btnLogin);
        btnLogIn.setOnClickListener(this);

        etemail=(EditText)findViewById(R.id.email);
        etpassword=(EditText)findViewById(R.id.pwd);

        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        mAuth=FirebaseAuth.getInstance();

        forgotPassword=(TextView)findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;

            case R.id.btnLogin:
                userLogin();
                break;

            case R.id.forgotPassword:
                startActivity(new Intent(this,ForgotPassword.class));
                break;

        }
    }

    private void userLogin(){
        String email=etemail.getText().toString().trim();
        String password=etpassword.getText().toString().trim();

        if(email.isEmpty()){
            etemail.setError("Email is required!");
            etemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etemail.setError("Please enter a valid email!");
            etemail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            etpassword.setError("Password is required!");
            etpassword.requestFocus();
            return;
        }
        if(password.length()<8){
            etpassword.setError("Minimum password length is 8 characters!");
            etpassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()){
                        //redirect to user profile
                        Toast.makeText(MainActivity.this,"Logged in successfully!",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MainActivity.this,UserProfile.class));
                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this,"Check your email to verify your account!",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(MainActivity.this,"Failed to login! Please check your credentials",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    etpassword.requestFocus();
                }
            }
        });

    }
}