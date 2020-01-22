package com.ody.transmission;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ody.aidl.Helpers.Response;
import com.ody.aidl.Services.Print;
import com.ody.aidl.StartUp;
import com.ody.aidl.Utils.AidlUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;


public class AIDLActivity extends AppCompatActivity {

    private static final int GET_FROM_GALLERY = 1;
    private boolean CUT_PAPER = false;
    private int PADDING = 1;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        context = getApplicationContext();
        Button text = (Button) findViewById(R.id.aidl_btn_printText);
        Button image = (Button) findViewById(R.id.aidl_btn_printImage);
        Button qr = (Button) findViewById(R.id.aidl_btn_generateQR);
        Button polling = (Button) findViewById(R.id.aidl_btn_pollingDisplay);
        Button kickdrawer = (Button) findViewById(R.id.aidl_btn_drawerKick);

        StartUp.ignition(context);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Response response = Print.text("AIDL Text USB_Print Test \n", CUT_PAPER, PADDING);
                    TextView log = (TextView) findViewById(R.id.aidl_tv_log);
                    if (!response.isSuccess()) {
                        log.setText(response.getsErrorMessage());
                    }
                } catch (Exception e) {
                    TextView log = findViewById(R.id.aidl_tv_log);
                    Writer writer = new StringWriter();
                    e.printStackTrace(new PrintWriter(writer));
                    String s = writer.toString();
                    log.setText(s);
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
                Response response = Print.qr("", CUT_PAPER, PADDING);
                TextView log = (TextView) findViewById(R.id.aidl_tv_log);
                if (!response.isSuccess()) {
                    log.setText(response.getsErrorMessage());
                }
            }
        });
        polling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AIDLActivity.this, AIDLPollingActivity.class);
                startActivity(intent);
            }
        });
        kickdrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        final Switch cut = (Switch) findViewById(R.id.switch_Cut);
        cut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                CUT_PAPER = b;
            }
        });

        Button cutManual = (Button) findViewById(R.id.btn_manualcut);
        cutManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response response = Print.cut();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FROM_GALLERY) {
            //content URI
            if(data != null){
                Uri selectedImage = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ImageView ivThumbnailPhoto = findViewById(R.id.aidl_iv_image);
                ivThumbnailPhoto.setImageBitmap(bitmap);
                try {
                    Response response = Print.image(bitmap, CUT_PAPER, PADDING);
                } catch (Exception e) {
                    TextView log = findViewById(R.id.tv_usb_log);
                    Writer writer = new StringWriter();
                    e.printStackTrace(new PrintWriter(writer));
                    String s = writer.toString();
                    log.setText(s);
                }

                //Toast.makeText(this, "caught", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //QR

    //Polling
    //KickDrawer

}
