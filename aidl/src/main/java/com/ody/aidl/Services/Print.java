package com.ody.aidl.Services;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import com.ody.aidl.Features.Image;
import com.ody.aidl.Features.QR;
import com.ody.aidl.Features.Text;
import com.ody.aidl.Helpers.Response;

public class Print {
    private static Response response;

    public static Response qr(String data, boolean cut, int padding) {
        try {
            response = QR.getInstance().print(data, null, null, cut, padding);
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Print - QR");
        }
        return response;
    }
    //image as string
    public static Response image(@Nullable String data, boolean cut, int padding) {
        try {
            response = Image.getInstance().print(data, 0, 0, cut, padding);
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Print - Image");
        }
        return response;
    }
    //image as bitmap
    public static Response image(@Nullable Bitmap data, boolean cut, int padding) {
        try {
            response = Image.getInstance().print(data, 0, 0, cut, padding);
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Print - Image");
        }
        return response;
    }

    public static Response text(String content, boolean cut, int padding) {
        try {
            response = Text.getInstance().plain(content, cut, padding);
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Print - Text");
        }
        return response;
    }

    public static Response cut() {
        try {
            response = Text.getInstance().cutter();
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Print - Text");
        }
        return response;
    }
}
