package com.ody.serial.Features;

import android.content.Context;
import android.widget.Toast;

import com.ody.serial.Classes.SerialPort;
import com.ody.serial.Helpers.Response;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidParameterException;

public class Text {
    private Response response;
    private static Text mText;

    public static Text getInstance(){
        return mText = new Text();
    }

    public Response plain(String sPrintValue, String sSerialPort, int iBaudRate, Context context) {

        SerialPort mSerialPort = null;
        OutputStream mOutputStream = null;

        try {
            Toast.makeText(context,sSerialPort,Toast.LENGTH_SHORT).show();
            File f = new File(sSerialPort);
            mSerialPort = new SerialPort(new File(sSerialPort), iBaudRate, 0, true,context);
           /* mOutputStream = mSerialPort.getOutputStream();

            byte[] cmd = new byte[1024];
            cmd = sPrintValue.getBytes("UTF-8");

            mOutputStream.write(cmd);
            mSerialPort.close();
            mSerialPort = null;
            mOutputStream.close();
            mOutputStream = null;

            response = Response.getInstance().compose(true,null,"Success");*/

       /* } catch (InvalidParameterException e) {
            Toast.makeText(context,"2",Toast.LENGTH_SHORT).show();
            //response = Response.getInstance().compose(false,e,"InvalidParameterException Exception in Text.plain()");
        } catch (SecurityException e) {
            Toast.makeText(context,"3",Toast.LENGTH_SHORT).show();*/
            //response = Response.getInstance().compose(false,e,"SecurityException Exception in Text.plain()");
        /*} catch (IOException e) {
            Toast.makeText(context,"4",Toast.LENGTH_SHORT).show();
            //response = Response.getInstance().compose(false,e,"IOException Exception in Text.plain()");*/
        } catch (Exception e) {
            Toast.makeText(context,"5",Toast.LENGTH_SHORT).show();
            //response = Response.getInstance().compose(false,e,"General Exception in Text.plain()");
        }

        return response;
    }
}
