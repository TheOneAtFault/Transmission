package com.ody.wifi.Classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.IOException;

public class AndroidImageLoader implements ImageLoaderIF {

    private int thresHoldValue;
    private Bitmap bitmap;

    public int getThresHoldValue()
    {
        return this.thresHoldValue;
    }

    public int getThresHoldValue(int[][] tpix)
    {
        int width = tpix.length;
        int height = tpix[0].length;
        int minColor = 255;
        int maxColor = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++)
            {
                if (tpix[x][y] < minColor) {
                    minColor = tpix[x][y];
                }
                if (tpix[x][y] > maxColor) {
                    maxColor = tpix[x][y];
                }
            }
        }
        this.thresHoldValue = ((minColor + maxColor) / 2);
        return this.thresHoldValue;
    }

    public int[][] getByteArray(Bitmap image)
    {
        int maxColor = 0;
        int minColor = 255;
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] tpix = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++)
            {
                tpix[x][y] = convertRGB(image.getPixel(x, y));
                if (tpix[x][y] < minColor) {
                    minColor = tpix[x][y];
                }
                if (tpix[x][y] > maxColor) {
                    maxColor = tpix[x][y];
                }
            }
        }
        this.thresHoldValue = ((minColor + maxColor) / 2);
        return tpix;
    }

    private int convertRGB(int ARGB)
    {
        int[] rgb = new int[3];

        rgb[0] = ((ARGB & 0xFF0000) >> 16);
        rgb[1] = ((ARGB & 0xFF00) >> 8);
        rgb[2] = (ARGB & 0xFF);
        return rgb[0] * 3 + rgb[1] * 10 + rgb[2];
    }

    public Bitmap getImage(String filepath)
            throws IOException
    {
        this.bitmap = BitmapFactory.decodeFile(filepath);
        return this.bitmap;
    }

    public Bitmap getImage()
    {
        return this.bitmap;
    }

    public int[][] imageLoad(String filepath)
            throws IOException
    {
        int[][] array = null;
        Bitmap image = getImage(filepath);
        if (image != null) {
            array = getByteArray(image);
        }
        return array;
    }

    public int[][] imageLoad(@Nullable Bitmap bitmapImage)
            throws IOException {
        int[][] array = null;
        if (bitmapImage != null) {
            array = getByteArray(bitmapImage);
        }
        return array;
    }

    public int[][] imageLoad(byte[] barr)
    {
        int[][] array = null;
        Bitmap image = BitmapFactory.decodeByteArray(barr, 0, barr.length);
        if (image != null) {
            array = getByteArray(image);
        }
        return array;
    }
}
