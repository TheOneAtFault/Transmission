package com.ody.aidl;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.util.Preconditions;

import java.util.concurrent.atomic.AtomicBoolean;

public class BaseApp {
    private final Context applicationContext = getApplicationContext();
    private final AtomicBoolean deleted = new AtomicBoolean();
    private final String name = "";

    @NonNull
    public Context getApplicationContext(){
        checkNotDeleted();
        return applicationContext;
    }

    @NonNull
    public String getName() {
        checkNotDeleted();
        return name;
    }

    private void checkNotDeleted() {
        Preconditions.checkState(!deleted.get(), "FirebaseApp was deleted");
    }


}
