package com.ody.aidl.Features;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import com.ody.aidl.Helpers.Response;
import com.ody.aidl.Utils.AidlUtil;

public class QR {
    static private int print_size = 5;
    static private int error_level = 3;
    private static Response response;
    private static QR mQR;

    public static QR getInstance() {
        return mQR = new QR();
    }

    /**
     * print QR code using aidl bluetooth service. QR is set to center align.
     *
     * @param content    = QR code information
     * @param printSize  = QR code display size, default set to 8
     * @param errorLevel = QR error level default set to 3(30%)
     * @param cut        = printer function to cut paper
     * @return response
     */
    public Response print(String content, @Nullable Integer printSize, @Nullable Integer errorLevel, boolean cut, int padding) {
        if (printSize != null) {
            print_size = printSize;
        }

        if (errorLevel != null) {
            error_level = errorLevel;
        }

        try {

            if (content == "") {
                content = "https://www.odysseypos.co.za";
            }

            /**
             * content = content
             * print_size
             * error_level => dimension correction level 0-3
             *                0- Error correction level L (7%)
             *                1- Error correction level M (15%)
             *                2- Error correction level Q (25%)
             *                3- Error correction level H (30%)
             **/
            AidlUtil.getInstance().printQr(content, print_size, error_level);
            if (cut) {
                AidlUtil.getInstance().padding(padding);
                AidlUtil.getInstance().makeCut();
            }
            else{
                AidlUtil.getInstance().padding(padding);
            }

            response = Response.getInstance().compose(true, null, "success");
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception caught in QR Print");
        }

        return response;
    }
}
