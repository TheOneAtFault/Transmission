package com.ody.wifi.Classes;

import java.io.IOException;

public abstract interface ImageLoaderIF {
    public abstract int getThresHoldValue();

    public abstract int getThresHoldValue(int[][] paramArrayOfInt);

    public abstract int[][] imageLoad(String paramString)
            throws IOException;
}
