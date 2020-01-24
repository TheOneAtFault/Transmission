package com.ody.transmission;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ody.usb.Helpers.USB_Response;
import com.ody.usb.Services.USB_Cutter;
import com.ody.usb.Services.USB_Devices;
import com.ody.usb.Services.USB_Print;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

public class USBActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int GET_FROM_GALLERY = 1;
    private String selectedImage = "";
    private ArrayList<String> list = new ArrayList<>();
    private int vendorId = 0;
    private ImageView someview;
    private USB_Response response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usb);

        //get usb devices and fill spinner
        Spinner spinner = (Spinner) findViewById(R.id.spnr_usb_devices);
        spinner.setOnItemSelectedListener(this);

        int[] vendors = getDevices();

        for (int i = 0; i < vendors.length; i++) {
            list.add(String.valueOf(vendors[i]));
        }

        //String result = String.join(",","ww","ww");

        someview = findViewById(R.id.imageView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button textPrint = findViewById(R.id.usb_btn_printText);
        Button textImage = findViewById(R.id.usb_btn_printImage);
        Button devices = findViewById(R.id.btn_usb_showDevices);
        Button qr = findViewById(R.id.btn_usb_qr);
        Button fullCut = findViewById(R.id.btn_usb_fullcut);
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printQR();
            }
        });
        fullCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullCut();
            }
        });
        textPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printText();
            }
        });
        textImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printImage();
            }
        });
        devices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDevices();
            }
        });

    }

    public void fullCut(){
        USB_Cutter.cut(
                this,
                vendorId);
    }

    private void printQR() {
        response = USB_Print.qr(
                this,
                vendorId,
                "123"
        );

        TextView textView = findViewById(R.id.tv_usb_log);
        textView.setText("Error Message:" + response.getsErrorMessage() +
                "\n" +
                "Output Message: " + response.getsCustomMessage());
    }

    //print Text
    public void printText() {
        response = USB_Print.textPlain(
                this,
                vendorId,
                "This is some text to figure out the printing \n"
        );

        TextView textView = findViewById(R.id.tv_usb_log);
        textView.setText("Error Message:" + response.getsErrorMessage() +
                "\n" +
                "Output Message: " + response.getsCustomMessage());
    }

    //USB_Print Image
    public void printImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/");
        //We pass an extra array with the accepted mime types. This will ensure only components
        // with these MIME types are targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

        startActivityForResult(Intent.createChooser(intent, "Select Image"), GET_FROM_GALLERY);
    }

    //Show USB_Devices
    public int[] getDevices() {
        return USB_Devices.getAll(this);
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
                response = USB_Print.image(this, vendorId, bitmap);
            } catch (Exception e) {
                TextView log = findViewById(R.id.tv_usb_log);
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                String s = writer.toString();
                log.setText(s);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getSelectedItem().toString();
        vendorId = Integer.parseInt(text);
        TextView info = (TextView) findViewById(R.id.tv_usb_log);
        info.setText(vendorId + "");
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
