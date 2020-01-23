package com.ody.usb.Classes.Shared;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import com.ody.usb.Classes.Image.ImageLoader;
import com.ody.usb.Classes.Image.MobileImageConverter;
import com.ody.usb.Classes.RequestQueue;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ESCPOSPrinter {
    private OLEPOSCommand olepos;
    private DeviceConnection connection;
    private String charSet;
    protected ESCPOS escpos;
    protected RequestQueue requestQueue;

    //constructor
    public ESCPOSPrinter() {
        this("ISO-8859-1");
    }

    public ESCPOSPrinter(String charset) {
        this.charSet = charset;
        this.escpos = new ESCPOS();
        this.requestQueue = RequestQueue.getInstance();
        this.olepos = new OLEPOSCommand(charset, this.requestQueue);
    }

    public ESCPOSPrinter(DeviceConnection connection) {
        this("ISO-8859-1", connection);
    }

    public ESCPOSPrinter(String charset, DeviceConnection connection) {
        this.charSet = charset;
        this.escpos = new ESCPOS();
        this.requestQueue = connection.getQueue();
        this.connection = connection;
        this.olepos = new OLEPOSCommand(charset, this.requestQueue);
    }

    public void printNormal(String data)
            throws UnsupportedEncodingException {
        this.olepos.parseJposCMD(data);
    }

    public int printBitmap( int alignment, @Nullable Bitmap image)
            throws IOException {
        return printBitmap(alignment, 0, 0, image);
    }

    private int printBitmap(int alignment, int size, int mode,@Nullable Bitmap image)
            throws IOException
    {
        ImageLoader imageLoader = new ImageLoader();
        int[][] img = imageLoader.imageLoad(image);
        if (img != null)
        {
            MobileImageConverter mConverter = new MobileImageConverter();
            byte[] bimg = mConverter.convertBitImage(img, imageLoader.getThresHoldValue());

            this.requestQueue.addRequest(this.escpos.ESC_a(alignment));

            this.requestQueue.addRequest(this.escpos.GS_v(size, mConverter.getxL(), mConverter.getxH(), mConverter.getyL(), mConverter.getyH(), bimg));
            this.requestQueue.addRequest(this.escpos.ESC_a(0));
            return 0;
        }
        return -1;
    }

    public void cutPaper(){
        this.requestQueue.addRequest(this.escpos.ESC_cut());
    }
}