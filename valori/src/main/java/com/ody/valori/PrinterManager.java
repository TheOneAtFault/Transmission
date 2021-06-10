package com.ody.valori;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.prints.printerservice.IPrinterCallback;
import com.prints.printerservice.IPrinterService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by guangyi.peng on 2017/3/1.
 */
public class PrinterManager {
    public static final int ALIGN_CENTER    = 0;
    public static final int ALIGN_LEFT      = 1;
    public static final int ALIGN_RIGHT     = 2;

    public static final int NORMAL = 0;
    public static final int BOLD = 1;
    public static final int SERIF = 2;
    public static final int ARIAL = 3;

    /**
     *
     *  <item>"NORMAL"</item>                   0
     *  <item>"BOLD"</item>                     1
     *  <item>"SERIF"</item>                    2
     *  <item>"ttf_Arial.ttf"</item>            3
     *  <item>"ttf_FangHei.ttf"</item>          4
     *  <item>"ttf_FranklinGothic.ttf"</item>   5
     *  <item>"ttf_Haettenschweiler.ttf"</item> 6
     *  <item>"ttf_HuaSong.ttf"</item>          7
     *  <item>"ttf_SansMono.ttf"</item>         8
     *
     * **/

    public final static String KEY_ALIGN = "key_attributes_align";
    public final static String KEY_TEXTSIZE = "key_attributes_textsize";
    public final static String KEY_TYPEFACE = "key_attributes_typeface";
    public final static String KEY_MARGINLEFT = "key_attributes_marginleft";
    public final static String KEY_MARGINRIGHT = "key_attributes_marginright";
    public final static String KEY_MARGINTOP = "key_attributes_margintop";
    public final static String KEY_MARGINBOTTOM = "key_attributes_marginbottom";
    public final static String KEY_LINESPACE = "key_attributes_linespace";
    public final static String KEY_WEIGHT = "key_attributes_weight";
    private static final String TAG = "PrinterSampleManager";

    public interface PrinterManagerListener{
        public void onServiceConnected();
    }
    public PrinterManager(Activity activity,PrinterManagerListener listener){
        this.mActivity = activity;
        this.mContext = activity.getApplicationContext();
        this.mListener = listener;
    }
    private Activity mActivity;
    private Context mContext;
    private PrinterManagerListener mListener;

    private IPrinterCallback mCallback = null;
    private IPrinterService mPrinterService;
    private boolean mPrinterServiceConnected = false;
    public static long sCurrentLength = 0;
    public static long sTotalLength = 0;

    private ServiceConnection mConnectionService = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG,"onServiceDisconnected");
            mPrinterService = null;
            mPrinterServiceConnected = false;
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG,"onServiceConnected");
            mPrinterService = IPrinterService.Stub.asInterface(service);
            mListener.onServiceConnected();
            mPrinterServiceConnected = true;
        }
    };
    public boolean hasXChengPrinter(Context context){
        boolean hasXChengPrinter = false;
        final String PKG_XCHENG = "com.prints.printerservice";
        List<String> packagesName = getInstalledPackagesName(context);
        if(packagesName.contains(PKG_XCHENG)){
            hasXChengPrinter = true;
        }
        return hasXChengPrinter;
    }
    private static List<String> getInstalledPackagesName(Context context){
        List<String> packagesName = new ArrayList<String>();
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        packagesName.clear();
        for (PackageInfo pi : packages) {
            packagesName.add(pi.packageName);
        }
        return packagesName;
    }
    private IPrinterService getPrinterService(){
        if(mPrinterService==null){
            //reconnect printerservice
            Intent intent=new Intent();
            intent.setPackage("com.prints.printerservice");
            intent.setAction("com.prints.printerservice.IPrinterService");
            mActivity.startService(intent);
            mActivity.bindService(intent, mConnectionService, Context.BIND_AUTO_CREATE);
        }
        return mPrinterService;
    }
    private void printTextSample(String content,IPrinterCallback callback){
        IPrinterService service = getPrinterService();
        if(service!=null&&mPrinterServiceConnected){
            try {
                service.printText(content, callback);
            }catch (Exception e){
            }
        }
    }

    public void onPrinterStart(){
        mCallback = new IPrinterCallback.Stub() {
            @Override
            public void onException(int code, final String msg) throws RemoteException {
                Log.i(TAG,"onException code="+code+" msg="+msg);
            }

            @Override
            public void onLength(long current, long total) throws RemoteException {
                sCurrentLength = current;
                sTotalLength = total;
            }

            @Override
            public void onComplete() throws RemoteException {
                Log.i(TAG,"onComplete");
            }
        };

        Intent intent=new Intent();
        intent.setPackage("com.prints.printerservice");
        intent.setAction("com.prints.printerservice.IPrinterService");
        mActivity.startService(intent);
        mActivity.bindService(intent, mConnectionService, Context.BIND_AUTO_CREATE);
    }
    public void onPrinterStop(){
        ///*
        try{
            mActivity.unbindService(mConnectionService);
        }catch(Exception e){

        }finally{
            mActivity.finish();
        }
        //*/
        //mActivity.finish();
    }

    public void sendRAWData(final byte[] data){
        try {
            mPrinterService.sendRAWData(data, mCallback);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void printText(final String text){
        try {
            mPrinterService.printText(text, mCallback);
        }catch(Exception e){

        }
    }

    public void printTextWithAttributes(final String text,final Map attributes){
        try {
            mPrinterService.printTextWithAttributes(text, attributes, mCallback);
        }catch(Exception e){

        }
    }

    public void printColumnsTextWithAttributes(final String[] text,final List attributes){
        try {
            mPrinterService.printColumnsTextWithAttributes(text, attributes, mCallback);
        }catch(Exception e){

        }
    }
    public void printBarCode(final String content,final int align,final int width,final int height,final boolean showContent){
        try {
            mPrinterService.printBarCode(content, align, width, height, showContent, mCallback);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void printQRCode(final String text ,final int align,final int size){
        try {
            mPrinterService.printerInit(mCallback);
            mPrinterService.printQRCode(text, align, size, mCallback);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void printBitmap(final Bitmap bitmap){
        try {
            mPrinterService.printBitmap(bitmap, mCallback);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void printBitmap(final Bitmap bitmap,final Map attributes){
        try {
            mPrinterService.printBitmapWithAttributes(bitmap, attributes, mCallback);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void printWrapPaper(final int n){
        try {
            mPrinterService.printWrapPaper(n, mCallback);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setPrinterSpeed(final int level){
        try {
            mPrinterService.setPrinterSpeed(level, mCallback);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void upgradePrinter(){
        try {
            mPrinterService.upgradePrinter();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getFirmwareVersion(){
        try {
            return mPrinterService.getFirmwareVersion();
        }catch(RemoteException e){

        }
        return "";
    }

    public String getBootloaderVersion(){
        try {
            return mPrinterService.getBootloaderVersion();
        }catch(RemoteException e){

        }
        return "";
    }

    public void printerInit(){
        try {
            mPrinterService.printerInit(mCallback);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.i(TAG,"printerInit error="+e);
        }
    }

    public void printerReset(){
        try {
            mPrinterService.printerReset(mCallback);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public int printerTemperature(final Activity activity){
        int temperature = -1;
        try {
            temperature = mPrinterService.printerTemperature(mCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return temperature;
    }
    public boolean printerPaper(){
        boolean hasPaper = false;
        try {
            hasPaper = mPrinterService.printerPaper(mCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return hasPaper;
    }
}
