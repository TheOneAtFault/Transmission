package com.ody.wifi.Classes;

import java.io.InputStream;

public abstract interface DeviceConnection {
    public abstract InputStream getInputStream();

    public abstract RequestQueue getQueue();
}
