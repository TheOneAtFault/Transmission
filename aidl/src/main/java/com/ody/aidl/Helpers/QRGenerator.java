package com.ody.aidl.Helpers;

import android.graphics.Bitmap;
import android.util.Base64;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.ody.aidl.Classes.QR.Contents;
import com.ody.aidl.Classes.QR.QRCodeEncoder;

import java.io.ByteArrayOutputStream;

public class QRGenerator {
    private static QRGenerator mQRenerator;
    private Response response;

    public static QRGenerator getInstance() {
        return mQRenerator = new QRGenerator();
    }

    public static Bitmap run(String data) {
        int qrCodeDimention = 40;
        Bitmap bitmap = null;
        try {
            //size of screen: 128*40
            QRCodeEncoder qrCodeEncoder =
                    new QRCodeEncoder(data, null, Contents.Type.TEXT,
                            BarcodeFormat.QR_CODE.toString(), qrCodeDimention);

            bitmap = qrCodeEncoder.encodeAsBitmap();

            ByteArrayOutputStream OutputStreamAsByteArray = new ByteArrayOutputStream();
            //bitmap.compress(Bitmap.CompressFormat.PNG, 100, OutputStreamAsByteArray);
            byte[] toByteArray = OutputStreamAsByteArray.toByteArray();
            //response = Response.getInstance().compose(true, null, Base64.encodeToString(toByteArray, Base64.DEFAULT));
        } catch (WriterException e) {
            //response = Response.getInstance().compose(false, e, "WriterException in QRGenerator.run()");
        } catch (Exception e) {
            //response = Response.getInstance().compose(false, e, "Exception in QRGenerator.run()");
        }

        return bitmap;
    }
}
