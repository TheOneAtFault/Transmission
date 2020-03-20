package com.ody.usb.Features;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ody.usb.App;
import com.ody.usb.Classes.Shared.ESCPOSPrinter;
import com.ody.usb.Classes.Shared.USBPort;
import com.ody.usb.Classes.Shared.USBPortConnection;
import com.ody.usb.Helpers.USB_Response;
import com.ody.usb.ServiceWorkers.PollingForHarambe;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import tw.com.prolific.driver.pl2303.PL2303Driver;

public class Text {
    private static UsbDevice mDevice;
    private static UsbManager mUsbManager;
    private static USBPort port;
    USBPortConnection portConnection;
    ESCPOSPrinter posPrinter;
    private static Text mText = new Text();
    private Context mContext;
    PL2303Driver mSerialConnction;

    private PL2303Driver.BaudRate mBaudrate = PL2303Driver.BaudRate.B9600;
    private PL2303Driver.DataBits mDataBits = PL2303Driver.DataBits.D8;
    private PL2303Driver.Parity mParity = PL2303Driver.Parity.NONE;
    private PL2303Driver.StopBits mStopBits = PL2303Driver.StopBits.S1;
    private PL2303Driver.FlowControl mFlowControl = PL2303Driver.FlowControl.OFF;

    private static final String ACTION_USB_PERMISSION = "com.genericusb.mainactivity.USB_PERMISSION";
    private String TAG = "PLDriverWorkOfArt";

    private Text(){
    }

    public static Text getInstance(){
        return mText;
    }

    public void connectService(Context context){
        mContext = context;
    }

    public void disconnectService(){
        mContext = null;
    }

    /**
     * USB_Print text with the usb module
     * @param context
     * @param vendorId
     * @param content
     * @return response object
     */
    public USB_Response display(Context context, int vendorId, String content){
        App.context = context;
        //set variables
        content = content;
        USB_Response USBResponse = USB_Response.getInstance();
        boolean bHasPermission = false;

        //set the application context passed from the call
        connectService(context);

        //register the required permission
        PendingIntent mPermissionIntent = PendingIntent.getBroadcast(context, 0,
                new Intent("com.genericusb.mainactivity.USB_PERMISSION"), 0);

        //setup manager and port
        mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        port = new USBPort(mUsbManager);

        HashMap<String, UsbDevice> usblist = mUsbManager.getDeviceList();
        Iterator<String> iterator = usblist.keySet().iterator();

        while (iterator.hasNext())
        {
            //validate against vendorid
            mDevice = (UsbDevice)usblist.get(iterator.next());

            if(mDevice.getVendorId() == vendorId)
            {
                try
                {
                    if (mUsbManager.hasPermission(mDevice))
                    {
                            App.mDriver = new PL2303Driver((UsbManager) context.getSystemService(Context.USB_SERVICE), context, ACTION_USB_PERMISSION);
                            openSesame();
                            Toast.makeText(App.context,"After port opened",Toast.LENGTH_SHORT).show();

                            Intent serviceIntent = new Intent(context, PollingForHarambe.class);
                            /*Bundle bundle = new Bundle();
                            bundle.putString("data",content);
                            serviceIntent.putExtras(bundle);*/
                            context.startService(serviceIntent);

                            Toast.makeText(App.context,"After service worker started",Toast.LENGTH_SHORT).show();

                            USBResponse = USB_Response.getInstance().compose(true,null,"Success");
                    }
                    else
                    {
                        mUsbManager.requestPermission(mDevice, mPermissionIntent);
                        USBResponse = USB_Response.getInstance().compose(false,null,
                                "Device Permission requested.");
                    }
                }
                catch(Exception e) {
                    USBResponse = USB_Response.getInstance().compose(true,e,
                            "Exception caught in Text.plain().");
                }
            }
        }

        disconnectService();
        return USBResponse;
    }

    public USB_Response plain(Context context, int vendorId, String content){
        //set variables
        content = content;
        USB_Response USBResponse = USB_Response.getInstance();
        boolean bHasPermission = false;

        //set the application context passed from the call
        connectService(context);

        //register the required permission
        PendingIntent mPermissionIntent = PendingIntent.getBroadcast(context, 0,
                new Intent("com.genericusb.mainactivity.USB_PERMISSION"), 0);

        //setup manager and port
        mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        port = new USBPort(mUsbManager);

        HashMap<String, UsbDevice> usblist = mUsbManager.getDeviceList();
        Iterator<String> iterator = usblist.keySet().iterator();

        while (iterator.hasNext())
        {
            //validate against vendorid
            mDevice = (UsbDevice)usblist.get(iterator.next());
            if(mDevice.getVendorId() == vendorId)
            {
                try
                {
                    if (mUsbManager.hasPermission(mDevice))
                    {
                        bHasPermission = true;
                    }
                    else
                    {
                        bHasPermission = false;
                        mUsbManager.requestPermission(mDevice, mPermissionIntent);
                        bHasPermission = true;
                        USBResponse = USB_Response.getInstance().compose(false,null,
                                "Device Permission requested.");
                    }

                    if(bHasPermission){
                        portConnection = port.connect_device(mDevice);

                        posPrinter = new ESCPOSPrinter(portConnection);
                        posPrinter.printNormal(content);

                        portConnection.close();
                        USBResponse = USB_Response.getInstance().compose(true,null,"Success");
                    }
                }
                catch(Exception e) {
                    USBResponse = USB_Response.getInstance().compose(true,e,
                            "Exception caught in Text.plain().");
                }
            }
        }

        disconnectService();
        return USBResponse;
    }

    public void openSesame(){
        try {
            mSerialConnction = App.mDriver;
            if (!mSerialConnction.PL2303USBFeatureSupported()) {
                Log.e("Whaaaaat","Double what!");
            }
            if(null==mSerialConnction)
                return ;

       /* if(!mSerialConnction.isConnected())
            return;*/
            if (mSerialConnction.PL2303USBFeatureSupported()) {
                if(!mSerialConnction.isConnected()) {
                    if( !mSerialConnction.enumerate() ) {

                        Toast.makeText(mContext, "no more devices found", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        Log.d(TAG, "onResume:enumerate succeeded! - lies");
                    }
                }//if isConnected

                int res = 0;
                try {
                    res = mSerialConnction.setup(mBaudrate, mDataBits, mStopBits, mParity, mFlowControl);
                    Toast.makeText(App.context,"Prost",Toast.LENGTH_SHORT).show();
                } catch (Exception ep) {
                    // TODO Auto-generated catch block
                    ep.printStackTrace();
                }
            }
        }catch (Exception main){
            Log.e(TAG, "openSesame: text fkd", main );
        }
    }

    public void poofGone(){
        if (App.mDriver != null){
            App.mDriver.end();
        }
    }
}
