package com.ody.usb.Helpers;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import java.util.HashMap;
import java.util.Iterator;

public class ReturnDevices {
    private UsbDevice mDevice;
    private UsbManager mUsbManager;
    private String response = "";
    private static ReturnDevices devices = new ReturnDevices();

    public static ReturnDevices getInstance() {
        return devices;
    }

    public String findDevices(Context context){

        mUsbManager = ((UsbManager)context.getSystemService("usb"));

        HashMap<String, UsbDevice> usblist = mUsbManager.getDeviceList();
        Iterator<String> iterator = usblist.keySet().iterator();
        while (iterator.hasNext())
        {
            mDevice = ((UsbDevice)usblist.get(iterator.next()));
            response = response + "\n" + "VendorID: " +  mDevice.getVendorId() + "~" + "Device Name: " +  mDevice.getDeviceName() + "~" + "ProductID: " +  mDevice.getProductId() + "|";

            mDevice = null;
        }
        return response;
    }
}
