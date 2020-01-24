package com.ody.wifi.Features;

import android.content.Context;
import android.os.AsyncTask;

import com.ody.wifi.Classes.ESCPOSPrinter;
import com.ody.wifi.Classes.WiFiPort;
import com.ody.wifi.Helpers.Wifi_Response;

public class Connection extends AsyncTask<String, Void, String> {
    private static Connection mConnection;
    private Wifi_Response response;

    public static Connection getInstance() {
        return mConnection = new Connection();
    }

    public boolean check(String address) {
        new Connection().execute("");

    }

    @Override
    protected String doInBackground(String... strings) {
        Thread hThread;
        ESCPOSPrinter escposPrinter = new ESCPOSPrinter();
        WiFiPort wifiPort = WiFiPort.getInstance();
        Boolean connected = false;
        try {
            wifiPort.connect(address);
            return true;
        }catch (Exception e){
            //catch
            return false;
        }
    }
}
