package com.ody.serial.Services;

import com.ody.serial.Features.Data;
import com.ody.serial.Features.Ports;
import com.ody.serial.Helpers.Response;

public class Communication {
    private static String response;

    //send
    public static String request(String serialPort, int baudRate, boolean closeSerialPort) {
        try {
            response = Data.getInstance().request(serialPort, baudRate, closeSerialPort);
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e,
                    "Exception in SendAndRecieve - returnData.").getsErrorMessage();
        }
        return response;
    }

    public static String requestPorts() {
        try {
            response = Ports.getInstance().get();
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Communication.requestPorts().").getsErrorMessage();
        }
        return response;
    }
}
