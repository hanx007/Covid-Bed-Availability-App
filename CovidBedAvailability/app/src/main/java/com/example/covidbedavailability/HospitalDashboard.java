package com.example.covidbedavailability;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class HospitalDashboard extends AppCompatActivity {
    //    String hospitals[]=new String [] {"KMC Hospital","Yenepoya Medical College","K S Hegde Hospital","Kanachur Hospital","Apollo Hospital", "Wenlock Hospital"};
    RecyclerView recyclerView;

    private EditText searchField;
    private ImageButton imageButton;
    private DatabaseReference databaseReference;
    //List<String> hospitalList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_dashboard);

        databaseReference= FirebaseDatabase.getInstance().getReference("Hospital");

        searchField = findViewById(R.id.searchText);
        imageButton = findViewById(R.id.imageButton);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText=searchField.getText().toString();
                firebaseHospitalSearch(searchText);
            }
        });


        //        searchText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                filter(s.toString());
//            }
//        });
//
//        hospitalList=new ArrayList<>();
//         recyclerView=findViewById(R.id.recyclerView);
//         recyclerAdapter=new RecyclerAdapter(hospitalList);
//
//         recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//         recyclerView.setAdapter(recyclerAdapter);
//
//        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(dividerItemDecoration);
//
//        hospitalList.add("KMC Hospital");
//        hospitalList.add("Yenepoya Medical College");
//        hospitalList.add("K S Hegde Hospital");
//        hospitalList.add("Kanachur Hospital");
//        hospitalList.add("Apollo Hospital");
//        hospitalList.add("Wenlock Hospital");
////         moviesList.add("");
////         moviesList.add("KMC Hospital");
////         moviesList.add("KMC Hospital");
////         moviesList.add("KMC Hospital");
//
//
//    }
////    private void filter(String text){
////        List<String> filteredList;
////        for(String item: mList){
////            if(item.getText1())
////        }
//    }
    }
    private void firebaseHospitalSearch(String searchText){
        Toast.makeText(HospitalDashboard.this,"Searching...",Toast.LENGTH_SHORT).show();
        Query firebaseSearchQuery = databaseReference.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerOptions<HospitalSearch> options=
                new FirebaseRecyclerOptions.Builder<HospitalSearch>()
                        .setQuery(firebaseSearchQuery,HospitalSearch.class)
                        .setLifecycleOwner(this)
                        .build();

        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<HospitalSearch, HospitalViewHolder>(options) {



            @NonNull
            @Override
            public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new HospitalViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_item, parent, false));

            }


            @Override
            protected void onBindViewHolder(@NonNull final HospitalViewHolder itemsViewHolder, int position,@NonNull HospitalSearch model) {
                itemsViewHolder.setDetails(getApplicationContext(),model.getName(),model.getStatus(),model.getImage());


            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    };




    //View holder class
    class HospitalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View view;
        String hname,hqty;
        public HospitalViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            view = itemView;

            itemView.setOnClickListener(this);
        }

        public void setDetails(Context ctx, String h_Name, String h_Status, String h_Image) {
            TextView hName = (TextView) view.findViewById(R.id.textView1);
            TextView hStatus = (TextView) view.findViewById(R.id.rowCountTextView);
            ImageView hImage = (ImageView) view.findViewById(R.id.imageView1);

            hName.setText(h_Name);
            hStatus.setText(h_Status);
            hname=hName.getText().toString();
            hqty=hStatus.getText().toString();
            Glide.with(ctx).load(h_Image).into(hImage);
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(HospitalDashboard.this, HospitalProfile.class);
            Bundle extras = new Bundle();
            extras.putString("EXTRA_HNAME",hname);
            extras.putString("EXTRA_BEDQTY",hqty);
            intent.putExtras(extras);
            startActivity(intent);
        }
    }


}

