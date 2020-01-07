package com.ody.usb.Services;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import com.ody.usb.Features.Image;
import com.ody.usb.Features.Text;
import com.ody.usb.Helpers.Response;

public class Print {
    /**
     * Print plain text to POS Printer.
     *
     * @param context   = external application context(Auto-initialization failed me, </3)
     * @param vendorId  = printing device id
     * @param data      = body of text to print
     * @return Response Instance
     */
    public static Response textPlain(Context context, int vendorId, String data) {
        Response response = Text.getInstance().plain(context, vendorId, data);
        return response;
    }

    /**
     * Print image file.
     *
     * @param context   = external application context(You know the story!)
     * @param vendorId  = printing device id
     * @param imagePath = image file path
     * @return
     */
    public static Response image(Context context, int vendorId, @Nullable Bitmap image) {
        Response response = Image.getInstance().plainImage(context, vendorId, image);
        return response;
    }
}
