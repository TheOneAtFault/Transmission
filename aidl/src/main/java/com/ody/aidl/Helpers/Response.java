package com.ody.aidl.Helpers;

import androidx.annotation.Nullable;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class Response {
    private boolean bSuccess;
    private String sErrorMessage;
    private String sOutput;
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

    public String getsOutput() {
        return sOutput;
    }

    public void setsOutput(String message) {
        sOutput = message;
    }

    public Response compose(boolean status, @Nullable Exception exception,@Nullable String sOutput){
        // Response response = new Response();

        if (exception != null) {
            Writer writer = new StringWriter();
            exception.printStackTrace(new PrintWriter(writer));
            String exceptionMessage = writer.toString();
            mResponse.setsErrorMessage(exceptionMessage);
        } else {
            mResponse.setsErrorMessage("NaN");
        }

        if(sOutput != null){
            mResponse.setsOutput(sOutput);
        }
        else{
            mResponse.setsOutput("");
        }

        mResponse.setSuccess(status);

        return mResponse;
    }

    public static Response getInstance(){
        return mResponse;
    }
}
