package com.ody.wifi.Classes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class WiFiPort implements PortInterface{
    private static int defaultPort = 9100;
    private Socket socket;
    private static WiFiPort wifiPort;
    private OutputStream os;
    private InputStream is;
    private int timeout = 3000;

    public static WiFiPort getInstance()
    {
        if (wifiPort == null) {
            wifiPort = new WiFiPort();
        }
        return wifiPort;
    }

    public WiFiPortConnection open(String address)
            throws IOException
    {
        return open(address, defaultPort);
    }

    private WiFiPortConnection open(String address, int port)
            throws IOException
    {
        InetSocketAddress socketAdress = new InetSocketAddress(InetAddress.getByName(address), port);
        Socket socket = new Socket();


        socket.connect(socketAdress, this.timeout);
        return new WiFiPortConnection(socket);
    }

    public void connect(String address, int port)
            throws IOException
    {
        PortMediator pm = PortMediator.getInstance();
        InetSocketAddress socketAdress = new InetSocketAddress(InetAddress.getByName(address), port);
        this.socket = new Socket();
        this.socket.connect(socketAdress, this.timeout);

        this.os = this.socket.getOutputStream();
        this.is = this.socket.getInputStream();
        pm.setIs(this.is);
        pm.setOs(this.os);
    }

    public void connect(String address)
            throws IOException
    {
        connect(address, defaultPort);
    }

    public InputStream getInputStream()
            throws IOException
    {
        if (this.socket != null) {
            this.is = this.socket.getInputStream();
        }
        return this.is;
    }

    public OutputStream getOutputStream()
            throws IOException
    {
        if (this.socket != null) {
            this.os = this.socket.getOutputStream();
        }
        return this.os;
    }

    public void disconnect()
            throws IOException, InterruptedException
    {
        int count = 0;
        RequestQueue rq = RequestQueue.getInstance();
        while ((!rq.isEmpty()) && (count < 4))
        {
            Thread.sleep(100L);
            count++;
        }
        if (this.os != null)
        {
            this.os.close();
            this.os = null;
        }
        if (this.is != null)
        {
            this.is.close();
            this.is = null;
        }
        if (this.socket != null)
        {
            this.socket.close();
            this.socket = null;
        }
    }

    public boolean isConnected()
    {
        if (this.socket != null) {
            return this.socket.isConnected();
        }
        return false;
    }
}
