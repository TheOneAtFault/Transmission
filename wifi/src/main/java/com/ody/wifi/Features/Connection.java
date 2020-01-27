package com.ody.wifi.Features;

import android.content.Context;
import android.os.AsyncTask;

import com.ody.wifi.Classes.ESCPOSPrinter;
import com.ody.wifi.Classes.WiFiPort;
import com.ody.wifi.Helpers.Wifi_Response;

import java.io.IOException;

public class Connection extends AsyncTask<String, Void, String> {
    private static Connection mConnection;
    private Wifi_Response response;

    public static Connection getInstance() {
        return mConnection = new Connection();
    }

    public boolean check(String address) {
        Thread hThread;
        ESCPOSPrinter escposPrinter = new ESCPOSPrinter();
        WiFiPort wifiPort = WiFiPort.getInstance();
        boolean connected = false;
        try {
            wifiPort.connect(address);
            connected = true;
        }catch (Exception e){
            //catch
            connected = false;
        }finally {
            try {
                wifiPort.disconnect();
            } catch (Exception e) {
                connected = false;
            }
        }

        return connected;
    }

    @Override
    protected String doInBackground(String... strings) {

        return null;
    }
}
