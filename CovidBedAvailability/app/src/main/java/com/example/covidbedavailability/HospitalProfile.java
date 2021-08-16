package com.example.covidbedavailability;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HospitalProfile extends AppCompatActivity {
    private Button bRequest;
    private  TextView h_name,bedQuantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_profile);
        final TextView displayHName=(TextView)findViewById(R.id.h_name);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String HospitalName = extras.getString("EXTRA_HNAME");
        String BedQuantity = extras.getString("EXTRA_BEDQTY");



        h_name=(TextView)findViewById(R.id.h_name);
        bedQuantity=(TextView)findViewById(R.id.bedQuantity);

        h_name.setText(HospitalName);
        bedQuantity.setText(BedQuantity);


        bRequest=(Button)findViewById(R.id.bRequest);
        bRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HospitalProfile.this,RequestBed.class));
            }
        });
    }
}