package com.ody.aidl.Utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import com.ody.aidl.AIDLProvider;
import com.ody.aidl.Helpers.Response;

import java.lang.ref.WeakReference;

import woyou.aidlservice.jiuiv5.IWoyouService;

public class AidlUtil {
    private static final String SERVICE＿PACKAGE = "woyou.aidlservice.jiuiv5";
    private static final String SERVICE＿ACTION = "woyou.aidlservice.jiuiv5.IWoyouService";

    private IWoyouService woyouService;
    private static AidlUtil mAidlUtil = new AidlUtil();
    private Context context;
    private Response response;

    private AidlUtil() {
    }

    public static AidlUtil getInstance() {
        return mAidlUtil;
    }

    public void connectPrinterService(WeakReference<Context> context) {
        this.context = context.get().getApplicationContext();
        Intent intent = new Intent();
        intent.setPackage(SERVICE＿PACKAGE);
        intent.setAction(SERVICE＿ACTION);
        context.get().getApplicationContext().startService(intent);
        context.get().getApplicationContext().bindService(intent, connService, Context.BIND_AUTO_CREATE);
    }

    public void disconnectPrinterService(Context context) {
        if (woyouService != null) {
            context.getApplicationContext().unbindService(connService);
            woyouService = null;
        }
    }

    public boolean isConnect() {
        return woyouService != null;
    }

    private ServiceConnection connService = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            woyouService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            woyouService = IWoyouService.Stub.asInterface(service);
        }
    };

    public void printQr(String data, int modulesize, int errorlevel) {
        if (woyouService == null) {
            Toast.makeText(context, "Service is null on: printQr", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            woyouService.setAlignment(1, null);//alignment set to 1 = center
            woyouService.printQRCode(data, modulesize, errorlevel, null);
            woyouService.lineWrap(3, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void printBitmap(Bitmap bitmap, int orientation) {
        if (woyouService == null) {
            //re-establish
            connectPrinterService(AIDLProvider.getCurContext());
            //Toast.makeText(context, "Service is null on: printBitmap", Toast.LENGTH_LONG).show();
            //return;
            if (woyouService == null){
                Toast.makeText(context, "Service is null on: printBitmap", Toast.LENGTH_LONG).show();
                return;
            }
        }

        try {
            if (orientation == 0) {
                woyouService.printBitmap(bitmap, null);
                //woyouService.printText("ImageTextHere\n", null);
                woyouService.lineWrap(3, null);
            } else {
                woyouService.printBitmap(bitmap, null);
                //woyouService.printText("\nImageTextHere orientation != 0\n", null);
                woyouService.lineWrap(3, null);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public Response sendRawData(byte[] data) {
        if (woyouService != null) {
            try {
                woyouService.sendRAWData(data, null);
                response = Response.getInstance().compose(true, null, "Success");
            } catch (RemoteException e) {
                response = Response.getInstance().compose(false, e, "Exception in sendRawData - Aidl");
            }
        } else {
            response = Response.getInstance().compose(false, null, "Service is null on: lcdSingle - Aidl");
        }
        return response;
    }

    //custom method
    public void makeCut() {
        if (woyouService != null) {
            Toast.makeText(context, "Make Cut", Toast.LENGTH_SHORT);
            try {
                woyouService.cutPaper(null);
            } catch (RemoteException e) {
                Toast.makeText(context, "RemoteException on: makeCut", Toast.LENGTH_SHORT);
            }
        } else {
            Toast.makeText(context, "Service is null on: makeCut", Toast.LENGTH_SHORT);
        }
    }

    public Response lcdSingle(String lineOne) {
        if (woyouService != null) {
            try {
                woyouService.sendLCDCommand(4); // clear screen
                woyouService.sendLCDString(lineOne, null);
                response = Response.getInstance().compose(true, null, "Success");
            } catch (Exception e) {
                response = Response.getInstance().compose(false, e, "Exception in lcdSingle - Aidl");
            }
        } else {
            response = Response.getInstance().compose(false, null, "Service is null on: lcdSingle - Aidl");
        }
        return response;
    }

    public Response lcdDouble(String lineOne, String lineTwo) {
        if (woyouService != null) {
            try {
                woyouService.sendLCDCommand(4); //clear screen
                woyouService.sendLCDDoubleString(lineOne, lineTwo, null);
                response = Response.getInstance().compose(true, null, "Success");
            } catch (Exception e) {
                response = Response.getInstance().compose(false, e, "Service is null on: lcdDouble - Aidl");
            }
        } else {
            response = Response.getInstance().compose(false, null, "Service is null on: lcdDouble - Aidl");
        }
        return response;
    }

    public Response lcdBitmap(Bitmap bitmap) {
        if (woyouService != null) {
            try {
                woyouService.sendLCDCommand(4);
                response = Response.getInstance().compose(true, null, "Success");
            } catch (Exception e) {
                response = Response.getInstance().compose(false, e, "Exception on: lcdBitmap - Aidl");
            }
        } else {
            response = Response.getInstance().compose(false, null, "Service is null on: lcdBitmap - Aidl");
        }
        return response;
    }

    public Response lcdWake() {
        if (woyouService != null) {
            try {
                woyouService.sendLCDCommand(2);
                response = Response.getInstance().compose(true, null, "Success");
            } catch (Exception e) {
                response = Response.getInstance().compose(false, e, "Exception in lcdWake - Aidl");
            }
        } else {
            response = Response.getInstance().compose(false, null, "Service is null on: lcdWake - Aidl");
        }
        return response;
    }

    public Response lcdSleep() {
        if (woyouService != null) {
            try {
                woyouService.sendLCDCommand(3);
                response = Response.getInstance().compose(true, null, "Success");
            } catch (Exception e) {
                response = Response.getInstance().compose(false, e, "Exception on: lcdSleep - Aidl");
            }
        } else {
            response = Response.getInstance().compose(false, null, "Service is null on: lcdSleep - Aidl");
        }
        return response;
    }
}

