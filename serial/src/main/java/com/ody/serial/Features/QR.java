package com.ody.serial.Features;

import com.ody.serial.Classes.SerialPort;
import com.ody.serial.Helpers.Response;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidParameterException;

public class QR {
    private static QR mQR = new QR();
    private Response response;

    public static QR getInstance() {
        return mQR;
    }

    public Response print(String data, String sSerialPort, int iBaudRate) {

        SerialPort mSerialPort = null;
        OutputStream mOutputStream = null;
        int dataLength = data.length();

        try {
            mSerialPort = new SerialPort(new File(sSerialPort), iBaudRate, 0, true);
            mOutputStream = mSerialPort.getOutputStream();

            //build qr command list
            byte[] CellWidthCommand = {29, 40, 107, 3, 0, 49, 67, (byte) 4};
            byte[] ECCCommand = {29, 40, 107, 3, 0, 49, 69, (byte) (0 + 48)};
            byte[] PrintCommand = {29, 40, 107, 3, 0, 49, 81, 48};
            byte[] DataHeadCommand = {29, 40, 107, 0, 0, 49, 80, 48};

            int slen = 0;
            if (dataLength == 0) {
                slen = data.length();
            } else {
                slen = dataLength;
            }

            int nl = (slen + 3) % 256;
            int nh = (slen + 3) / 256;
            DataHeadCommand[3] = ((byte) nl);
            DataHeadCommand[4] = ((byte) nh);
            mOutputStream.write(CellWidthCommand);
            mOutputStream.write(ECCCommand);
            mOutputStream.write(DataHeadCommand);
            mOutputStream.write(data.getBytes("UTF-8"));
            mOutputStream.write(PrintCommand);

            mSerialPort.close();
            mSerialPort = null;

        } catch (InvalidParameterException e) {
            response = Response.getInstance().compose(false, e, "InvalidParameterException in QR.print()");
        } catch (SecurityException e) {
            response = Response.getInstance().compose(false, e, "SecurityException in QR.print()");
        } catch (IOException e) {
            response = Response.getInstance().compose(false, e, "IOException in QR.print()");
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in QR.print()");
        }

        return response;
    }
}
