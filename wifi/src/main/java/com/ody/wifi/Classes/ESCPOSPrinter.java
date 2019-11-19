package com.ody.wifi.Classes;

import android.graphics.Bitmap;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ESCPOSPrinter {
    private OLEPOSCommand olepos;
    protected RequestQueue requestQueue;
    protected ESCPOS escpos;

    public int printBitmap(String bitmapName, int alignment)
            throws IOException
    {
        return printBitmap(bitmapName, alignment, 0, 0);
    }

    public int printBitmap(String bitmapName, int alignment, int size)
            throws IOException
    {
        return printBitmap(bitmapName, alignment, size, 0);
    }

    private int printBitmap(String bitmapName, int alignment, int size, int mode)
            throws IOException
    {
        ImageLoader imageLoader = new ImageLoader();
        int[][] img = imageLoader.imageLoad(bitmapName);
        if (img != null)
        {
            MobileImageConverter mConverter = new MobileImageConverter();
            byte[] bimg = mConverter.convertBitImage(img, imageLoader.getThresHoldValue());
            this.requestQueue.addRequest(this.escpos.ESC_a(alignment));


            this.requestQueue.addRequest(this.escpos.GS_v(size, mConverter.getxL(), mConverter.getxH(), mConverter.getyL(), mConverter.getyH(), bimg));
            return 0;
        }
        return -1;
    }

    public int printBitmap(Bitmap bitmap, int alignment, int size)
            throws IOException
    {
        ImageLoader imageLoader = new ImageLoader();
        MobileImageConverter mConverter = new MobileImageConverter();
        int[][] img = imageLoader.getByteArray(bitmap);
        if (img != null)
        {
            byte[] bimg = mConverter.convertBitImage(img, imageLoader.getThresHoldValue());
            this.requestQueue.addRequest(this.escpos.ESC_a(alignment));
            this.requestQueue.addRequest(this.escpos.GS_v(size, mConverter.getxL(), mConverter.getxH(), mConverter.getyL(), mConverter.getyH(), bimg));

            return 0;
        }
        return -1;
    }

    public void printNormal(String data)
            throws UnsupportedEncodingException
    {
        this.olepos.parseJposCMD(data);
    }

}
