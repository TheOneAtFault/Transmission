package com.ody.wifi.Features;

import com.ody.wifi.Classes.ESCPOSPrinter;
import com.ody.wifi.Classes.LKPrint;
import com.ody.wifi.Classes.RequestHandler;
import com.ody.wifi.Classes.WiFiPort;
import com.ody.wifi.Helpers.Response;

import java.io.IOException;

public class Image {

    private static Image mImage;
    private Response response;

    public static Image getInstance() {
        return mImage;
    }

    public Response print(String IP, String Path, int nTimeOut) {

        Thread hThread;
        ESCPOSPrinter posPrinter;
        WiFiPort wifiPort;
        posPrinter = new ESCPOSPrinter();
        Boolean connected;

        connected = false;
        wifiPort = WiFiPort.getInstance();

        //connect to wifi port
        try {

            wifiPort.connect(IP);
            connected = true;

            if (connected) {
                hThread = new Thread(new RequestHandler());
                hThread.start();

                posPrinter.printBitmap(Path, LKPrint.LK_ALIGNMENT_CENTER);

                if (hThread.isAlive()) {
                    hThread.join(nTimeOut);
                }

                if ((hThread != null) && (hThread.isAlive())) {
                    hThread.interrupt();
                    hThread = null;
                }

                response = Response.getInstance().compose(true, null,
                        "Success.");
            }
            else{
                response = Response.getInstance().compose(true, null,
                        "Not connected.");
            }

        } catch (IOException e) {
            response = Response.getInstance().compose(false, e,
                    "IOException on connecting to wifi print.");
        } catch (InterruptedException e) {
            response = Response.getInstance().compose(false, e,
                    "InterruptedException on connecting to wifi print.");
        }

        return response;
    }
}
