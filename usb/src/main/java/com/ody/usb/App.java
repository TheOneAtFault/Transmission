package com.ody.usb;

import android.app.Application;
import android.content.Context;

import tw.com.prolific.driver.pl2303.PL2303Driver;

public class App extends Application {
    public static PL2303Driver mDriver;
    public static Context context;
}
