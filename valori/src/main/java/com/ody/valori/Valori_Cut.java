package com.ody.valori;

import android.app.Activity;
import android.content.Context;

public class Valori_Cut implements PrinterManager.PrinterManagerListener {

    private static Valori_Cut mValori_Cut;
    public static final byte[] CUT = new byte[]{29, 86, 66, 0};
    private static PrinterManager mPrinterManager;

    public static Valori_Cut getInstance() {
        if (mValori_Cut == null) {
            mValori_Cut = new Valori_Cut();
        }
        return mValori_Cut;
    }

    public void cut(Context context){
        mPrinterManager = new PrinterManager((Activity) context,this);
        mPrinterManager.onPrinterStart();
    }

    @Override
    public void onServiceConnected() {
        mPrinterManager.printerInit();
        mPrinterManager.sendRAWData(CUT);
        //mPrinterManager.onPrinterStop();
    }
}
