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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.kubo.kubo_fix.Common.Common;
import com.kubo.kubo_fix.Interface.ItemClickListener;
import com.kubo.kubo_fix.Model.Barberman;
import com.kubo.kubo_fix.ViewHolder.BarbermanViewHolder;
import com.squareup.picasso.Picasso;

public class BarbermanActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    FirebaseRecyclerAdapter<Barberman, BarbermanViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_barberman);

        setTitle("Barberman List");
        Log.e("Barberman", "Pilih Barberman");

        recyclerView = findViewById(R.id.recycler_barberman);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadBarberman();

    }

    //method load shop
    private void loadBarberman() {

        //firebase recycler, model Shop
        FirebaseRecyclerOptions<Barberman> options = new FirebaseRecyclerOptions.Builder<Barberman>()
                .setQuery(FirebaseDatabase.getInstance()
                                .getReference()
                                .child("Barberman").child(Common. barbershopSelected)
                        ,Barberman.class)
                .build();

        //recycler adapter shop - ShopViewHolder
        adapter = new FirebaseRecyclerAdapter<Barberman, BarbermanViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BarbermanViewHolder viewHolder, int position, @NonNull Barberman model) {

                viewHolder.barbermanNama.setText(model.getNama());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.barbermanImage);

                final Barberman clickItem = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        //Get CategoryId and send to new Activity
                        Intent barbermanList = new Intent(BarbermanActivity.this, JadwalBookingActivity.class);

                        //When user select shop, we will save shop id to select service of this shop
                        Common.barbermanSelected = adapter.getRef(position).getKey();

                        startActivity(barbermanList);

                    }
                });
            }

            @Override
            public BarbermanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.barberman_item, parent, false);
                return new BarbermanViewHolder(itemView) {
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
