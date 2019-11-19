package com.ody.aidl.Services;

import android.graphics.Bitmap;

import com.ody.aidl.Features.Image;
import com.ody.aidl.Features.QR;
import com.ody.aidl.Features.Text;
import com.ody.aidl.Helpers.Response;
import com.ody.aidl.StartUp;
import com.ody.aidl.Utils.AidlUtil;

public class Print {
    private static Response response;

    public static Response qr(String data, boolean cut) {
        try {
            response = QR.getInstance().print(data, null, null, cut);
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Print - QR");
        }
        finally {
            AidlUtil.getInstance().disconnectPrinterService(StartUp.getApplication());
        }
        return response;
    }

    public static Response image(Bitmap data, boolean cut) {
        try {
            response = Image.getInstance().print(data, 0, 0, cut);
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Print - Image");
        }
        finally {
            AidlUtil.getInstance().disconnectPrinterService(StartUp.getApplication());
        }
        return response;
    }

    public static Response text(String content, boolean cut) {
        try {
            response = Text.getInstance().plain(content, cut);
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Print - Text");
        }
        finally {
            AidlUtil.getInstance().disconnectPrinterService(StartUp.getApplication());
        }
        return response;
    }
}
