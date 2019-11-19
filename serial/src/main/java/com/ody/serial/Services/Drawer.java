package com.ody.serial.Services;

import com.ody.serial.Features.Kicker;
import com.ody.serial.Helpers.Response;

public class Drawer {
    private static Response response;

    public static Response kick_PAT100() {
        try {
            response = Kicker.getInstance().PAT100();
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Drawer - kick_PAT100");
        }
        return response;
    }
}
