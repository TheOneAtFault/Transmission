package com.ody.wifi.Classes;

import android.graphics.Bitmap;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ESCPOSPrinter {
    private OLEPOSCommand olepos;
    protected RequestQueue requestQueue;
    protected ESCPOS escpos;

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
