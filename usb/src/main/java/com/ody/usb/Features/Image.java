package com.ody.usb.Features;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import androidx.annotation.Nullable;

import com.ody.usb.Classes.Shared.ESCPOSPrinter;
import com.ody.usb.Classes.Shared.USBPort;
import com.ody.usb.Classes.Shared.USBPortConnection;
import com.ody.usb.Helpers.USB_Response;

import java.util.HashMap;
import java.util.Iterator;


public class Image {
    private static Image mImage;
    private Context mContext;
    private UsbManager mUsbManager;
    private USBPort port;
    private UsbDevice mDevice;
    private USBPortConnection portConnection;
    private boolean bHasPermission = false;
    private USB_Response USBResponse = USB_Response.getInstance();

    private Image() {
    }

    public static Image getInstance() {
        return mImage = new Image();
    }

    public void connectService(Context context) {
        mContext = context;
    }

    public USB_Response plainImage(Context context, int vendorId, @Nullable Bitmap image) {
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

        while (iterator.hasNext()) {
            mDevice = (UsbDevice) usblist.get(iterator.next());
            //validate against vendorid
            if (mDevice.getVendorId() == vendorId) {
                try {
                    if (mUsbManager.hasPermission(mDevice)) {
                        bHasPermission = true;
                    } else {
                        bHasPermission = false;
                        mUsbManager.requestPermission(mDevice, mPermissionIntent);
                        bHasPermission = true;
                        USBResponse = USB_Response.getInstance().compose(false, null,
                                "Device Permission requested.");
                    }

                    if (bHasPermission) {
                        portConnection = port.connect_device(mDevice);
                        ESCPOSPrinter POSPrinter = new ESCPOSPrinter(portConnection);
                        //Get on with it
                        POSPrinter.printBitmap(1, image); //0 - left align, 1 - center align, 2 - right align
                        USBResponse = USB_Response.getInstance().compose(true, null, "Success");
                    }
                } catch (Exception e) {
                    USBResponse = USB_Response.getInstance().compose(false, e, "Exception caught in Image.plainImage().");
                }
            }
        }
        return USBResponse;
    }

    public USB_Response plainImage(Context context, int vendorId, @Nullable String image) {
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

        while (iterator.hasNext()) {
            mDevice = (UsbDevice) usblist.get(iterator.next());
            //validate against vendorid
            if (mDevice.getVendorId() == vendorId) {
                try {
                    if (mUsbManager.hasPermission(mDevice)) {
                        bHasPermission = true;
                    } else {
                        bHasPermission = false;
                        mUsbManager.requestPermission(mDevice, mPermissionIntent);
                        bHasPermission = true;
                        USBResponse = USB_Response.getInstance().compose(false, null,
                                "Device Permission requested.");
                    }

                    if (bHasPermission) {
                        portConnection = port.connect_device(mDevice);
                        ESCPOSPrinter POSPrinter = new ESCPOSPrinter(portConnection);
                        //Get on with it
                        Bitmap bitmap = BitmapFactory.decodeFile(image);
                        POSPrinter.printBitmap(1, bitmap); //0 - left align, 1 - center align, 2 - right align
                        USBResponse = USB_Response.getInstance().compose(true, null, "Success");
                    }
                } catch (Exception e) {
                    USBResponse = USB_Response.getInstance().compose(false, e, "Exception caught in Image.plainImage().");
                }
            }
        }
        return USBResponse;
    }
}
