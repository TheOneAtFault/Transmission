package com.ody.usb.Classes;

import java.io.InputStream;
import java.io.OutputStream;

public class PortMediator {
    private static PortMediator pm;
    private InputStream is;
    private OutputStream os;

    public static PortMediator getInstance()
    {
        if (pm == null) {
            pm = new PortMediator();
        }
        return pm;
    }

    public void setIs(InputStream is)
    {
        this.is = is;
    }

    public InputStream getIs()
    {
        return this.is;
    }

    public void setOs(OutputStream os)
    {
        this.os = os;
    }

    public OutputStream getOs()
    {
        return this.os;
    }
}
