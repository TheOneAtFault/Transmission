package com.ody.transmission;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ody.aidl.Helpers.QRGenerator;
import com.ody.aidl.Helpers.Response;
import com.ody.aidl.Services.Display;
import com.ody.aidl.Services.Print;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class AIDLPollingActivity extends AppCompatActivity {
    private static final int GET_GALLERY = 1;
    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidlpolling);

        Button post = (Button) findViewById(R.id.aidlpolling_btn_post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = getContent();
                //String[] lines = content.split("|");
                if (bitmap != null) {
                    Response responses = Display.lcdBitmap(bitmap);
                    if (!responses.isSuccess()){
                        //Toast.makeText(AIDLPollingActivity.this,"Polling bitmap fail",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //Toast.makeText(AIDLPollingActivity.this, ""+ lines[0] + lines[1] , Toast.LENGTH_SHORT).show();
                    Display.lcdDouble(content, "line2");
                }
            }
        });

        Button bitmapbtn = (Button) findViewById(R.id.aidlpolling_btn_bitmap);
        bitmapbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                String[] mimeTypes = {"image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), GET_GALLERY);
            }
        });

        Button qr = (Button) findViewById(R.id.aidlpolling_btn_qr);
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(AIDLPollingActivity.this, "QR initiated" , Toast.LENGTH_SHORT).show();
                String content = getContent();
                if (content != null) {
                    try{
                        Bitmap newBitmap = QRGenerator.getInstance().run(content);
                        if (newBitmap != null) {
                            //byte[] bitmapAsBytes = Base64.decode(response.getsOutput(), Base64.DEFAULT);
                            bitmap = newBitmap;
                            ImageView ivQR = (ImageView) findViewById(R.id.aidlpolling_iv_qr);
                            ivQR.setImageBitmap(bitmap);
                            if (bitmap != null){
                                bitmap = Bitmap.createScaledBitmap(bitmap,35,35,false);
                            }
                        }
                    }catch (Exception e){
                        TextView log = findViewById(R.id.aidlpolling_tv_log);
                        Writer writer = new StringWriter();
                        e.printStackTrace(new PrintWriter(writer));
                        String s = writer.toString();
                        log.setText(s);
                    }
                }
            }
        });

    }

    public void wake(View view) {
        Display.wake();
    }

    public void sleep(View view) {
        Display.sleep();
    }

    public void clear(View view) {
        Display.lcdDouble("", "");
        bitmap = null;
        ImageView iv_bitmap = (ImageView) findViewById(R.id.aidlpolling_iv_qr);
        iv_bitmap.setImageResource(R.drawable.ody);
    }

    public String getContent() {
        EditText content = (EditText) findViewById(R.id.aidlpolling_edt_content);
        return content.getText().toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY) {
            Uri selectedImage = data.getData();
            bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ImageView ivThumbnailPhoto = findViewById(R.id.aidlpolling_iv_qr);
            ivThumbnailPhoto.setImageBitmap(bitmap);
        }
    }
}
