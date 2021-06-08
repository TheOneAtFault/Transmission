package com.ody.bluetooth;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ValoriInitProvider extends ContentProvider implements PrinterManager.PrinterManagerListener {
    private PrinterManager mPrinterManager;
    private static final byte[] CUT = new byte[]{29, 86, 66, 0};
    private static final byte[] DRAWER = new byte[]{27, 112, 48, 55, 121};
    @Override
    public boolean onCreate() {
        Context context = getContext();

        mPrinterManager = new PrinterManager((Activity) context, this);
        mPrinterManager.onPrinterStart();

        mPrinterManager.sendRAWData(CUT);

        return false;
    }

    @Override
    public void attachInfo(Context context, ProviderInfo info) {
        if (info == null) {
            throw new NullPointerException("YourLibraryInitProvider ProviderInfo cannot be null.");
        }
        // So if the authorities equal the library internal ones, the developer forgot to set his applicationId
        if ("<your-library-applicationid>.yourlibraryinitprovider".equals(info.authority)) {
            throw new IllegalStateException("Incorrect provider authority in manifest. Most likely due to a "
                    + "missing applicationId variable in application\'s build.gradle.");
        }
        super.attachInfo(context, info);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public void onServiceConnected() {
        mPrinterManager.printerInit();
    }
}
