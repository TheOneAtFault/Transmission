package com.ody.valori;

import android.app.Activity;
import android.content.Context;

public class Valori_DrawerKick implements PrinterManager.PrinterManagerListener {

    private static Valori_DrawerKick mValori_Drawer;
    public static final byte[] DRAWER = new byte[]{27, 112, 48, 55, 121};
    private static PrinterManager mPrinterManager;

    public static Valori_DrawerKick getInstance() {
        if (mValori_Drawer == null) {
            mValori_Drawer = new Valori_DrawerKick();
        }
        return mValori_Drawer;
    }

    public void kick(Context context){
        mPrinterManager = new PrinterManager((Activity) context,this);
        mPrinterManager.onPrinterStart();
    }

    @Override
    public void onServiceConnected() {
        mPrinterManager.printerInit();
        mPrinterManager.sendRAWData(DRAWER);
        //mPrinterManager.onPrinterStop();
    }
}
