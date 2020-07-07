package com.ody.usb.Services;

import android.content.Context;

import com.ody.usb.Features.Cutter;
import com.ody.usb.Features.Image;

public class USB_Cutter {
    public static void cut(Context context, int anId) {
        Cutter.getInstance().cut(context, anId);
    }
}
