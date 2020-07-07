package com.ody.usb.Features;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.widget.Toast;

import com.ody.usb.Classes.Shared.ESCPOSPrinter;
import com.ody.usb.Classes.Shared.USBPort;
import com.ody.usb.Classes.Shared.USBPortConnection;
import com.ody.usb.Helpers.USB_Response;

import java.util.HashMap;
import java.util.Iterator;

public class Text {
    private static UsbDevice mDevice;
    private static UsbManager mUsbManager;
    private static USBPort port;
    USBPortConnection portConnection;
    ESCPOSPrinter posPrinter;
    private static Text mText = new Text();
    private Context mContext;

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

    public USB_Response plain(Context context, int anId, String content){
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
            //validate against anId
            mDevice = (UsbDevice)usblist.get(iterator.next());
            if(mDevice.getProductId() == anId)
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

}
