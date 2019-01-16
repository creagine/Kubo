package com.kubo.kubo_fix.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kubo.kubo_fix.BarbershopActivity;
import com.kubo.kubo_fix.Common.Common;
import com.kubo.kubo_fix.Interface.ItemClickListener;
import com.kubo.kubo_fix.Model.Barbershop;
import com.kubo.kubo_fix.Model.Token;
import com.kubo.kubo_fix.R;
import com.kubo.kubo_fix.ViewHolder.BarbershopViewHolder;
import com.squareup.picasso.Picasso;


public class HomeFragment extends Fragment {

    RecyclerView recyclerView;

    FirebaseRecyclerAdapter<Barbershop, BarbershopViewHolder> adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recycler_barbershop);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseMessaging.getInstance().subscribeToTopic("notifications");

        loadBarbershop();

        //to add your token when login app
        updateToken(FirebaseInstanceId.getInstance().getToken());

        return view;
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
                Picasso.with(getActivity()).load(model.getImage())
                        .into(viewHolder.barbershopImage);

                final Barbershop clickItem = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        //Get CategoryId and send to new Activity
                        Intent barbershopList = new Intent(getActivity(), BarbershopActivity.class);

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

    private void updateToken(String token) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference tokens = db.getReference("Tokens");
        Token data = new Token(token,false); //false because this token from client app
        tokens.child(Common.currentUser).setValue(data);
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
