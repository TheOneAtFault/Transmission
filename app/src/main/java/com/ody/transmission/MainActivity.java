package com.ody.transmission;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//Toast.makeText(context,"Jobs done",Toast.LENGTH_SHORT).show();

public class MainActivity extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = getApplicationContext();

        //aidl
        Button aidl = findViewById(R.id.btn_AIDL);
        final Button usb = findViewById(R.id.btn_usb);
        Button serial = findViewById(R.id.btn_serial);
        Button wifi = findViewById(R.id.btn_wifi);


        aidl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toAidl = new Intent(MainActivity.this, AIDLActivity.class);
                //toAidl.putExtra("key", 0);
                startActivity(toAidl);
            }
        });

        usb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toUsb = new Intent(MainActivity.this, USBActivity.class);
                startActivity(toUsb);
            }
        });

        serial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSerial = new Intent(MainActivity.this, SerialActivity.class);
                startActivity(toSerial);
            }
        });

        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toWifi = new Intent(MainActivity.this, WifiActivity.class);
                startActivity(toWifi);
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.hardware.usb.action.USB_STATE");

    }

}
