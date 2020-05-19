package com.ody.aidl.Services;

import com.ody.aidl.Features.CashDrawer;
import com.ody.aidl.Features.Text;
import com.ody.aidl.Helpers.Response;

public class Drawer {
    private static Response response;
    private int PADDING = 2;
    public static Response kick() {
        try {
            byte[] data = new byte[]{0x10, 0x14, 0x00, 0x00, 0x00};
            response = CashDrawer.getInstance().asBytes(data);
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Drawer - kick");
        }
        return response;
    }
}
