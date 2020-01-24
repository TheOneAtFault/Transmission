package com.ody.wifi.Services;

import android.content.Context;

import com.ody.wifi.Features.Cutter;

public class Wifi_Cutter {
    public static void cut(Context context, String address) {
        Cutter.getInstance().cut(context, address);
    }
}
