package com.ody.wifi.Services;

import android.content.Context;
import android.graphics.Bitmap;

import com.ody.wifi.Features.Image;
import com.ody.wifi.Features.QR;
import com.ody.wifi.Features.Text;
import com.ody.wifi.Helpers.Wifi_Response;

public class Wifi_Print {
    private static Wifi_Response response;

    public static Wifi_Response image(Context context, String ip, String data, boolean cut) {
        try {
            response = Image.getInstance().print(context, ip, data, cut);
        } catch (Exception e) {
            response = Wifi_Response.getInstance().compose(false, e, "Exception in Wifi_Print.image()");
        }
        return response;
    }

    public static Wifi_Response image(Context context, String ip, Bitmap data, boolean cut) {
        try {
            response = Image.getInstance().print(context, ip, data, cut);
        } catch (Exception e) {
            response = Wifi_Response.getInstance().compose(false, e, "Exception in Wifi_Print.image()");
        }
        return response;
    }

    public static Wifi_Response text(Context context, String ip, String data, boolean cut){
        try{
            response = Text.getInstance().plain(context, ip, data, cut);
        }catch (Exception e){
            response = Wifi_Response.getInstance().compose(false,e,"Exception in Wifi_Print.text()");
        }
        return response;
    }

    public static Wifi_Response qr(Context context, String ip, String data, boolean cut){
        try{
            response = QR.getInstance().print(context, ip, data, cut);
        }catch (Exception e){
            response = Wifi_Response.getInstance().compose(false,e,"Exception in Wifi_Print.qr()");
        }
        return response;
    }
}
