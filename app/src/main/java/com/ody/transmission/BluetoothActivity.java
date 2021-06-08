package com.ody.transmission;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.ody.bluetooth.PrinterManager;
import com.ody.bluetooth.ThreadPoolManager;
import com.ody.bluetooth.Valori_CUT;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //ImageView ivThumbnailPhoto = findViewById(R.id.aidl_iv_image);
                //ivThumbnailPhoto.setImageBitmap(bitmap);
                try {
                    Blue(bitmap);
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

        Valori_CUT.kickDrawer();
    }

    public  void Red(){
        try {
            Valori_CUT.printerCut();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void Blue(Bitmap bb){
        //Valori_CUT.printImage();

//        final Bitmap imgAsBitmap = bb;
//
//        final Map<String, Integer> map = new HashMap<String, Integer>();
//        map.put(PrinterManager.KEY_ALIGN, 0);
//        map.put(PrinterManager.KEY_MARGINLEFT, 5);
//        map.put(PrinterManager.KEY_MARGINRIGHT, 5);
//
//        ThreadPoolManager.getInstance().executeTask(new Runnable() {
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                try {
//                    mPrinterManager.printBitmap(imgAsBitmap, map);
//                } catch (Exception e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                    Toast.makeText(mContext, "Nope", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }

}