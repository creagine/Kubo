package com.kubo.kubo_fix;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class NoInternetActivity extends AppCompatActivity {

    Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        btnRefresh = findViewById(R.id.buttonRefresh);

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // ARE WE CONNECTED TO THE NET
                if (isInternetConnected()) {

                    Intent intent = new Intent(NoInternetActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    Intent connectionIntent = new Intent(NoInternetActivity.this, NoInternetActivity.class);
                    startActivity(connectionIntent);
                    finish();
                }

            }

            public boolean isInternetConnected() {
                // At activity startup we manually check the internet status and change
                // the text status
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected())
                    return true;
                else
                    return false;


            }
        });
    }

}
