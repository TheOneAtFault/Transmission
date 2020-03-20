package com.ody.usb.Services;

import android.content.Context;

import com.ody.usb.Features.Text;
import com.ody.usb.Helpers.USB_Response;

import tw.com.prolific.driver.pl2303.PL2303Driver;

public class USB_Polling {
    /**
     * USB_Print plain text to POS Printer.
     *
     * @param context   = external application context(Auto-initialization failed me, </3)
     * @param vendorId  = printing device id
     * @param data      = body of text to print
     * @return USB_Response Instance
     */
    public static USB_Response write(Context context, int vendorId, String data) {
        return Text.getInstance().display(context, vendorId, data);
    }
}
