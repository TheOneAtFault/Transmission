package com.ody.aidl;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.util.Preconditions;

import com.ody.aidl.Utils.AidlUtil;

import java.util.concurrent.atomic.AtomicBoolean;

public class BaseApp extends Application {
    //private final Context applicationContext = getApplicationContext();
//    private final AtomicBoolean deleted = new AtomicBoolean();
//    private final String name = "";
//
//    @NonNull
//    public Context getAppContext(){
//        //checkNotDeleted();
//        return applicationContext;
//    }
//
//    @NonNull
//    public String getName() {
//        checkNotDeleted();
//        return name;
//    }
//
//    private void checkNotDeleted() {
//        Preconditions.checkState(!deleted.get(), "FirebaseApp was deleted");
//    }

    private boolean isAidl;
    public boolean isAidl() {
        return isAidl;
    }

    public void setAidl(boolean aidl) {
        isAidl = aidl;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isAidl = true;
        AidlUtil.getInstance().connectPrinterService(this);
    }
}
