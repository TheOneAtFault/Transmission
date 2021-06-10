package com.ody.valori;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;
import java.util.Map;

public class Valori_Print_Text implements PrinterManager.PrinterManagerListener {
    private static Valori_Print_Text mValori_Print_text;

    private static byte[] mData;

    private static PrinterManager mPrinterManager;

    public static Valori_Print_Text getInstance() {
        if (mValori_Print_text == null) {
            mValori_Print_text = new Valori_Print_Text();
        }
        return mValori_Print_text;
    }

    public void print(Context context, String data){
        mData = data.getBytes();

        mPrinterManager = new PrinterManager((Activity) context,this);
        mPrinterManager.onPrinterStart();
    }

    @Override
    public void onServiceConnected() {
        mPrinterManager.printerInit();
        mPrinterManager.sendRAWData(mData);
        //mPrinterManager.onPrinterStop();
    }
}
