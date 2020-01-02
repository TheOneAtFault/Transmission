package com.ody.aidl.Features;

import com.ody.aidl.Helpers.Response;
import com.ody.aidl.AIDLProvider;
import com.ody.aidl.Utils.AidlUtil;

public class Text {
    private static Text mText;
    private Response response;

    public static Text getInstance() {
        return mText;
    }

    public Response plain(String content, boolean cut) {
        try {
            if (content != "") {
                byte[] btContent = content.getBytes();
                AidlUtil.getInstance().sendRawData(btContent);
                if (cut) {
                    AidlUtil.getInstance().makeCut();
                }
            } else {
                response = Response.getInstance().compose(true, null, "Provided content was empty");
            }
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Text.plainText");
        }
        finally {
            AidlUtil.getInstance().disconnectPrinterService(AIDLProvider.getApplication());
        }

        return response;
    }

    public Response asBytes(byte[] content, boolean cut) {
        try {
            if (content.length > 0) {
                AidlUtil.getInstance().sendRawData(content);
                if (cut) {
                    AidlUtil.getInstance().makeCut();
                }
            } else {
                response = Response.getInstance().compose(true, null, "Provided content was empty");
            }
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Text.plainText");
        }
        finally {
            AidlUtil.getInstance().disconnectPrinterService(AIDLProvider.getApplication());
        }

        return response;
    }
}
