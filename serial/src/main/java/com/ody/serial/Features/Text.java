package com.ody.serial.Features;

import com.ody.serial.Classes.SerialPort;
import com.ody.serial.Helpers.Response;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidParameterException;

public class Text {
    private Response response;
    private static Text mText = new Text();

    public static Text getInstance(){
        return mText;
    }

    public Response plain(String sPrintValue, String sSerialPort, int iBaudRate) {

        SerialPort mSerialPort = null;
        OutputStream mOutputStream = null;

        try {
            mSerialPort = new SerialPort(new File(sSerialPort), iBaudRate, 0, true);
            mOutputStream = mSerialPort.getOutputStream();

            byte[] cmd = new byte[1024];
            cmd = sPrintValue.getBytes("UTF-8");

            mOutputStream.write(cmd);
            mSerialPort.close();
            mSerialPort = null;
            mOutputStream.close();
            mOutputStream = null;

            response = Response.getInstance().compose(true,null,"Success");

        } catch (InvalidParameterException e) {
            response = Response.getInstance().compose(false,e,"InvalidParameterException Exception in Text.plain()");
        } catch (SecurityException e) {
            response = Response.getInstance().compose(false,e,"SecurityException Exception in Text.plain()");
        } catch (IOException e) {
            response = Response.getInstance().compose(false,e,"IOException Exception in Text.plain()");
        } catch (Exception e) {
            response = Response.getInstance().compose(false,e,"General Exception in Text.plain()");
        }

        return response;
    }
}
