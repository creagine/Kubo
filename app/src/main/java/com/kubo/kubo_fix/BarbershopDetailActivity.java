package com.kubo.kubo_fix;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kubo.kubo_fix.Model.Barbershop;

public class BarbershopDetailActivity extends AppCompatActivity {

    TextView barbershop_name,service_price;
    ImageView service_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FirebaseDatabase database;
    DatabaseReference service;
    String serviceId="";

    Barbershop currentService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbershop_detail);

        //Firebase
        database = FirebaseDatabase.getInstance();
        service = database.getReference("Barbershop");

        barbershop_name = findViewById(R.id.barbershop_name);

        getDetailService(serviceId);
    }

    private void getDetailService(String serviceId) {
        service.child(serviceId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentService = dataSnapshot.getValue(Barbershop.class);

                barbershop_name.setText(currentService.getNama());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
