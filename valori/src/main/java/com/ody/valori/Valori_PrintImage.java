package com.ody.valori;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;
import java.util.Map;

public class Valori_PrintImage implements PrinterManager.PrinterManagerListener {
    private static Valori_PrintImage mValori_PrintImage;

    private static byte[] ImageContent;
    private static Bitmap mImage;

    private static PrinterManager mPrinterManager;

    public static Valori_PrintImage getInstance() {
        if (mValori_PrintImage == null) {
            mValori_PrintImage = new Valori_PrintImage();
        }
        return mValori_PrintImage;
    }

    public void print(Context context, String image){
        mPrinterManager = new PrinterManager((Activity) context,this);
        mPrinterManager.onPrinterStart();

        if (image != null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inTargetDensity = 160;
            options.inDensity = 160;
            Bitmap bitmap = BitmapFactory.decodeFile(image);
            mImage = bitmap;// Bitmap.createScaledBitmap(bitmap, 200, 200, false);
        }
    }

    @Override
    public void onServiceConnected() {
        mPrinterManager.printerInit();

        final Map<String,Integer> map = new HashMap<String,Integer>();
        map.put(PrinterManager.KEY_ALIGN, 0);
        map.put(PrinterManager.KEY_MARGINLEFT, 5);
        map.put(PrinterManager.KEY_MARGINRIGHT, 5);

        mPrinterManager.printBitmap(mImage,map);
        //mPrinterManager.onPrinterStop();
    }
}
