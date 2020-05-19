package com.ody.aidl.Features;

import com.ody.aidl.Helpers.Response;
import com.ody.aidl.Utils.AidlUtil;

public class CashDrawer {
    private static CashDrawer mCashDrawer;
    private Response response;

    public static CashDrawer getInstance() {
        return mCashDrawer = new CashDrawer();
    }

    public Response asBytes(byte[] content) {
        try {
            if (content.length > 0) {
                response = AidlUtil.getInstance().sendRawData(content);
            } else {
                response = Response.getInstance().compose(true, null, "Provided content was empty");
            }
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Text.plainText");
        }
        /*finally {
            //AidlUtil.getInstance().disconnectPrinterService(AIDLProvider.getApplication());
        }*/

        return response;
    }
}
