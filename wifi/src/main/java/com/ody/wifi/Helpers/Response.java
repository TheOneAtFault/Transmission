package com.ody.wifi.Helpers;

import androidx.annotation.Nullable;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

//todo: explain class
public class Response {
    private boolean bSuccess;
    private String sErrorMessage;
    private String sCustomMessage;
    private static Response mResponse = new Response();

    private Response(){
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

    public Response compose(boolean status, @Nullable Exception exception,@Nullable String message){
       // Response response = new Response();

        if (exception != null) {
            Writer writer = new StringWriter();
            exception.printStackTrace(new PrintWriter(writer));
            String exceptionMessage = writer.toString();
            mResponse.setsErrorMessage(exceptionMessage);
        } else {
            mResponse.setsErrorMessage("NaN");
        }

        if(message != null){
            mResponse.setsCustomMessage(message);
        }
        else{
            mResponse.setsCustomMessage("");
        }

        mResponse.setSuccess(status);

        return mResponse;
    }

    public static Response getInstance(){
        return mResponse;
    }
}
