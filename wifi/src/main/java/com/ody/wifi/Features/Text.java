package com.ody.wifi.Features;

import com.ody.wifi.Classes.ESCPOSPrinter;
import com.ody.wifi.Classes.RequestHandler;
import com.ody.wifi.Classes.WiFiPort;
import com.ody.wifi.Helpers.Response;

import java.io.IOException;
//todo: get done
public class Text {
    private static Text mText = new Text();
    private Response response;

    public static Text getInstance() {
        return mText;
    }

    public Response plain(String ip, String data) {
        Thread hThread;
        ESCPOSPrinter escposPrinter;
        WiFiPort wifiPort;
        escposPrinter = new ESCPOSPrinter();
        Boolean connected;
        String message = "failed";

        connected = false;
        wifiPort = WiFiPort.getInstance();

        try {
            wifiPort.connect(ip);
            connected = true;
            message = "Wifi Connected";

            if (connected) {
                hThread = new Thread(new RequestHandler());
                hThread.start();

                try {

                    //			if (logoPath != ""){
                    //				posPtr.printBitmap(logoPath, LKPrint.LK_ALIGNMENT_CENTER);
                    //			}

                    escposPrinter.printNormal(data);
                    message = "printed";

                } catch (IOException e) {
                    message = e.getMessage();
                }

                if (hThread.isAlive()) {
                    try {
                        hThread.join(500);
                    } catch (InterruptedException e) {
                        message = e.getMessage();
                    }
                }

                if ((hThread != null) && (hThread.isAlive())) {
                    hThread.interrupt();
                    hThread = null;
                }


                //////////////
                if (connected) {
                    try {
                        wifiPort.disconnect();
                        message = "Wifi disconnected";
                    } catch (IOException e) {
                        message = e.getMessage();
                    } catch (InterruptedException e) {
                        message = e.getMessage();
                    }
                }

            }

        } catch (IOException e) {
            response = Response.getInstance().compose(false, e, "Exception in Text.plain()");
        }
        return response;
    }
}
