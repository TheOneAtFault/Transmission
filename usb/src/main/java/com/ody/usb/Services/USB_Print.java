package com.ody.usb.Services;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import com.ody.usb.Features.Image;
import com.ody.usb.Features.QR;
import com.ody.usb.Features.PrintJob;
import com.ody.usb.Features.Text;
import com.ody.usb.Helpers.USB_Response;

public class USB_Print {
    /**
     * USB_Print plain text to POS Printer.
     *
     * @param context   = external application context(Auto-initialization failed me, </3)
     * @param vendorId  = printing device id
     * @param data      = body of text to print
     * @return USB_Response Instance
     */
    public static USB_Response textPlain(Context context, int vendorId, String data) {
        return Text.getInstance().plain(context, vendorId, data);
    }

    /**
     * USB_Print image file.
     *
     * @param context   = external application context(You know the story!)
     * @param vendorId  = printing device id
     * @param data = image file path
     * @return
     */
    public static USB_Response image(Context context, int vendorId, @Nullable Bitmap data, boolean cut, int padding) {
        return Image.getInstance().plainImage(context, vendorId, data, cut, padding);
    }

    public static USB_Response image(Context context, int vendorId, String data, boolean cut, int padding) {
        return Image.getInstance().plainImage(context, vendorId, data, cut, padding);
    }

    public static USB_Response qr(Context context, int vendorId, String data){
        return QR.getInstance().plain(context, vendorId, data);
    }

    public static USB_Response slip(Context context, int vendorId, String imagePath, String body, String qrData, boolean cut, int padding){
        PrintJob.getInstance().main(context, vendorId);
        return null;
    }
}
