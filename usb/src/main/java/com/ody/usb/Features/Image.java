package com.ody.usb.Features;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import androidx.annotation.Nullable;

import com.ody.usb.Classes.Shared.ESCPOSPrinter;
import com.ody.usb.Classes.Shared.USBPort;
import com.ody.usb.Classes.Shared.USBPortConnection;
import com.ody.usb.Helpers.Response;

import java.util.HashMap;
import java.util.Iterator;


public class Image {
    private static Image mImage = new Image();
    private Context mContext;
    private UsbManager mUsbManager;
    private USBPort port;
    private UsbDevice mDevice;
    private USBPortConnection portConnection;
    private boolean bHasPermission = false;
    private Response response = Response.getInstance();

    private Image() {
    }

    public static Image getInstance() {
        return mImage;
    }

    public void connectService(Context context) {
        mContext = context;
    }

    public Response plainImage(Context context, int vendorId, @Nullable Bitmap image){
        //set the application context passed from the call

        connectService(context);

        //register the required permission
        PendingIntent mPermissionIntent = PendingIntent.getBroadcast(context, 0,
                new Intent("com.genericusb.mainactivity.USB_PERMISSION"), 0);

        //setup manager and port
        mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        port = new USBPort(mUsbManager);

        //get device
        HashMap<String, UsbDevice> usblist = mUsbManager.getDeviceList();
        Iterator<String> iterator = usblist.keySet().iterator();

        while (iterator.hasNext()){
            mDevice = (UsbDevice)usblist.get(iterator.next());
            if (mDevice.getVendorId() == vendorId){
                //validate against vendorid
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

                        if (bHasPermission){
                            portConnection = port.connect_device(mDevice);
                            ESCPOSPrinter POSPrinter = new ESCPOSPrinter(portConnection);
                            //Get on with it
                            POSPrinter.printBitmap( 1, image); //0 - left align, 1 - center align, 2 - right align
                            response = Response.getInstance().compose(true,null,"Success");
                        }
                    }
                    catch(Exception e) {
                        response = Response.getInstance().compose(false,e,"Exception caught in Image.plainImage().");
                    }
                }
            }
        }
        return response;
    }
}
