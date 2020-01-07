package com.ody.usb.Helpers;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
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

    public int[] findDevices(Context context) {
        ArrayList<Integer> vendorId = new ArrayList();
        mUsbManager = ((UsbManager) context.getSystemService("usb"));

        HashMap<String, UsbDevice> usblist = mUsbManager.getDeviceList();
        Iterator<String> iterator = usblist.keySet().iterator();
        while (iterator.hasNext()) {
            mDevice = ((UsbDevice) usblist.get(iterator.next()));
            vendorId.add(mDevice.getVendorId());
            mDevice = null;
        }

        int[] ret = new int[vendorId.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = vendorId.get(i).intValue();
        }

        return ret;
    }
}
