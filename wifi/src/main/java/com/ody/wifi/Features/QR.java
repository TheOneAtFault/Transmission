package com.ody.wifi.Features;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.zxing.BarcodeFormat;
import com.ody.wifi.Classes.ESCPOSPrinter;
import com.ody.wifi.Classes.LKPrint;
import com.ody.wifi.Classes.QR.Contents;
import com.ody.wifi.Classes.QR.QRCodeEncoder;
import com.ody.wifi.Classes.RequestHandler;
import com.ody.wifi.Classes.WiFiPort;
import com.ody.wifi.Helpers.Wifi_Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class QR {
    private static QR mQr;
    private Wifi_Response response;
    private int QR_CODE_DIM = 300;
    private Bitmap _bitmap;

    public static QR getInstance() {
        return mQr = new QR();
    }

    public Wifi_Response print(Context context, String IP, String data, boolean cut) {
        _bitmap = generate(data);
        if (_bitmap == null){
            return response;
        }

        Thread hThread;
        ESCPOSPrinter posPrinter;
        WiFiPort wifiPort;
        posPrinter = new ESCPOSPrinter();
        boolean connected;

        connected = false;
        wifiPort = WiFiPort.getInstance();

        //connect to wifi port
        try {
            wifiPort.connect(IP);
            connected = true;
        } catch (Exception e) {
            response = Wifi_Response.getInstance().compose(
                    false,
                    e,
                    "IOException on connecting to wifi printer - QR.print()."
            );
        }

        try {
            if(connected) {
                hThread = new Thread(new RequestHandler());
                hThread.start();

                int result = posPrinter.printBitmap(_bitmap, LKPrint.LK_ALIGNMENT_CENTER, 0);

                if (result == 0){
                    if(cut){
                        posPrinter.cutPaper();
                    }
                }else{
                    response = Wifi_Response.getInstance().compose(false, null,
                            "An error occurred in QR.print() - Wifi Module");
                }

                if (hThread.isAlive()) {
                    hThread.join(500);
                }

                if ((hThread != null) && (hThread.isAlive())) {
                    hThread.interrupt();
                    hThread = null;
                }

                response = Wifi_Response.getInstance().compose(
                        true,
                        null,
                        "Success."
                );
            }
        }
        catch (Exception e) {
            response = Wifi_Response.getInstance().compose(
                    false,
                    e,
                    "InterruptedException on connecting to wifi printer - QR.print()."
            );
        }
        finally {
            if (connected) {
                try {
                    wifiPort.disconnect();
                } catch (IOException e) {
                    response = Wifi_Response.getInstance().compose(
                            false,
                            e,
                            "IO Exception in Text.plain() - wifi"
                    );
                } catch (InterruptedException e) {
                    response = Wifi_Response.getInstance().compose(
                            false,
                            e,
                            "Interrupted Exception in Text.plain() - wifi"
                    );
                }
            }
        }

        return response;
    }

    private Bitmap generate(String data) {
        try {
            //Encode with a QR Code image
            QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(data,
                    null,
                    Contents.Type.TEXT,
                    BarcodeFormat.QR_CODE.toString(),
                    QR_CODE_DIM);
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();

            //Re-encode the bitmap, messy
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } catch (Exception e) {
            response = Wifi_Response.getInstance().compose(
                    false,
                    e,
                    "Exception in Wifi QR generation."
            );
        }
        return null;
    }
}
