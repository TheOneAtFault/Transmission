package com.ody.wifi.Features;

import android.content.Context;

import com.ody.wifi.Classes.ESCPOSPrinter;
import com.ody.wifi.Classes.RequestHandler;
import com.ody.wifi.Classes.WiFiPort;
import com.ody.wifi.Helpers.Wifi_Response;

import java.io.IOException;

//todo: get done
public class Text {
    private static Text mText = new Text();
    private Wifi_Response response;

    public static Text getInstance() {
        return mText = new Text();
    }

    public Wifi_Response plain(String ip, String data, boolean cut) {
        Thread hThread;
        ESCPOSPrinter posPrinter = new ESCPOSPrinter();
        WiFiPort wifiPort = WiFiPort.getInstance();
        boolean connected = false;

        try {
            wifiPort.connect(ip);
            connected = true;

            hThread = new Thread(new RequestHandler());
            hThread.start();

            try {
                posPrinter.printNormal(data);

                if (cut) {
                    posPrinter.cutPaper();
                }

                response = Wifi_Response.getInstance().compose(
                        true,
                        null,
                        "Success"
                );
            } catch (IOException e) {
                response = Wifi_Response.getInstance().compose(
                        false,
                        e,
                        "Exception in Text.plain() - wifi"
                );
            }

            if (hThread.isAlive()) {
                try {
                    hThread.join(500);
                } catch (InterruptedException e) {
                    response = Wifi_Response.getInstance().compose(
                            false,
                            e,
                            "Exception in Text.plain() threading section - wifi"
                    );
                }
            }

            if ((hThread != null) && (hThread.isAlive())) {
                hThread.interrupt();
                hThread = null;
            }

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

        } catch (Exception e) {
            response = Wifi_Response.getInstance().compose(
                    false,
                    e,
                    "Global Exception in Text.plain() - Wifi"
            );
        }
        return response;
    }
}
