package com.ody.serial.Features;

import com.ody.serial.Classes.SerialPort;
import com.ody.serial.Helpers.Response;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;

public class Data {
    private static Data mData;
    private Response response;

    public static Data getInstance() {
        return mData;
    }

    public String request(String serialPort, int baudRate, boolean closeSerialPort) {
        InputStream mInputStream;
        String inputString = "";
        SerialPort mSerialPort = null;

        try {
            mSerialPort = new SerialPort(new File(serialPort), baudRate, 0, true);
            mInputStream = mSerialPort.getInputStream();

            byte[] buffer = new byte[1024];

            if (mInputStream == null) {
                inputString = "0";
            }

            int size = mInputStream.read(buffer);
            if (size > 0) {

                inputString = new String(buffer, 0, size);

                if (closeSerialPort) {
                    mSerialPort.close();
                }

                mSerialPort = null;
                mInputStream.close();
                mInputStream = null;

            }

            response = Response.getInstance().compose(true, null, "Success");

        } catch (IOException e) {
            response = Response.getInstance().compose(false, e, "IOException in GetData.request()");
        } catch (InvalidParameterException e) {
            response = Response.getInstance().compose(false, e, "InvalidParameterException in GetData.request()");
        } catch (SecurityException e) {
            response = Response.getInstance().compose(false, e, "SecurityException in GetData.request()");
        } finally {
            if (response.isSuccess()) {
                return response.getsErrorMessage();
            } else {
                return inputString;
            }
        }
    }

}
