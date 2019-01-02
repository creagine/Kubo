package com.kubo.kubo_fix;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.kubo.kubo_fix.Common.Common;
import com.kubo.kubo_fix.Interface.ItemClickListener;
import com.kubo.kubo_fix.Model.Order;
import com.kubo.kubo_fix.ViewHolder.OrderViewHolder;

public class CheckAntrianActivity extends AppCompatActivity {

    DatabaseReference orderReference;

    //var recycler
    RecyclerView recyclerView;

    //firebase recycler adapter
    FirebaseRecyclerAdapter<Order,OrderViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_antrian);

        //get barbershopId from uid current user
        String barbershopId = Common.barbershopSelected;

        // get reference to 'order'
        orderReference = FirebaseDatabase.getInstance().getReference("Order").child(barbershopId);

        //get current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //init recycler shop
        recyclerView = findViewById(R.id.recycler_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadOrder();

    }

    //method load shop
    private void loadOrder() {

        //Create query by category id
        Query queryByStatus = orderReference.orderByChild("status").equalTo("Order Approved");

        //firebase recycler, model Shop
        FirebaseRecyclerOptions<Order> options = new FirebaseRecyclerOptions.Builder<Order>()
                .setQuery(queryByStatus,Order.class)
                .build();

        //recycler adapter shop - ShopViewHolder
        adapter = new FirebaseRecyclerAdapter<Order, OrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder viewHolder, int position, @NonNull Order model) {

                viewHolder.service.setText(model.getService());
                viewHolder.jadwal.setText(model.getJadwal());
                viewHolder.price.setText(model.getTotalharga());
                viewHolder.status.setText(model.getStatus());

                final Order clickItem = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {


                    }



                });

            }

            @Override
            public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.order_item, parent, false);
                return new OrderViewHolder(itemView);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
            finish();
    }

}
