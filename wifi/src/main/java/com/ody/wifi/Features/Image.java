package com.ody.wifi.Features;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ody.wifi.Classes.ESCPOSPrinter;
import com.ody.wifi.Classes.LKPrint;
import com.ody.wifi.Classes.RequestHandler;
import com.ody.wifi.Classes.WiFiPort;
import com.ody.wifi.Helpers.Wifi_Response;

import java.io.IOException;

public class Image {

    private static Image mImage;
    private Wifi_Response response;

    public static Image getInstance() {
        return mImage = new Image();
    }

    public Wifi_Response print(String IP, String data, boolean cut) {

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
                    "IOException on connecting to wifi print."
            );
        }

        try {
            if(connected) {
                hThread = new Thread(new RequestHandler());
                hThread.start();
                Bitmap bitmap = BitmapFactory.decodeFile(data);
                posPrinter.printBitmap(bitmap, LKPrint.LK_ALIGNMENT_CENTER, 0);

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
                    "InterruptedException on connecting to wifi print."
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
                            "IO Exception in [string]Image.print() - wifi"
                    );
                } catch (InterruptedException e) {
                    response = Wifi_Response.getInstance().compose(
                            false,
                            e,
                            "Interrupted Exception in [string]Image.print() - wifi"
                    );
                }
            }
        }

        return response;
    }

    public Wifi_Response print(String IP, Bitmap data, boolean cut) {

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
                    "IOException on connecting to wifi print."
            );
        }

        try {
            if(connected) {
                hThread = new Thread(new RequestHandler());
                hThread.start();
                int result = posPrinter.printBitmap(data, LKPrint.LK_ALIGNMENT_CENTER, 0);
                if(result == 0){
                    response = Wifi_Response.getInstance().compose(
                            true,
                            null,
                            "Success."
                    );

                    if(cut){
                        posPrinter.cutPaper();
                    }
                }
                else{
                    response = Wifi_Response.getInstance().compose(
                            false,
                            null,
                            "An error occurred in Image.plain() - Wifi Module."
                    );
                }

                if (hThread.isAlive()) {
                    hThread.join(500);
                }

                if ((hThread != null) && (hThread.isAlive())) {
                    hThread.interrupt();
                    hThread = null;
                }
            }
        }
        catch (Exception e) {
            response = Wifi_Response.getInstance().compose(
                    false,
                    e,
                    "InterruptedException on connecting to wifi print."
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
                            "IO Exception in [bitmap]Image.print() - wifi"
                    );
                } catch (InterruptedException e) {
                    response = Wifi_Response.getInstance().compose(
                            false,
                            e,
                            "Interrupted Exception in [bitmap]Image.print() - wifi"
                    );
                }
            }
        }

        return response;
    }
}
