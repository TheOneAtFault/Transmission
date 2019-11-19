package com.ody.aidl.Services;

import com.ody.aidl.Helpers.QRGenerator;
import com.ody.aidl.Helpers.Response;

public class Generate {
    private static Response response;

    public static Response qr(String data) {
        try {
            response = QRGenerator.getInstance().run(data);
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Generate.qr()");
        }
        return response;
    }
}
