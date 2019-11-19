package com.ody.aidl.Services;

import com.ody.aidl.Features.Text;
import com.ody.aidl.Helpers.Response;

public class Drawer {
    private static Response response;

    public static Response kick() {
        try {
            byte[] data = new byte[]{0x10, 0x14, 0x00, 0x00, 0x00};
            response = Text.getInstance().asBytes(data, false);

        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Drawer - kick");
        }
        return response;
    }
}
