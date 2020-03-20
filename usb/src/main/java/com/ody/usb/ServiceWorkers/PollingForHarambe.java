package com.ody.usb.ServiceWorkers;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ody.usb.App;

import tw.com.prolific.driver.pl2303.PL2303Driver;

public class PollingForHarambe extends IntentService {

    private Handler handler = new Handler();
    int interval = 2000;

    private PL2303Driver.BaudRate mBaudrate = PL2303Driver.BaudRate.B9600;
    private PL2303Driver.DataBits mDataBits = PL2303Driver.DataBits.D8;
    private PL2303Driver.Parity mParity = PL2303Driver.Parity.NONE;
    private PL2303Driver.StopBits mStopBits = PL2303Driver.StopBits.S1;
    private PL2303Driver.FlowControl mFlowControl = PL2303Driver.FlowControl.OFF;

    private int counter = 0;
    public PollingForHarambe() {
        super("PollingForHarambe");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Toast.makeText(App.context,"on start command",Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Toast.makeText(App.context,"atleast in the handler",Toast.LENGTH_SHORT).show();
        if (App.mDriver != null && App.mDriver.isConnected()){
            /*Bundle bundle = intent.getExtras();
            String data = bundle.getString("data");*/
            Toast.makeText(App.context,"Inside",Toast.LENGTH_SHORT).show();
            //counter++;
            String value = "datau";

            byte[] destination = new byte[new byte[]{0x0C}.length + value.getBytes().length];
            System.arraycopy(new byte[]{0x0C}, 0, destination, 0, new byte[]{0x0C}.length);
            System.arraycopy(value.getBytes(), 0, destination, new byte[]{0x0C}.length, value.getBytes().length);
            int res = App.mDriver.write(destination);

            App.mDriver.end();
        }
        //handler.post(SerialComms);

    }

    Runnable SerialComms = new Runnable() {
        @Override
        public void run() {


            handler.postDelayed(this, interval);
        }
    };
}
