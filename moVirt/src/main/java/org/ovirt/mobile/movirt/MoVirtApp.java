package org.ovirt.mobile.movirt;

import android.app.Application;
import android.content.Context;

import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.ovirt.mobile.movirt.sync.SyncUtils;

@EApplication
public class MoVirtApp extends Application {
    private static final String TAG = MoVirtApp.class.getSimpleName();

    @Pref
    AppPrefs_ prefs;

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        SyncUtils.createSyncAccount(this);
    }

    public boolean endpointConfigured() {
        return true; //prefs.endpoint().exists();// &&
//                prefs.username().exists() &&
//                prefs.password().exists();
    }
}
