package com.ody.usb.Services;

import android.content.Context;

import com.ody.usb.Helpers.ReturnDevices;

public class Devices {
    public static int[] getAll(Context context){
        return ReturnDevices.getInstance().findDevices(context);
    }
}
