package com.ody.aidl.Features;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ody.aidl.AIDLProvider;
import com.ody.aidl.Helpers.Response;
import com.ody.aidl.Utils.AidlUtil;

import java.io.File;

public class DigitalDisplay {
    private static DigitalDisplay mDigtaDigitalDisplay = new DigitalDisplay();
    private Response response;

    public static DigitalDisplay getInstance() {
        return mDigtaDigitalDisplay;
    }

    public Response displayWake() {
        try {
            response = AidlUtil.getInstance().lcdWake();
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in DigitalDisplay.displayWake()");
        } finally {
            //AidlUtil.getInstance().disconnectPrinterService(AIDLProvider.getApplication());
        }
        return response;
    }

    public Response displaySleep() {
        try {
            response = AidlUtil.getInstance().lcdSleep();
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in DigitalDisplay.displaySleep()");
        } finally {
           // AidlUtil.getInstance().disconnectPrinterService(AIDLProvider.getApplication());
        }
        return response;
    }

    public Response displayImage(String filePath) {
        try {
            //chack file exists
            File file = new File(filePath);
            if (file.exists()) {
                //convert to itty bitty something
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                response = AidlUtil.getInstance().lcdBitmap(bitmap);
            } else {
                response = Response.getInstance().compose(false, null, "File does not exist");
            }

        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in DigitalDisplay.lcdImage");
        } finally {
           //AidlUtil.getInstance().disconnectPrinterService(AIDLProvider.getApplication());
        }
        return response;
    }

    public Response displayImage(Bitmap bitmap) {
        try {
            //chack file exists
            //File file = new File(filePath);
            if (bitmap != null) {
                //convert to itty bitty something
                //Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                response = AidlUtil.getInstance().lcdBitmap(bitmap);
            } else {
                response = Response.getInstance().compose(false, null, "File does not exist");
            }

        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in DigitalDisplay.lcdImage");
        } finally {
            //AidlUtil.getInstance().disconnectPrinterService(AIDLProvider.getApplication());
        }
        return response;
    }

    public Response displaySingle(String content) {
        try {
            response = AidlUtil.getInstance().lcdSingle(content);
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in lcdSingle - DigitalDisplay");
        } finally {
            //AidlUtil.getInstance().disconnectPrinterService(AIDLProvider.getApplication());
        }
        return response;
    }


    public Response displayDouble(String lineOne, String lineTwo) {
        try {
            response = AidlUtil.getInstance().lcdDouble(lineOne, lineTwo);
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in lcdDouble - DigitalDisplay");
        } finally {
            //AidlUtil.getInstance().disconnectPrinterService(AIDLProvider.getApplication());
        }
        return response;
    }
}
