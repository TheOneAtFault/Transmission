package com.ody.wifi.Classes;

import java.io.IOException;
import java.io.OutputStream;

public class RequestHandler implements Runnable {
    private OutputStream os;
    private RequestQueue rq;
    private static final int bufSize = 368;

    public RequestHandler()
    {
        this.rq = RequestQueue.getInstance();
        this.rq.clearQueue();
    }

    private void smallWrite(byte[] b)
            throws IOException, InterruptedException
    {
        this.os.write(b);
        this.os.flush();
        Thread.sleep(10L);
    }

    private void bigWrite(byte[] b)
            throws IOException, InterruptedException
    {
        this.os.write(b);
        this.os.flush();
        Thread.sleep(100L);
    }

    public void run()
    {
        byte[] dummy = new byte[3];
        long lastTime = System.currentTimeMillis();
        try
        {
            this.os = PortMediator.getInstance().getOs();

            smallWrite(dummy);
            Thread.sleep(400L);
            while (!Thread.currentThread().isInterrupted())
            {
                byte[] data = this.rq.dequeue().getRequestData();
                if (System.currentTimeMillis() - lastTime > 59000L)
                {
                    smallWrite(dummy);
                    lastTime = System.currentTimeMillis();
                    Thread.sleep(400L);
                }
                if (data.length < 368) {
                    smallWrite(data);
                } else {
                    bigWrite(data);
                }
            }
        }
        catch (InterruptedException e)
        {
            this.rq.clearQueue();
        }
        catch (Exception localException) {}
    }
}
