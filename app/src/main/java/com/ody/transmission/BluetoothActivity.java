package com.ody.transmission;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.ody.valori.PrinterManager;
import com.ody.valori.Valori_Cut;
import com.ody.valori.Valori_DrawerKick;
import com.ody.valori.Valori_PrintImage;
import com.ody.valori.Valori_Print_QR;
import com.ody.valori.Valori_Print_Text;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.UUID;

public class BluetoothActivity extends AppCompatActivity  {
    private PrinterManager mPrinterManager;
    private static final int GET_FROM_GALLERY = 1;
    private static BluetoothSocket btsocket;
    private static OutputStream outputStream;

    public static final String PRINTER_ADDRESS = "11:22:33:44:55:66";
    public static final UUID PRINTER_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public static final byte[] CUT = new byte[]{29, 86, 66, 0};
    public static final byte[] DRAWER = new byte[]{27, 112, 48, 55, 121};

    private Button mBitmap;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        mContext = this;

        Button image = (Button)findViewById(R.id.bluetooth_print_image);
        Button cut = (Button)findViewById(R.id.bluetooth_cut);
        Button drawer = (Button)findViewById(R.id.bluetooth_drawer_kick);
        Button qr = (Button)findViewById(R.id.bluetooth_qr);

        mBitmap = (Button) findViewById(R.id.bluetooth_print_image);

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

        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Red();
            }
        });

        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawer();
            }
        });


        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Qr();
            }
        });



        Toast.makeText(this, "ready", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FROM_GALLERY) {
            //content URI
            if(data != null){
                Uri selectedImage = data.getData();
                Bitmap bitmap = null;
                Uri uri;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //ImageView ivThumbnailPhoto = findViewById(R.id.aidl_iv_image);
                //ivThumbnailPhoto.setImageBitmap(bitmap);
                try {
                    uri = MediaStore.Images.Media.getContentUri(data.getDataString());

                    Uri selectedImageUri = data.getData( );
                    String picturePath = getPath( this.getApplicationContext( ), selectedImageUri );
                    Toast.makeText(mContext, picturePath, Toast.LENGTH_SHORT).show();
                    Blue(picturePath);
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


    public  void Drawer(){
        try {
            Valori_DrawerKick.getInstance().kick(mContext);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public  void Red(){
        try {
            Valori_Cut.getInstance().cut(mContext);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void Blue(String bb){
        try {
            Valori_PrintImage.getInstance().print(mContext, bb);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Qr(){
        try {
            Valori_Print_Text.getInstance().print(mContext, "");
            Valori_Print_QR.getInstance().print(mContext, "www.odyssey.com");
        }catch (Exception e){
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    public static String getPath( Context context, Uri uri ) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver( ).query( uri, proj, null, null, null );
        if(cursor != null){
            if ( cursor.moveToFirst( ) ) {
                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
                result = cursor.getString( column_index );
            }
            cursor.close( );
        }
        if(result == null) {
            result = "Not found";
        }
        return result;
    }

}