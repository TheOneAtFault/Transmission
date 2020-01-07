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
import com.ody.usb.Helpers.Response;

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

    private Text(){
    }

    public static Text getInstance(){
        return mText;
    }

    public void connectService(Context context){
        mContext = context;
    }


    /**
     * Print text with the usb module
     * @param context
     * @param vendorId
     * @param content
     * @return response object
     */
    public Response plain(Context context, int vendorId, String content){
        //set variables
        Response response = Response.getInstance();
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
                        response = Response.getInstance().compose(false,null,
                                "Device Permission requested.");
                    }

                    if(bHasPermission){
                        portConnection = port.connect_device(mDevice);

                        posPrinter = new ESCPOSPrinter(portConnection);
                        posPrinter.printNormal(content);

                        portConnection.close();
                        response = Response.getInstance().compose(true,null,"Success");
                    }
                }
                catch(Exception e) {
                    response = Response.getInstance().compose(true,e,
                            "Exception caught in Text.plain().");
                }
            }
        }
        return response;
    }
}
