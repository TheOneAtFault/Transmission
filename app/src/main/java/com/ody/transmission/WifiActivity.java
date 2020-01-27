package com.ody.transmission;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ody.wifi.Helpers.Wifi_Response;
import com.ody.wifi.Services.Wifi_Check_Connection;
import com.ody.wifi.Services.Wifi_Cutter;
import com.ody.wifi.Services.Wifi_Print;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class WifiActivity extends AppCompatActivity {
    private Context _context;
    private String _address;
    private Wifi_Response response;
    private int GET_FROM_GALLERY = 1;
    private boolean CUT_PAPER = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        _context = getApplicationContext();

        final Switch cutMode = (Switch) findViewById(R.id.switch_wifi_cutMode);
        cutMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                CUT_PAPER = b;
            }
        });

        final EditText address = findViewById(R.id.edt_wifi_printerAddress);
        Button cut = findViewById(R.id.btn_wifi_manualCut);
        Button qr = findViewById(R.id.btn_wifi_printQR);
        Button image = findViewById(R.id.btn_wifi_printImage);
        Button text = findViewById(R.id.btn_wifi_printText);
        Button check = findViewById(R.id.btn_wifi_checkConnection);


        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _address = address.getText().toString();
                if (!TextUtils.isEmpty(_address)) {
                    wifiCut(_address);
                } else {
                    Toast.makeText(_context, "Enter the printer address", Toast.LENGTH_LONG).show();
                }
            }
        });

        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _address = address.getText().toString();
                if (!TextUtils.isEmpty(_address)) {
                    wifiQR(_address);
                } else {
                    Toast.makeText(_context, "Enter the printer address", Toast.LENGTH_LONG).show();
                }
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _address = address.getText().toString();
                if (!TextUtils.isEmpty(_address)) {
                    wifiImage(_address);
                } else {
                    Toast.makeText(_context, "Enter the printer address", Toast.LENGTH_LONG).show();
                }
            }
        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _address = address.getText().toString();
                if (!TextUtils.isEmpty(_address)) {
                    wifiText(_address);
                } else {
                    Toast.makeText(_context, "Enter the printer address", Toast.LENGTH_LONG).show();
                }
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _address = address.getText().toString();
                if (!TextUtils.isEmpty(_address)) {
                    wifiCheck(_context, _address);
                } else {
                    Toast.makeText(_context, "Enter the printer address", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void wifiCut(String address) {
        Wifi_Cutter.cut(address);
    }

    private void wifiCheck(Context context, String address) {
       boolean result = Wifi_Check_Connection.check(address);
       if (result){
           Toast.makeText(context,"connection established",Toast.LENGTH_LONG).show();
       }
       else {
           Toast.makeText(context,"connection failed",Toast.LENGTH_LONG).show();
       }
    }

    private void wifiQR(String address) {
        response = Wifi_Print.qr(address, "Wifi QR Printing Demo", CUT_PAPER);
        if (response.isSuccess()) {
            TextView textView = findViewById(R.id.tv_wifi_log);
            textView.setText("Complete.");
        } else {
            TextView textView = findViewById(R.id.tv_wifi_log);
            textView.setText("Error Message:" + response.getsErrorMessage() +
                    "\n" +
                    "Output Message: " + response.getsCustomMessage());
        }
    }

    private void wifiImage(String address) {
        _address = address;
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/");
        //We pass an extra array with the accepted mime types. This will ensure only components
        // with these MIME types are targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

        startActivityForResult(Intent.createChooser(intent, "Select Image"), GET_FROM_GALLERY);
    }

    private void wifiText(String address) {
        response = Wifi_Print.text(address, "Wifi Text Printing Demo \n", CUT_PAPER);
        if (response.isSuccess()) {
            TextView textView = findViewById(R.id.tv_wifi_log);
            textView.setText("Complete.");
        } else {
            TextView textView = findViewById(R.id.tv_wifi_log);
            textView.setText("Error Message:" + response.getsErrorMessage() +
                    "\n" +
                    "Output Message: " + response.getsCustomMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FROM_GALLERY && resultCode == RESULT_OK) {
            //content URI
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageView ivThumbnailPhoto = findViewById(R.id.imageView);
            ivThumbnailPhoto.setImageBitmap(bitmap);
            try {
                response = Wifi_Print.image(_address, bitmap, CUT_PAPER);
            } catch (Exception e) {
                TextView log = findViewById(R.id.tv_usb_log);
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                String s = writer.toString();
                log.setText(s);
            }
        }
    }
}
