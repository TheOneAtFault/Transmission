package com.ody.wifi.Services;

import com.ody.wifi.Features.Connection;

public class Wifi_Check_Connection {
    public static boolean check(String address){

        return Connection.getInstance().check(address);
    }
}
