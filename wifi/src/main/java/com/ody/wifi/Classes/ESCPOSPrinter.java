package com.ody.wifi.Classes;

import android.graphics.Bitmap;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ESCPOSPrinter {
    private OLEPOSCommand olepos;
    private RequestQueue requestQueue;
    private ESCPOS escpos;
    private DeviceConnection connection;
    private String charSet;
    //constructor
    public ESCPOSPrinter() {
        this("ISO-8859-1");
    }

    private ESCPOSPrinter(String charset) {
        this.charSet = charset;
        this.escpos = new ESCPOS();
        this.requestQueue = RequestQueue.getInstance();
        this.olepos = new OLEPOSCommand(charset, this.requestQueue);
    }

    public ESCPOSPrinter(DeviceConnection connection) {
        this("ISO-8859-1", connection);
    }

    private ESCPOSPrinter(String charset, DeviceConnection connection) {
        this.charSet = charset;
        this.escpos = new ESCPOS();
        this.requestQueue = connection.getQueue();
        this.connection = connection;
        this.olepos = new OLEPOSCommand(charset, this.requestQueue);
    }

    public int printBitmap(Bitmap bitmap, int alignment, int size)
            throws IOException
    {
        return printBitmap(bitmap, alignment, size, 0);
    }

    private int printBitmap(Bitmap data, int alignment, int size, int mode)
            throws IOException
    {
        ImageLoader imageLoader = new ImageLoader();
        int[][] img = imageLoader.imageLoad(data);
        if (img != null)
        {
            MobileImageConverter mConverter = new MobileImageConverter();
            byte[] bimg = mConverter.convertBitImage(img, imageLoader.getThresHoldValue());
            this.requestQueue.addRequest(this.escpos.ESC_a(alignment));
            this.requestQueue.addRequest(this.escpos.GS_v(size, mConverter.getxL(), mConverter.getxH(), mConverter.getyL(), mConverter.getyH(), bimg));
            this.requestQueue.addRequest(this.escpos.ESC_d(1));//line feed
            this.requestQueue.addRequest(this.escpos.ESC_AT());
            //reset alignment
            this.requestQueue.addRequest(this.escpos.ESC_a(0));
            return 0;
        }
        return -1;
    }

    public void printNormal(String data)
            throws UnsupportedEncodingException
    {
        this.olepos.parseJposCMD(data);
    }

    public void cutPaper(){
        this.requestQueue.addRequest(this.escpos.ESC_cut());
    }
}
