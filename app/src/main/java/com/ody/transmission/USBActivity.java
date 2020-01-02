package com.ody.transmission;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ody.usb.Helpers.Response;
import com.ody.usb.Services.Devices;
import com.ody.usb.Services.Print;

public class USBActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usb);

        Button textPrint = findViewById(R.id.usb_btn_printText);
        Button devices = findViewById(R.id.btn_usb_showDevices);
        textPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printText();
            }
        });
        devices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDevices();
            }
        });
    }

    //print Text
    public void printText(){
       Response response =  Print.textPlain(this,1208,"Test usb text printing in module.");
        TextView textView = findViewById(R.id.tv_usb_log);
        textView.setText(response.getsErrorMessage() + "\n" + response.getsCustomMessage());
    }

    //Print Image
    public void printImage(){
        Response response =  Print.image(this,1208,"Test usb text printing in module.");
        TextView textView = findViewById(R.id.tv_usb_log);
        textView.setText(response.getsErrorMessage() + "\n" + response.getsCustomMessage());
    }

    //Show Devices
    public void getDevices(){
        String result = "No devices found";
        result = Devices.getAll(this);
        TextView textView = findViewById(R.id.tv_usb_log);
        textView.setText(result);
    }
}
