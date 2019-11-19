package com.ody.wifi.Classes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

public class WiFiPortConnection implements DeviceConnection {
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private RequestQueue queue;
    private Thread requestHandler;

    protected WiFiPortConnection(Socket socket)
            throws IOException
    {
        if ((socket != null) && (socket.isConnected()))
        {
            this.inputStream = socket.getInputStream();
            this.outputStream = socket.getOutputStream();
            this.socket = socket;
            this.queue = new RequestQueue();

            this.requestHandler = new Thread(new SenderThread());
            this.requestHandler.start();
        }
    }

    public void close()
            throws InterruptedException, IOException
    {
        int count = 0;
        while ((!this.queue.isEmpty()) && (count < 3))
        {
            Thread.sleep(100L);
            count++;
        }
        if (this.outputStream != null) {
            this.outputStream.close();
        }
        if (this.inputStream != null) {
            this.inputStream.close();
        }
        if (this.socket != null) {
            this.socket.close();
        }
        this.socket = null;
        if ((this.requestHandler != null) && (this.requestHandler.isAlive())) {
            this.requestHandler.interrupt();
        }
    }

    public boolean isConnected()
    {
        if (this.socket != null) {
            return this.socket.isConnected();
        }
        return false;
    }

    public InputStream getInputStream()
    {
        return this.inputStream;
    }

    private OutputStream getOutputStream()
    {
        return this.outputStream;
    }

    public RequestQueue getQueue()
    {
        return this.queue;
    }

    public void setTimeOut(int timeout)
            throws SocketException
    {
        if (this.socket != null) {
            this.socket.setSoTimeout(timeout);
        }
    }

    class SenderThread
            implements Runnable
    {
        SenderThread() {}

        public void run()
        {
            try
            {
                OutputStream os = WiFiPortConnection.this.getOutputStream();
                while (!Thread.currentThread().isInterrupted())
                {
                    byte[] data = WiFiPortConnection.this.queue.dequeue().getRequestData();
                    os.write(data);
                    os.flush();
                    Thread.sleep(10L);
                }
            }
            catch (Exception e)
            {
                WiFiPortConnection.this.queue.clearQueue();
            }
        }
    }
}
