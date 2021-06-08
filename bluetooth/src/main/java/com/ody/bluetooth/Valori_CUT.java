package com.ody.bluetooth;

import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Valori_CUT extends Application implements PrinterManager.PrinterManagerListener {
    private static PrinterManager mPrinterManager = null;

    private static Valori_CUT ourInstance = new Valori_CUT();

    private static BluetoothSocket btsocket;
    private static OutputStream outputStream;
    // bluetooth stuff to remove
    public static final String PRINTER_ADDRESS = "11:22:33:44:55:66";
    public static final UUID PRINTER_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private static final byte[] CUT = new byte[]{29, 86, 66, 0};
    private static final byte[] DRAWER = new byte[]{27, 112, 48, 55, 121};

    static Context _mContext;

    private Valori_CUT(){}

    public static Valori_CUT getInstance() {
        if (ourInstance == null){ //if there is no instance available... create new one
            ourInstance = new Valori_CUT();
        }
        return ourInstance;
    }

//    public Valori_CUT() {
//        _mContext = context.getApplicationContext();
//
//        try {
//            mPrinterManager = new PrinterManager((Activity) context, this);
//            mPrinterManager.onPrinterStart();
//        } catch (Exception e) {
//            Toast.makeText(_mContext, "stop", Toast.LENGTH_SHORT).show();
//            Toast.makeText(_mContext, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
//        }
//
//        Toast.makeText(_mContext, "ready", Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void onServiceConnected() {
        mPrinterManager.printerInit();
        Toast.makeText(_mContext, "connected", Toast.LENGTH_SHORT).show();
    }

    public static void kickDrawer() {
        try {
            mPrinterManager.sendRAWData(DRAWER);
        } catch (Exception e) {
            Toast.makeText(_mContext, "kickDD", Toast.LENGTH_SHORT).show();
        }
    }

    public static void printerCut() {
        try {
            mPrinterManager.sendRAWData(CUT);
        } catch (Exception e) {
            Toast.makeText(_mContext, "cut", Toast.LENGTH_SHORT).show();
        }
    }

    public static void printImage(String image) {

        if (image == null) {
            return;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inTargetDensity = 160;
        options.inDensity = 160;
        Bitmap bitmap = BitmapFactory.decodeFile(image);
        bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, false);

        final Bitmap imgAsBitmap = bitmap;

        final Map<String, Integer> map = new HashMap<String, Integer>();
        map.put(PrinterManager.KEY_ALIGN, 0);
        map.put(PrinterManager.KEY_MARGINLEFT, 5);
        map.put(PrinterManager.KEY_MARGINRIGHT, 5);

        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    mPrinterManager.printBitmap(imgAsBitmap, map);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(_mContext, "Nope", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
