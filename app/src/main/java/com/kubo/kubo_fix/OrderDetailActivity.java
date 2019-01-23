package com.kubo.kubo_fix;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.kubo.kubo_fix.Model.MyResponse;
import com.kubo.kubo_fix.Model.Notification;
import com.kubo.kubo_fix.Model.Order;
import com.kubo.kubo_fix.Model.Sender;
import com.kubo.kubo_fix.Model.Token;
import com.kubo.kubo_fix.Remote.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {

    //database
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference orderReference, usersOrderReference, usersOrderHistoryReference,
            orderHistoryReference;

    TextView txtBarbershop;
    TextView txtService;
    TextView txtBarberman;
    TextView txtJadwal;
    TextView txtTotal;
    Button btnCancel, btnCall, btnWa;

    String idOrder, idUser, atasnama, totalharga, idBarbershop, jadwal, barberman, service,
            barbershop, status, phoneBarbershop, phoneUser;

    APIService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        setTitle("Detail order");

        //Init FCM Service buat notif
        mService = Common.getFCMService();

        //ngecek di node Barbershop, id user yg sudah login sudah ada apa belum
        //get current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //get barbershopId from uid current user
        String barbershopId = user.getUid();

        txtBarbershop = findViewById(R.id.showBarbershop);
        txtService = findViewById(R.id.showService);
        txtBarberman = findViewById(R.id.showBarberman);
        txtJadwal = findViewById(R.id.showJadwalBooking);
        txtTotal = findViewById(R.id.showBiaya);
        btnCancel = findViewById(R.id.buttonCancel);
        btnCall = findViewById(R.id.buttonCall);
        btnWa = findViewById(R.id.buttonWa);

        //memulai firebase database
        mFirebaseInstance = FirebaseDatabase.getInstance();

        getData(barbershopId);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get reference to 'order'
                orderReference = mFirebaseInstance.getReference("Order");
                // get reference to 'usersorder'
                usersOrderReference = mFirebaseInstance.getReference("UsersOrder");
                // get reference to 'orderhistory'
                orderHistoryReference = mFirebaseInstance.getReference("OrderHistory");
                // get reference to 'usersorderhistory'
                usersOrderHistoryReference = mFirebaseInstance.getReference("UsersOrderHistory");

                status = "Order Cancelled";

                Order cancelOrder = new Order(idOrder, idUser, atasnama, idBarbershop, barbershop,
                        barberman, service, totalharga, jadwal, status, phoneBarbershop, phoneUser);

                //data diisi ke order history (seperti seakan2 dipindah)
                orderHistoryReference.child(idBarbershop).child(Common.orderSelected).setValue(cancelOrder);
                usersOrderHistoryReference.child(idUser).child(Common.orderSelected).setValue(cancelOrder);

                orderReference.child(idBarbershop).child(Common.orderSelected).removeValue();
                usersOrderReference.child(idUser).child(Common.orderSelected).removeValue();

                sendOrderStatusToUser(Common.orderSelected,cancelOrder);

                Intent intent = new Intent(OrderDetailActivity.this, MainActivity.class);

                Toast.makeText(OrderDetailActivity.this,
                        "Order cancelled, moved to order history",
                        Toast.LENGTH_LONG).show();

                startActivity(intent);

            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int permissionCheck = ContextCompat.checkSelfPermission(OrderDetailActivity.this, Manifest.permission.CALL_PHONE);

                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            OrderDetailActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            123);
                } else {
                    //nomer user diambil dari order
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + phoneBarbershop));
                    startActivity(intent);
                }
            }
        });

        btnWa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_VIEW);
                String url = "https://api.whatsapp.com/send?phone=" + "62" + phoneBarbershop.substring(1);
                sendIntent.setData(Uri.parse(url));
                startActivity(sendIntent);

            }
        });

    }

    private void getData(String barbershopId) {

        // get reference to 'order'
        orderReference = mFirebaseInstance.getReference("UsersOrder");
        orderReference.child(barbershopId).child(Common.orderSelected)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Order currentOrder = dataSnapshot.getValue(Order.class);

                        idBarbershop = currentOrder.getIdBarbershop();
                        idOrder = currentOrder.getIdOrder();
                        atasnama = currentOrder.getAtasnama();
                        status = currentOrder.getStatus();
                        barbershop = currentOrder.getNamaBarbershop();
                        barberman = currentOrder.getBarberman();
                        service = currentOrder.getService();
                        totalharga = currentOrder.getTotalharga();
                        jadwal = currentOrder.getJadwal();
                        idUser = currentOrder.getIdUser();
                        phoneBarbershop = currentOrder.getPhoneBarbershop();
                        phoneUser = currentOrder.getPhoneUser();

                        txtBarbershop.setText(barbershop);
                        txtBarberman.setText(barberman);
                        txtService.setText(service);
                        txtTotal.setText(totalharga);
                        txtJadwal.setText(jadwal);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    private void sendOrderStatusToUser(final String key,final Order order) {

        DatabaseReference tokens = mFirebaseInstance.getReference("Tokens");

        tokens.orderByKey().equalTo(order.getIdBarbershop())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapShot:dataSnapshot.getChildren())
                        {
                            Token token = postSnapShot.getValue(Token.class);

                            //Make raw payload
                            Notification notification = new Notification("Click here to check",order.getStatus());
                            Sender content = new Sender(token.getToken(),notification);

                            mService.sendNotification(content)
                                    .enqueue(new Callback<MyResponse>() {
                                        @Override
                                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                            if (response.code() == 200) {
                                                if (response.body().success == 1) {
                                                    Toast.makeText(OrderDetailActivity.this, "Order was updated !", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(OrderDetailActivity.this, "Order was updated but failed to send notification !", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<MyResponse> call, Throwable t) {
                                            Log.e("ERROR",t.getMessage());
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

}
