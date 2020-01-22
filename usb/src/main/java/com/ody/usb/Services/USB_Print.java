package com.ody.usb.Services;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import com.ody.usb.Features.Image;
import com.ody.usb.Features.QR;
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
        USB_Response USBResponse = Text.getInstance().plain(context, vendorId, data);
        return USBResponse;
    }

    /**
     * USB_Print image file.
     *
     * @param context   = external application context(You know the story!)
     * @param vendorId  = printing device id
     * @param image = image file path
     * @return
     */
    public static USB_Response image(Context context, int vendorId, @Nullable Bitmap image) {
        USB_Response USBResponse = Image.getInstance().plainImage(context, vendorId, image);
        return USBResponse;
    }

    public static USB_Response qr(Context context, int vendorId, String content){
        USB_Response aa = QR.getInstance().plain(context, vendorId, content);
        return aa;
    }
}
