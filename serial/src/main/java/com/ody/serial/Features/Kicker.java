package com.ody.serial.Features;

import com.ody.serial.Classes.Drawer.CashDrawerApiContext;
import com.ody.serial.Classes.Drawer.CashDrawerManage;
import com.ody.serial.Helpers.Response;

public class Kicker {

    private static Kicker mKicker = new Kicker();
    private Response response;

    public static Kicker getInstance() {
        return mKicker;
    }

    public Response PAT100() {
        try {
            CashDrawerApiContext mPTTApiContext = new CashDrawerManage();
            mPTTApiContext.OpenCashDrawerA();
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Kicker.PAT100()");
        }

        return response;
    }

    /*static {
        System.loadLibrary("cash_drawer");
    }*/

}
