package com.example.covidbedavailability;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestBed extends AppCompatActivity implements View.OnClickListener {
    FirebaseDatabase rootNode;
    DatabaseReference reff;
    private TextView requestBtn;
    private EditText patientName,patientGender,patientAge,phoneNumber,patientRelation;
    private Spinner patientCondition,reachingTime,spinnerHospital;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_bed);

        requestBtn=(Button)findViewById(R.id.requestBtn);
        requestBtn.setOnClickListener(this);

        patientName=(EditText)findViewById(R.id.patientName);
        patientAge=(EditText)findViewById(R.id.patientAge);
        phoneNumber=(EditText)findViewById(R.id.phoneNumber);
        patientGender=(EditText)findViewById(R.id.patientGender);
        patientRelation=(EditText)findViewById(R.id.patientRelation);
        patientCondition=(Spinner)findViewById(R.id.patientCondition);
        reachingTime=(Spinner)findViewById(R.id.reachingTime);
        spinnerHospital=(Spinner)findViewById(R.id.spinnerHospital);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        ArrayAdapter<CharSequence> hospitaladapter =  ArrayAdapter.createFromResource(this,R.array.hospital_array, android.R.layout.simple_spinner_item);
        hospitaladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHospital.setAdapter(hospitaladapter);

        ArrayAdapter<CharSequence> timeadapter = ArrayAdapter.createFromResource(this,R.array.time_array, android.R.layout.simple_spinner_item);
        timeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reachingTime.setAdapter(timeadapter);

        ArrayAdapter<CharSequence> symptomsadapter = ArrayAdapter.createFromResource(this,R.array.symptoms_array,android.R.layout.simple_spinner_item);
        symptomsadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patientCondition.setAdapter(symptomsadapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.requestBtn){
            registerPatient();
            progressBar.setVisibility(View.VISIBLE);

        }
    }
    private void  registerPatient(){
        rootNode=FirebaseDatabase.getInstance();
        reff=rootNode.getReference("patient");
        String name = patientName.getText().toString().trim();
        String age = patientAge.getText().toString().trim();
        String phone = phoneNumber.getText().toString().trim();
        String relation = patientRelation.getText().toString().trim();
        String gender = patientGender.getText().toString().trim();
        String time = reachingTime.getSelectedItem().toString().trim();
        String condition = patientCondition.getSelectedItem().toString().trim();
        String hospital=spinnerHospital.getSelectedItem().toString().trim();
        if(name.isEmpty()){
            patientName.setError("Enter name");
            patientName.requestFocus();
            return;
        }
        if(age.isEmpty()){
            patientAge.setError("Enter age");
            patientAge.requestFocus();
            return;
        }
        if(age.length()>3){
            patientAge.setError("Enter age");
            patientAge.requestFocus();
            return;
        }
        if(phone.isEmpty()){
            phoneNumber.setError("Enter phone");
            phoneNumber.requestFocus();
            return;
        }


        if(relation.isEmpty()){
            patientRelation.setError("Enter relation");
            patientRelation.requestFocus();
            return;
        }
        if (gender.isEmpty()){
            patientGender.setError("Enter gender");
            patientGender.requestFocus();
            return;
        }


        if(phone.length() != 10){
            phoneNumber.setError("Minimum Phone number length should be 10 characters");
            phoneNumber.requestFocus();
            return;
        }
        progressBar.setVisibility(View.GONE);

        Member patient=new Member(name,age,phone,relation,gender,time,condition,hospital);
        reff.child(phone).setValue(patient);
        Toast.makeText(RequestBed.this,"Requested bed successfully",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RequestBed.this,UserProfile.class));
    }
}