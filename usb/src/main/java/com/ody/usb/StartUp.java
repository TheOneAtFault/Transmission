package com.ody.usb;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ody.usb.Features.Image;
import com.ody.usb.Features.Text;

public class StartUp extends ContentProvider {
    //private static WeakReference<Context> mContext;

    @Override
    public boolean onCreate() {
        Context context = this.getContext();
        //Init Text
        Text.getInstance().connectService(context);
        Image.getInstance().connectService(context);

        Toast.makeText(context,"onCreate",Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public void attachInfo(Context context, ProviderInfo info) {
        if (info == null){
            throw new NullPointerException("StartUp ProviderInfo cannot be null.");
        }
        // So if the authorities equal the library internal ones, the developer forgot to set his applicationId
        if ("com.ody.usb.StartUp".equals(info.authority)) {
            throw new IllegalStateException("Incorrect provider authority in manifest. Most likely due to a "
                    + "missing applicationId variable in application\'s build.gradle.");
        }
        super.attachInfo(context, info);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
