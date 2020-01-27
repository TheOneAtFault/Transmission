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

    public static void dispose(){
        mAidlUtil = null;
    }

    public void connectPrinterService(Context context) {
        this.context = context.getApplicationContext();
        Intent intent = new Intent();
        intent.setPackage(SERVICE＿PACKAGE);
        intent.setAction(SERVICE＿ACTION);
        context.getApplicationContext().startService(intent);
        context.getApplicationContext().bindService(intent, connService, Context.BIND_AUTO_CREATE);
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

    public Response printQr(String data, int modulesize, int errorlevel) {
        if (woyouService != null) {
            try {
                woyouService.setAlignment(1, null);//alignment set to 1 = center
                woyouService.printQRCode(data, modulesize, errorlevel, null);
                woyouService.setAlignment(0, null);//alignment set to 1 = center
                response = Response.getInstance().compose(true, null, "Success on: printQr - Aidl");
            } catch (RemoteException e) {
                response = Response.getInstance().compose(false, e, "Exception on: printQr - Aidl");
            }
        }
        else{
            response = Response.getInstance().compose(false, null, "Service is null on: printQr - Aidl");
        }
        return response;
    }

    public Response printBitmap(Bitmap bitmap, int orientation) {
        if (woyouService != null) {
            try {
                if (orientation == 0) {
                    woyouService.printBitmap(bitmap, null);
                } else {
                    woyouService.printBitmap(bitmap, null);
                }
                response = Response.getInstance().compose(true, null, "Success on: printBitmap - Aidl");
            } catch (RemoteException e) {
                response = Response.getInstance().compose(false, e, "Exception on: printBitmap - Aidl");
            }
        }
        else{
            response = Response.getInstance().compose(false, null, "Service is null on: printBitmap - Aidl");
        }
        return response;
    }

    public Response sendRawData(byte[] data) {
        if (woyouService != null) {
            try {
                woyouService.sendRAWData(data, null);
                response = Response.getInstance().compose(true, null, "Success on: sendRawData - Aidl");
            } catch (RemoteException e) {
                response = Response.getInstance().compose(false, e, "Exception on sendRawData - Aidl");
            }
        } else {
            response = Response.getInstance().compose(false, null, "Service is null on: sendRawData - Aidl");
        }
        return response;
    }

    //custom method
    public Response makeCut() {
        if (woyouService != null) {
            try {
                woyouService.cutPaper(null);
                response = Response.getInstance().compose(true, null, "Success on: makeCut - Aidl");
            } catch (RemoteException e) {
                response = Response.getInstance().compose(false, e, "Exception on: makeCut - Aidl");
            }
        } else {
            response = Response.getInstance().compose(false, null, "Exception on: makeCut - Aidl");
        }
        return response;
    }

    public Response padding(int howMuch){
        if (woyouService != null) {
            try {
                woyouService.lineWrap(howMuch, null);
                response = Response.getInstance().compose(true, null, "Success on: padding - Aidl");
            }
            catch (Exception e){
                response = Response.getInstance().compose(false, e, "Exception on: padding - Aidl");
            }
        }
        else
        {
            response = Response.getInstance().compose(false, null, "Service is null on: padding - Aidl");
        }
        return response;
    }

    public Response lcdSingle(String lineOne) {
        if (woyouService != null) {
            try {
                woyouService.sendLCDCommand(4); // clear screen
                woyouService.sendLCDString(lineOne, null);
                response = Response.getInstance().compose(true, null, "Success on: lcdSingle - Aidl");
            } catch (Exception e) {
                response = Response.getInstance().compose(false, e, "Exception on: lcdSingle - Aidl");
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
                response = Response.getInstance().compose(true, null, "Success on: lcdDouble - Aidl");
            } catch (Exception e) {
                response = Response.getInstance().compose(false, e, "Exception on: lcdDouble - Aidl");
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
                woyouService.sendLCDBitmap(bitmap, null);
                response = Response.getInstance().compose(true, null, "Success on: lcdBitmap - Aidl");
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
                response = Response.getInstance().compose(true, null, "Success on lcdWake - Aidl");
            } catch (Exception e) {
                response = Response.getInstance().compose(false, e, "Exception on lcdWake - Aidl");
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
                response = Response.getInstance().compose(true, null, "Success on: lcdSleep - Aidl");
            } catch (Exception e) {
                response = Response.getInstance().compose(false, e, "Exception on: lcdSleep - Aidl");
            }
        } else {
            response = Response.getInstance().compose(false, null, "Service is null on: lcdSleep - Aidl");
        }
        return response;
    }
}

