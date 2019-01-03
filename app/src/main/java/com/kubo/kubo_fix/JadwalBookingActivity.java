package com.kubo.kubo_fix;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.kubo.kubo_fix.Common.Common;

public class JadwalBookingActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private String format = "";
    private String time;

    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_jadwal_booking);

        setTitle("Book Time");
        Log.e("Booking", "Masukkan Jadwal Booking Anda");

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        btnNext = findViewById(R.id.buttonNext);

        timePicker.setIs24HourView(true);
        int hour = timePicker.getCurrentHour();
        int min = timePicker.getCurrentMinute();

        time = String.valueOf(hour)+":"+String.valueOf(min);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //When user select shop, we will save shop id to select service of this shop
                Common.jadwalSelected = time;

                Intent intent = new Intent(JadwalBookingActivity.this, NotaActivity.class);
                startActivity(intent);

            }
        });


    }

}
