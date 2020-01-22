package com.ody.serial.Services;

import com.ody.serial.Features.Data;
import com.ody.serial.Features.Ports;
import com.ody.serial.Helpers.Response;

public class Communication {
    private static String responseAsString;

    //send
    public static String request(String serialPort, int baudRate, boolean closeSerialPort) {
        try {
            responseAsString = Data.getInstance().request(serialPort, baudRate, closeSerialPort);
        } catch (Exception e) {
            responseAsString = Response.getInstance().compose(false, e,
                    "Exception in Send And Recieve (request) - returnData.").getsErrorMessage();
        }
        return responseAsString;
    }

    public static String requestPorts() {
        try {
            responseAsString = Ports.getInstance().get();
        } catch (Exception e) {
            responseAsString = Response.getInstance().compose(false, e, "Exception in Communication.requestPorts().").getsErrorMessage();
        }
        return responseAsString;
    }
}
