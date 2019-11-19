package com.ody.wifi.Services;

import com.ody.wifi.Features.Image;
import com.ody.wifi.Helpers.Response;

public class Print {
    private static Response response;

    public static Response image(String ip, String path, int timeOut) {
        try {
            response = Image.getInstance().print(ip, path, timeOut);
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Print.image()");
        }
        return response;
    }

    public static Response text(){
        try{

        }catch (Exception e){
            response = Response.getInstance().compose(false,e,"Exception in Print.text()");
        }
        return response;
    }
}
