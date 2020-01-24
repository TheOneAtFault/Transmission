package com.ody.wifi.Helpers;

import androidx.annotation.Nullable;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
//todo: explain class
public class Wifi_Response {
    private boolean bSuccess;
    private String sErrorMessage;
    private String sCustomMessage;
    private static Wifi_Response mWifiResponse = new Wifi_Response();

    private Wifi_Response(){
    }

    public boolean isSuccess() {
        return bSuccess;
    }

    public void setSuccess(boolean success) {
        this.bSuccess = success;
    }

    public String getsErrorMessage() {
        return sErrorMessage;
    }

    public void setsErrorMessage(String message) {
        sErrorMessage = message;
    }

    public String getsCustomMessage() {
        return sCustomMessage;
    }

    public void setsCustomMessage(String message) {
        sCustomMessage = message;
    }

    public Wifi_Response compose(boolean status, @Nullable Exception exception, @Nullable String message){
       // Wifi_Response response = new Wifi_Response();

        if (exception != null) {
            Writer writer = new StringWriter();
            exception.printStackTrace(new PrintWriter(writer));
            String exceptionMessage = writer.toString();
            mWifiResponse.setsErrorMessage(exceptionMessage);
        } else {
            mWifiResponse.setsErrorMessage("NaN");
        }

        if(message != null){
            mWifiResponse.setsCustomMessage(message);
        }
        else{
            mWifiResponse.setsCustomMessage("");
        }

        mWifiResponse.setSuccess(status);

        return mWifiResponse;
    }

    public static Wifi_Response getInstance(){
        return mWifiResponse;
    }
}
