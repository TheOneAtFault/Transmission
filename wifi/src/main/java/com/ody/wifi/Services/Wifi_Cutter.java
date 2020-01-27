package com.ody.wifi.Services;

import android.content.Context;

import com.ody.wifi.Features.Cutter;
import com.ody.wifi.Helpers.Wifi_Response;

public class Wifi_Cutter {
    public static Wifi_Response cut(Context context, String address) {
       return Cutter.getInstance().cut(context, address);
    }
}
