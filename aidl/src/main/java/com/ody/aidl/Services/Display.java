package com.ody.aidl.Services;

import android.graphics.Bitmap;

import com.ody.aidl.Features.DigitalDisplay;
import com.ody.aidl.Helpers.Response;

public class Display {
    private static Response response;

    public static Response wake() {
        try {
            response = DigitalDisplay.getInstance().displayWake();
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Display.wake()");
        }
        return response;
    }

    public static Response sleep() {
        try {
            response = DigitalDisplay.getInstance().displaySleep();
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Display.sleep()");
        }
        return response;
    }

    public static Response lcdBitmap(String file) {
        try {
            response = DigitalDisplay.getInstance().displayImage(file);
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in lcdBitmap");
        }
        return response;
    }

    public static Response lcdBitmap(Bitmap file) {
        try {
            response = DigitalDisplay.getInstance().displayImage(file);
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in lcdBitmap");
        }
        return response;
    }

    public static Response lcdSingle(String content) {
        try {
            response = DigitalDisplay.getInstance().displaySingle(content);
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in lcdSingle - Polling Display");
        }
        return response;
    }

    public static Response lcdDouble(String lineOne, String lineTwo) {
        try {
            response = DigitalDisplay.getInstance().displayDouble(lineOne, lineTwo);
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in lcdDouble - Polling Display");
        }
        return response;
    }

    public static void lcdQR(String qrData) {
        try {
            DigitalDisplay.getInstance().displayQR(qrData);
        } catch (Exception e) {
            //response = Response.getInstance().compose(false, e, "Exception in lcdQR - Polling Display");
        }
    }

    public static void lcdBranding(String qrData, String image) {
        try {
            DigitalDisplay.getInstance().displayBranding(qrData, image);
        } catch (Exception e) {
            //response = Response.getInstance().compose(false, e, "Exception in lcdDouble - Polling Display");
        }
    }

    public static void lcdDrawText(String item, String value) {
        try {
            DigitalDisplay.getInstance().drawText(item, value);
        } catch (Exception e) {
            //response = Response.getInstance().compose(false, e, "Exception in lcdDouble - Polling Display");
        }
    }

}
