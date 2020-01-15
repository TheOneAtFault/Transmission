package com.ody.aidl.Features;

import com.ody.aidl.Helpers.Response;
import com.ody.aidl.AIDLProvider;
import com.ody.aidl.Utils.AidlUtil;

public class Text {
    private static Text mText;
    private Response response;

    public static Text getInstance() {
        return mText = new Text();
    }

    public Response plain(String content, boolean cut, int padding) {
        try {
            if (content.equals("")) {
                byte[] btContent = content.getBytes();
                response = AidlUtil.getInstance().sendRawData(btContent);
                if (response.isSuccess() && cut) {
                    AidlUtil.getInstance().padding(padding);
                    AidlUtil.getInstance().makeCut();
                }
                else{
                    AidlUtil.getInstance().padding(padding);
                }
            } else {
                response = Response.getInstance().compose(false, null, "Provided content was empty");
            }
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Text.plainText");
        }

        return response;
    }

    public Response cutter() {
        try {
            AidlUtil.getInstance().makeCut();
            response = Response.getInstance().compose(false, null, "Success on: Text.plainText");
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in Text.plainText");
        }

        return response;
    }

    public Response asBytes(byte[] content, boolean cut, int padding) {
        try {
            if (content.length > 0) {
                response = AidlUtil.getInstance().sendRawData(content);
                if (response.isSuccess() && cut) {
                    AidlUtil.getInstance().padding(padding);
                    AidlUtil.getInstance().makeCut();
                }
                else{
                    AidlUtil.getInstance().padding(padding);
                }
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
