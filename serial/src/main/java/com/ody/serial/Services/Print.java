package com.ody.serial.Services;

import com.ody.serial.Features.QR;
import com.ody.serial.Features.Text;
import com.ody.serial.Helpers.Response;

public class Print {
    private static Response response;

    public static Response text(String content, String serialPort, int baudRate) {
        try {
            response = Text.getInstance().plain(content, serialPort, baudRate);
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Print.text()");
        }
        return response;
    }

    public static Response qr(String data, String serialPort, int baudRate) {
        try {
            response = QR.getInstance().print(data, serialPort, baudRate);
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Print.qe()");
        }
        return response;
    }
}
