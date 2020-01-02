package com.ody.aidl.Features;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.ody.aidl.Helpers.Response;
import com.ody.aidl.R;
import com.ody.aidl.AIDLProvider;
import com.ody.aidl.Utils.AidlUtil;


public class Image {
    //options
    private static int IN_TARGET_DENSITY = 160;
    private static int IN_DENSITY = 160;
    //operation variables
    private static int myorientation;
    private static Response response;
    Context context;
    private static Image INSTANCE;

    private Image() {
    }

    public static Image getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Image();
        }
        return INSTANCE;
    }

    public Response print(@Nullable String image, int inTargetDensity, int inDensity, boolean cut) {
        try {
            if (inTargetDensity != 0) {
                IN_TARGET_DENSITY = inTargetDensity;
            }
            if (inDensity != 0) {
                IN_DENSITY = inDensity;
            }

            //set options
            //todo add options if required
            if (image != null) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inTargetDensity = 160;
                options.inDensity = 160;
                Bitmap bitmap = BitmapFactory.decodeFile(image);

                AidlUtil.getInstance().printBitmap(bitmap, myorientation);
                if (cut) {
                    AidlUtil.getInstance().makeCut();
                }
                //set response
                response = Response.getInstance().compose(true, null, "success");
            } else if (image == null) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inTargetDensity = 160;
                options.inDensity = 160;
                Bitmap bitmap = BitmapFactory.decodeResource(AIDLProvider.getApplication().getResources(), R.mipmap.ody, options);

                AidlUtil.getInstance().printBitmap(bitmap, myorientation);
                if (cut) {
                    AidlUtil.getInstance().makeCut();
                }
                //set response
                response = Response.getInstance().compose(true, null, "success");
            } else {
                response = Response.getInstance().compose(false, null, "Image file is empty.");
            }
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in image aidl");
        } finally {
            AidlUtil.getInstance().disconnectPrinterService(AIDLProvider.getApplication());
        }

        return response;
    }
}
