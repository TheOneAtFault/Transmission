package com.ody.transmission;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ody.aidl.Helpers.Response;
import com.ody.aidl.Services.Print;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;


public class AIDLActivity extends AppCompatActivity {

    private static final int GET_FROM_GALLERY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        Button text = (Button) findViewById(R.id.aidl_btn_printText);
        Button image = (Button) findViewById(R.id.aidl_btn_printImage);
        Button qr = (Button) findViewById(R.id.aidl_btn_generateQR);
        Button polling = (Button) findViewById(R.id.aidl_btn_pollingDisplay);
        Button kickdrawer = (Button) findViewById(R.id.aidl_btn_drawerKick);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Response response =  Print.text("AIDL Text Print Test \n",true);
               TextView log = (TextView) findViewById(R.id.aidl_tv_log);
               if (!response.isSuccess()){
                   log.setText(response.getsErrorMessage());
               }
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/");
                String[] mimeTypes = {"image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

                startActivityForResult(Intent.createChooser(intent, "Select Image"), GET_FROM_GALLERY);
            }
        });
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        polling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        kickdrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FROM_GALLERY) {
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
                Response response = Print.image(bitmap,true);
            } catch (Exception e) {
                TextView log = findViewById(R.id.tv_usb_log);
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                String s = writer.toString();
                log.setText(s);
            }

            Toast.makeText(this, "caught", Toast.LENGTH_SHORT).show();
        }
    }

    //QR
    //Polling
    //KickDrawer

}
