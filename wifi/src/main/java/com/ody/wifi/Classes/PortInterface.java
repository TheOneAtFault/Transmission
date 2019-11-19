package com.ody.wifi.Classes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract interface PortInterface {
    public abstract void connect(String paramString)
            throws IOException;

    public abstract void disconnect()
            throws IOException, InterruptedException;

    public abstract InputStream getInputStream()
            throws IOException;

    public abstract OutputStream getOutputStream()
            throws IOException;

    public abstract boolean isConnected();
}
