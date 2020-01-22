package com.ody.usb.Helpers;

import androidx.annotation.Nullable;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

//todo: explain class
public class USB_Response {
    private boolean bSuccess;
    private String sErrorMessage;
    private String sCustomMessage;
    private static USB_Response mUSBResponse = new USB_Response();

    private USB_Response(){
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

    public USB_Response compose(boolean status, @Nullable Exception exception, @Nullable String message){
       // USB_Response response = new USB_Response();
        if (exception != null) {
            Writer writer = new StringWriter();
            exception.printStackTrace(new PrintWriter(writer));
            String exceptionMessage = writer.toString();
            mUSBResponse.setsErrorMessage(exceptionMessage);
        } else {
            mUSBResponse.setsErrorMessage("NaN");
        }

        if(message != null){
            mUSBResponse.setsCustomMessage(message);
        }
        else{
            mUSBResponse.setsCustomMessage("");
        }

        mUSBResponse.setSuccess(status);

        return mUSBResponse;
    }

    public static USB_Response getInstance(){
        return mUSBResponse;
    }
}
