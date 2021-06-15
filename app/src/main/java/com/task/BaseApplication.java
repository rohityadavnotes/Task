package com.task;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;
import com.task.di.component.DaggerApplicationComponent;
import com.task.utils.LogcatUtil;
import javax.inject.Inject;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class BaseApplication extends Application implements HasAndroidInjector {

    public static final String TAG = BaseApplication.class.getSimpleName();

    /***********************************************************************************************
     ************************ Method to get the [BaseApplication] instance. ************************
     **********************************************************************************************/

    private static BaseApplication INSTANCE;

    public static BaseApplication getInstance() {
        return INSTANCE;
    }

    /***********************************************************************************************
     ************************************* Implement Dagger 2 **************************************
     **********************************************************************************************/

    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }

    /***********************************************************************************************
     ***************************** Application Lifecycle Methods ***********************************
     **********************************************************************************************/

    /* Called by the system when the device configuration changes */
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LogcatUtil.informationMessage(TAG, "LANDSCAPE");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            LogcatUtil.informationMessage(TAG, "PORTRAIT");
        }
    }

    /*
     * Called when the application is starting, before any other application objects have been
     * created.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        DaggerApplicationComponent.builder().application(this).build().inject(this);
        registerActivityLifecycleCallbacks(new MyActivityLifecycleCallbacks());
    }

    private static final class MyActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {
        public void onActivityCreated(Activity activity, Bundle bundle) {
            LogcatUtil.informationMessage(TAG, "onActivityCreated:" + activity.getLocalClassName());
        }

        public void onActivityDestroyed(Activity activity) {
            LogcatUtil.informationMessage(TAG, "onActivityDestroyed:" + activity.getLocalClassName());
        }

        public void onActivityPaused(Activity activity) {
            LogcatUtil.informationMessage(TAG, "onActivityPaused:" + activity.getLocalClassName());
        }

        public void onActivityResumed(Activity activity) {
            LogcatUtil.informationMessage(TAG, "onActivityResumed:" + activity.getLocalClassName());
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            LogcatUtil.informationMessage(TAG, "onActivitySaveInstanceState:" + activity.getLocalClassName());
        }

        public void onActivityStarted(Activity activity) {
            LogcatUtil.informationMessage(TAG, "onActivityStarted:" + activity.getLocalClassName());
        }

        public void onActivityStopped(Activity activity) {
            LogcatUtil.informationMessage(TAG, "onActivityStopped:" + activity.getLocalClassName());
        }
    }

    /*
     * This is called when the overall system is running low on memory, and would like actively
     * running processes to tighten their belts.
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        LogcatUtil.informationMessage(TAG, "onLowMemory()");
    }

    /*
     * Only for testing, not called in production. This method is for use in emulated process
     * environments. It will never be called on a production Android device.
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        LogcatUtil.informationMessage(TAG, "onTerminate()");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LogcatUtil.informationMessage(TAG, "onTrimMemory(int level)");
    }

    @Override
    protected void attachBaseContext(Context base) {
        try {
            super.attachBaseContext(base);
            MultiDex.install(base);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogcatUtil.informationMessage(TAG, "attachBaseContext(Context base)");
    }
}
