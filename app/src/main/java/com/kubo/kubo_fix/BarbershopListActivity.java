package com.kubo.kubo_fix;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.kubo.kubo_fix.Common.Common;
import com.kubo.kubo_fix.Interface.ItemClickListener;
import com.kubo.kubo_fix.Model.Barbershop;
import com.kubo.kubo_fix.ViewHolder.BarbershopViewHolder;
import com.squareup.picasso.Picasso;

public class BarbershopListActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    FirebaseRecyclerAdapter<Barbershop, BarbershopViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbershop_list);

        recyclerView = findViewById(R.id.recycler_barbershop);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadBarbershop();

    }

    //method load shop
    private void loadBarbershop() {

        //firebase recycler, model Shop
        FirebaseRecyclerOptions<Barbershop> options = new FirebaseRecyclerOptions.Builder<Barbershop>()
                .setQuery(FirebaseDatabase.getInstance()
                                .getReference()
                                .child("Barbershop")
                        ,Barbershop.class)
                .build();

        //recycler adapter shop - ShopViewHolder
        adapter = new FirebaseRecyclerAdapter<Barbershop, BarbershopViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BarbershopViewHolder viewHolder, int position, @NonNull Barbershop model) {

                viewHolder.barbershopNama.setText(model.getNama());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.barbershopImage);

                final Barbershop clickItem = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        //Get CategoryId and send to new Activity
                        Intent barbershopList = new Intent(BarbershopListActivity.this, ServiceActivity.class);

                        //When user select shop, we will save shop id to select service of this shop
                        Common.barbershopSelected = adapter.getRef(position).getKey();

                        startActivity(barbershopList);

                    }
                });
            }

            @Override
            public BarbershopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.barbershop_item, parent, false);
                return new BarbershopViewHolder(itemView) {
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