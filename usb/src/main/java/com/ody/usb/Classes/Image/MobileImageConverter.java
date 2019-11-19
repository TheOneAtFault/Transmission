package com.ody.usb.Classes.Image;

public class MobileImageConverter {
    private int xL;
    private int xH;
    private int yL;
    private int yH;

    public int getxL()
    {
        return this.xL;
    }

    public int getxH()
    {
        return this.xH;
    }

    public int getyL()
    {
        return this.yL;
    }

    public int getyH()
    {
        return this.yH;
    }

    private int pow(int n, int times)
    {
        int retVal = 1;
        for (int i = 0; i < times; i++) {
            retVal *= n;
        }
        return retVal;
    }

    public int getByteWidth(int iWidth)
    {
        int byteWidth = iWidth / 8;
        if (iWidth % 8 != 0) {
            byteWidth++;
        }
        return byteWidth;
    }

    public void setLHLength(int byteWidth, int iHeight)
    {
        this.xL = (byteWidth % 256);
        this.xH = (byteWidth / 256);
        this.yL = (iHeight % 256);
        this.yH = (iHeight / 256);
        if (this.xH > 255) {
            this.xH = 255;
        }
        if (this.yH > 8) {
            this.yH = 8;
        }
    }

    public byte[] convertBitImage(int[][] tpix, int thresHoldValue)
    {
        int index = 0;
        int width = tpix.length;
        int height = tpix[0].length;
        int byteWidth = getByteWidth(width);
        setLHLength(byteWidth, height);
        byte[] result = new byte[byteWidth * height];
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                if ((x != 0) && (x % 8 == 0)) {
                    index++;
                }
                if (tpix[x][y] <= thresHoldValue) {
                    result[index] = ((byte)(result[index] | (byte)pow(2, 7 - x % 8)));
                }
            }
            index++;
        }
        return result;
    }
}
