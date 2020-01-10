package com.ody.aidl;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ody.aidl.Features.Image;
import com.ody.aidl.Services.Print;
import com.ody.aidl.Utils.AidlUtil;

import java.lang.ref.WeakReference;

import static android.content.ContentValues.TAG;

public class AIDLProvider extends ContentProvider {
    private static WeakReference<Context> mContext;
    private static WeakReference<Application> mApplication;
    private boolean isAidl;

    public static Application getApplication(){
        return mApplication.get();
    }

    public static WeakReference<Context> getCurContext(){
        return mContext;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        /*if (AidlUtil.initializeApp(getContext()) == null) {
            Log.i(TAG, "FirebaseApp initialization unsuccessful");
        } else {
            Log.i(TAG, "FirebaseApp initialization successful");
        }*/
        //aidl init
        isAidl = true;
        AidlUtil.getInstance();
        //AidlUtil.getInstance().connectPrinterService(mContext);
       // Toast.makeText(context,"Startup - context",Toast.LENGTH_SHORT).show();

        return false;
    }

    @Override
    public void attachInfo(Context context, ProviderInfo info) {
        if (info == null){
            throw new NullPointerException("AIDLProvider ProviderInfo cannot be null.");
        }
        // if the authorities equal the library internal ones, set applicationId.
        if ("com.ody.aidl.AIDLProvider".equals(info.authority)) {
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
