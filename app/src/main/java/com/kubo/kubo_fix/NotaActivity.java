package com.kubo.kubo_fix;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kubo.kubo_fix.Common.Common;
import com.kubo.kubo_fix.Model.Barberman;
import com.kubo.kubo_fix.Model.Barbershop;
import com.kubo.kubo_fix.Model.Order;
import com.kubo.kubo_fix.Model.Service;

public class NotaActivity extends AppCompatActivity {

    //database
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference serviceReference, barbermanReference, jadwalReference, orderReference,
            usersOrderReference, barbershopReference;

    TextView txtBarbershop;
    TextView txtService;
    TextView txtBarberman;
    TextView txtJadwal;
    TextView txtTotal;
    Button btnOrder, btnCancel;

    String totalharga, jadwal, barberman, service, barbershop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota);

        setTitle("Order List Anda");
        Log.e("Nota", "Order List");

        txtBarbershop = findViewById(R.id.showBarbershop);
        txtService = findViewById(R.id.showService);
        txtBarberman = findViewById(R.id.showBarberman);
        txtJadwal = findViewById(R.id.showJadwalBooking);
        txtTotal = findViewById(R.id.showBiaya);
        btnOrder = findViewById(R.id.buttonOrder);
        btnCancel = findViewById(R.id.buttonCancel);

        //memulai firebase database
        mFirebaseInstance = FirebaseDatabase.getInstance();

        getData();

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                booking();

                Intent intent = new Intent(NotaActivity.this, MainActivity.class);

                Toast.makeText(NotaActivity.this,
                        "Order anda akan kami proses, silahkan cek tab Order",
                        Toast.LENGTH_LONG).show();

                startActivity(intent);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(NotaActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    private void booking(){

        // get reference to 'UsersOrder'
        usersOrderReference = mFirebaseInstance.getReference("UsersOrder");

        // get reference to 'order'
        orderReference = mFirebaseInstance.getReference("Order");

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String idOrder = orderReference.push().getKey();
        String atasnama = user.getEmail();
        String userId = user.getUid();
        String idBarbershop = Common.barbershopSelected;
        String status = "Waiting for approval";

        Order orderdata = new Order(idOrder, userId, atasnama, idBarbershop, barbershop, barberman, service, totalharga, jadwal, status);

        orderReference.child(Common.barbershopSelected).child(idOrder).setValue(orderdata);
        usersOrderReference.child(userId).child(idOrder).setValue(orderdata);

    }

    private void getData() {

        // get reference to 'service'
        serviceReference = mFirebaseInstance.getReference("Service");
        serviceReference.child(Common.barbershopSelected).child(Common.serviceSelected)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Service currentService = dataSnapshot.getValue(Service.class);

                service = currentService.getNama();
                totalharga = currentService.getHarga();
                txtTotal.setText(totalharga);
                txtService.setText(service);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // get reference to 'barberman'
        barbermanReference = mFirebaseInstance.getReference("Barberman");
        barbermanReference.child(Common.barbershopSelected).child(Common.barbermanSelected)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Barberman currentBarberman = dataSnapshot.getValue(Barberman.class);

                        barberman = currentBarberman.getNama();
                        txtBarberman.setText(barberman);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        // get 'jadwal'
        jadwal = Common.jadwalSelected;
        txtJadwal.setText(jadwal);

//        jadwalReference = mFirebaseInstance.getReference("Jadwal");
//        jadwalReference.child(Common.barbershopSelected).child(Common.jadwalSelected)
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        Jadwal currentJadwal = dataSnapshot.getValue(Jadwal.class);
//
//                        jadwal = currentJadwal.getWaktu();
//                        txtJadwal.setText(jadwal);
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });

        // get reference to 'barbershop'
        barbershopReference = mFirebaseInstance.getReference("Barbershop");
        barbershopReference.child(Common.barbershopSelected)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Barbershop currentBarbershop = dataSnapshot.getValue(Barbershop.class);

                        barbershop = currentBarbershop.getNama();
                        txtBarbershop.setText(barbershop);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

}

