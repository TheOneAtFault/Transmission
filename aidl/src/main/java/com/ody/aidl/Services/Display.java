package com.ody.aidl.Services;

import android.graphics.Bitmap;

import com.ody.aidl.Helpers.Response;
import com.ody.aidl.StartUp;
import com.ody.aidl.Utils.AidlUtil;

public class Display {
    private static Response response;

    public static Response wake() {
        try {
            response = AidlUtil.getInstance().lcdWake();
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Display.wake()");
        } finally {
            AidlUtil.getInstance().disconnectPrinterService(StartUp.getApplication());
        }
        return response;
    }

    public static Response sleep() {
        try {
            response = AidlUtil.getInstance().lcdSleep();
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Display.sleep()");
        } finally {
            AidlUtil.getInstance().disconnectPrinterService(StartUp.getApplication());
        }
        return response;
    }

    public static Response lcdBitmap(Bitmap bitmap) {
        try {
            response = AidlUtil.getInstance().lcdBitmap(bitmap);
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in lcdBitmap");
        } finally {
            AidlUtil.getInstance().disconnectPrinterService(StartUp.getApplication());
        }
        return response;
    }

    public static Response lcdSingle(String content) {
        try {
            response = AidlUtil.getInstance().lcdSingle(content);
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in lcdSingle - Polling Display");
        } finally {
            AidlUtil.getInstance().disconnectPrinterService(StartUp.getApplication());
        }
        return response;
    }

    public static Response lcdDouble(String lineOne, String lineTwo) {
        try {
            response = AidlUtil.getInstance().lcdDouble(lineOne, lineTwo);
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in lcdDouble - Polling Display");
        } finally {
            AidlUtil.getInstance().disconnectPrinterService(StartUp.getApplication());
        }
        return response;
    }
}
