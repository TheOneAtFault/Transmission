package com.ody.valori;

import android.app.Activity;
import android.content.Context;

public class Valori_Print_QR implements PrinterManager.PrinterManagerListener {
    private static Valori_Print_QR mInstance;

    private static String mData;

    private static PrinterManager mPrinterManager;

    public static Valori_Print_QR getInstance() {
        if (mInstance == null) {
            mInstance = new Valori_Print_QR();
        }
        return mInstance;
    }

    public void print(Context context, String data){
        mData = data;

        mPrinterManager = new PrinterManager((Activity) context,this);
        mPrinterManager.onPrinterStart();
    }

    @Override
    public void onServiceConnected() {
        mPrinterManager.printerInit();
        mPrinterManager.printQRCode(mData, 0, 300);
        //mPrinterManager.onPrinterStop();
    }
}
