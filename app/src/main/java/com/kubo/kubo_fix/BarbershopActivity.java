package com.kubo.kubo_fix;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kubo.kubo_fix.Common.Common;
import com.kubo.kubo_fix.Interface.ItemClickListener;
import com.kubo.kubo_fix.Model.Barbershop;
import com.kubo.kubo_fix.Model.Service;
import com.kubo.kubo_fix.ViewHolder.ServiceViewHolder;
import com.squareup.picasso.Picasso;

public class BarbershopActivity extends AppCompatActivity {

    TextView txtNama, txtAlamat;
    ImageView imgBarbershop;
    Button btnBooking, btnAntrian;

    FirebaseDatabase database;
    DatabaseReference barbershopReference;

    RecyclerView recyclerView;

    FirebaseRecyclerAdapter<Service, ServiceViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbershop);

        setTitle("KUBO");
        Log.e("Barbershop", "Pilih Barbershop");

        //Firebase
        database = FirebaseDatabase.getInstance();
        barbershopReference = database.getReference("Barbershop");

        recyclerView = findViewById(R.id.recycler_service);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadService();

        txtNama = findViewById(R.id.TextViewNamaBarbershop);
        txtAlamat = findViewById(R.id.textViewAlamatBarbershop);
        imgBarbershop = findViewById(R.id.imageViewBarbershop);
        btnBooking = findViewById(R.id.buttonBooking);
        btnAntrian = findViewById(R.id.buttonCheckAntrian);

        getBarbershop(Common.barbershopSelected);

        btnAntrian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BarbershopActivity.this, CheckAntrianActivity.class);
                startActivity(intent);

            }
        });

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent booking = new Intent(BarbershopActivity.this, ServiceActivity.class);
                startActivity(booking);

            }
        });

    }

    private void getBarbershop(String idBarbershop) {
        barbershopReference.child(idBarbershop).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Barbershop currentBarbershop = dataSnapshot.getValue(Barbershop.class);

                //Set Image
                Picasso.with(getBaseContext()).load(currentBarbershop.getImage())
                        .into(imgBarbershop);

                txtNama.setText(currentBarbershop.getNama());
                txtAlamat.setText(currentBarbershop.getAlamat());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //method load shop
    private void loadService() {

        //firebase recycler, model Shop
        FirebaseRecyclerOptions<Service> options = new FirebaseRecyclerOptions.Builder<Service>()
                .setQuery(FirebaseDatabase.getInstance()
                                .getReference()
                                .child("Service").child(Common.barbershopSelected)
                        ,Service.class)
                .build();

        //recycler adapter shop - ShopViewHolder
        adapter = new FirebaseRecyclerAdapter<Service, ServiceViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ServiceViewHolder viewHolder, int position, @NonNull Service model) {

                viewHolder.serviceNama.setText(model.getNama());
                viewHolder.serviceHarga.setText(model.getHarga());


                final Service clickItem = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

//                        //Get CategoryId and send to new Activity
//                        Intent serviceList = new Intent(BarbershopActivity.this, BarbermanActivity.class);
//
//                        //When user select shop, we will save shop id to select service of this shop
//                        Common.serviceSelected = adapter.getRef(position).getKey();
//
//                        startActivity(serviceList);

                    }
                });
            }

            @NonNull
            @Override
            public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.service_item, parent, false);
                return new ServiceViewHolder(itemView) {
                };
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        //show item in service list when click back from service detail
        if (adapter != null)
            adapter.startListening();
    }

}
