package com.ody.transmission;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ody.usb.Helpers.Response;
import com.ody.usb.Services.Devices;
import com.ody.usb.Services.Print;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class USBActivity extends AppCompatActivity {

    private static final int GET_FROM_GALLERY = 1;
    private String selectedImage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usb);

        Button textPrint = findViewById(R.id.usb_btn_printText);
        Button textImage = findViewById(R.id.usb_btn_printImage);
        Button devices = findViewById(R.id.btn_usb_showDevices);
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

    //print Text
    public void printText(){
       Response response =  Print.textPlain(this,1208,"Test usb text printing in module.");
        TextView textView = findViewById(R.id.tv_usb_log);
        textView.setText(response.getsErrorMessage() + "\n" + response.getsCustomMessage());
    }

    //Print Image
    public void printImage(){
        //startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);

        startActivityForResult(Intent.createChooser(intent, "Select Image"), GET_FROM_GALLERY);


//        textView.setText(response.getsErrorMessage() + "\n" + response.getsCustomMessage());
    }

    //Show Devices
    public void getDevices(){
        String result = "No devices found";
        result = Devices.getAll(this);
        TextView textView = findViewById(R.id.tv_usb_log);
        textView.setText(result);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GET_FROM_GALLERY){
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
            try{
                Response response =  Print.image(this,1208,"Test usb text printing in module.", bitmap);
            }catch (Exception e){
                TextView log = findViewById(R.id.tv_usb_log);
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                String s = writer.toString();
                log.setText(s);
            }


            Toast.makeText(this, "caught",Toast.LENGTH_SHORT).show();
        }
    }
}
