package com.ody.usb.Helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class USB_QRGenerator {
    private static USB_QRGenerator mQRenerator;
    private USB_Response response;

    public static USB_QRGenerator getInstance() {
        return mQRenerator = new USB_QRGenerator();
    }

    public USB_Response run(String data) {
        USB_Response response = null;
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 150, 150);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray();
            Bitmap bitmap = BitmapFactory.decodeByteArray(pngData, 0, pngData.length);
            response = USB_Response.getInstance().compose(true, null,"in qr generate - success");
        }
        catch (Exception e){
            response = USB_Response.getInstance().compose(false, e,"in qr generate");
        }
        return response;
    }
}
