package com.ody.usb.Classes.Shared;

import com.ody.usb.Classes.RequestQueue;

import java.io.InputStream;

public abstract interface DeviceConnection {
    public abstract InputStream getInputStream();

    public abstract RequestQueue getQueue();
}
