package com.kubo.kubo_fix.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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
    FirebaseRecyclerAdapter<Barbershop, BarbershopViewHolder> searchAdapter;

    private EditText mSearchField;
    private ImageButton mCloseBtn;

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

        mSearchField = view.findViewById(R.id.search_field);
        mCloseBtn = view.findViewById(R.id.close_btn);

        recyclerView = view.findViewById(R.id.recycler_barbershop);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseMessaging.getInstance().subscribeToTopic("notifications");

        loadBarbershop();

        //to add your token when login app
        updateToken(FirebaseInstanceId.getInstance().getToken());

        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSearchField.getText().clear();
                loadBarbershop();

            }
        });

        mSearchField.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String searchText = mSearchField.getText().toString();

                    startSearch(searchText);
                    return true;
                }
                return false;
            }
        });

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

    //method search
    private void startSearch(String searchText) {

        //Create query by name
        Query searchByName = FirebaseDatabase.getInstance()
                .getReference()
                .child("Barbershop").orderByChild("nama").startAt(searchText).endAt(searchText + "\uf8ff");

        //Create option with query
        FirebaseRecyclerOptions<Barbershop> serviceOptions = new FirebaseRecyclerOptions.Builder<Barbershop>()
                .setQuery(searchByName, Barbershop.class)
                .build();

        //adapter hasil search
        searchAdapter = new FirebaseRecyclerAdapter<Barbershop, BarbershopViewHolder>(serviceOptions) {
            //setup viewholder recyclerview hasil search
            @Override
            protected void onBindViewHolder(@NonNull BarbershopViewHolder viewHolder, int position, @NonNull Barbershop model) {

                viewHolder.barbershopNama.setText(model.getNama());
                Picasso.with(getActivity()).load(model.getImage())
                        .into(viewHolder.barbershopImage);

                final Barbershop local = model;

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
        searchAdapter.startListening();
        recyclerView.setAdapter(searchAdapter); // Set adapter for Recycler View is Search result
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
