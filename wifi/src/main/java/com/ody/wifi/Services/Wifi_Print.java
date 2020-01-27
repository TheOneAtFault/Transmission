package com.ody.wifi.Services;
import android.graphics.Bitmap;

import com.ody.wifi.Features.Image;
import com.ody.wifi.Features.QR;
import com.ody.wifi.Features.Text;
import com.ody.wifi.Helpers.Wifi_Response;

public class Wifi_Print {
    private static Wifi_Response response;

    public static Wifi_Response image(String ip, String data, boolean cut) {
        try {
            response = Image.getInstance().print(ip, data, cut);
        } catch (Exception e) {
            response = Wifi_Response.getInstance().compose(false, e, "Exception in Wifi_Print.image()");
        }
        return response;
    }

    public static Wifi_Response image(String ip, Bitmap data, boolean cut) {
        try {
            response = Image.getInstance().print(ip, data, cut);
        } catch (Exception e) {
            response = Wifi_Response.getInstance().compose(false, e, "Exception in Wifi_Print.image()");
        }
        return response;
    }

    public static Wifi_Response text(String ip, String data, boolean cut){
        try{
            response = Text.getInstance().plain(ip, data, cut);
        }catch (Exception e){
            response = Wifi_Response.getInstance().compose(false,e,"Exception in Wifi_Print.text()");
        }
        return response;
    }

    public static Wifi_Response qr(String ip, String data, boolean cut){
        try{
            response = QR.getInstance().print(ip, data, cut);
        }catch (Exception e){
            response = Wifi_Response.getInstance().compose(false,e,"Exception in Wifi_Print.qr()");
        }
        return response;
    }
}
