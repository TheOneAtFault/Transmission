package com.ody.wifi.Features;

import com.ody.wifi.Classes.ESCPOSPrinter;
import com.ody.wifi.Classes.RequestHandler;
import com.ody.wifi.Classes.WiFiPort;
import com.ody.wifi.Helpers.Wifi_Response;

import java.io.IOException;

public class Cutter {
    private static Cutter mCutter;
    private Wifi_Response response;

    public static Cutter getInstance() {
        return mCutter = new Cutter();
    }

    public Wifi_Response cut(String address) {
        Thread hThread;
        ESCPOSPrinter escposPrinter = new ESCPOSPrinter();
        WiFiPort wifiPort = WiFiPort.getInstance();
        boolean connected = false;

        try {
            wifiPort.connect(address);
            connected = true;

            hThread = new Thread(new RequestHandler());
            hThread.start();

            try {
                escposPrinter.cutPaper();
                response = Wifi_Response.getInstance().compose(
                        true,
                        null,
                        "Success"
                );
            } catch (Exception e) {
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

        } catch (Exception e) {
            response = Wifi_Response.getInstance().compose(
                    false,
                    e,
                    "Global Exception in Text.plain() - Wifi"
            );
        } finally {
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
}
