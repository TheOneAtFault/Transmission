package com.ody.aidl.Features;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.ody.aidl.Helpers.Response;
import com.ody.aidl.R;
import com.ody.aidl.StartUp;
import com.ody.aidl.Utils.AidlUtil;

public class Image {
    //options
    static int IN_TARGET_DENSITY = 160;
    static int IN_DENSITY = 160;
    //operation variables
    static int myorientation;
    static StartUp startUp;
    private static Response response;
    Context context;
    private static Image mImage = new Image();

    private Image() {

    }

    public static Image getInstance() {
        return mImage;
    }

    public Response print(@Nullable Bitmap bitmap, int inTargetDensity, int inDensity, boolean cut) {
        try {
            if (inTargetDensity != 0) {
                IN_TARGET_DENSITY = inTargetDensity;
            }
            if (inDensity != 0) {
                IN_DENSITY = inDensity;
            }

            //set options
            //todo add options if required
            if (bitmap == null) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inTargetDensity = 160;
                options.inDensity = 160;
                bitmap = BitmapFactory.decodeResource(startUp.getApplication().getResources(), R.mipmap.logosmall, options);
            }

            AidlUtil.getInstance().printBitmap(bitmap, myorientation);
            if (cut) {
                AidlUtil.getInstance().makeCut();
            }
            //set response
            response = Response.getInstance().compose(true, null, "success");
        } catch (Exception e) {
            response = Response.getInstance().compose(false, e, "Exception in image aidl");
        }

        return response;
    }
}
