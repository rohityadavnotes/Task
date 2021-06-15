package com.task.data.local.sharedpreferences;

import android.content.Context;
import com.task.di.qualifier.applicationlevel.ApplicationContext;
import com.task.di.qualifier.applicationlevel.PreferenceName;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SharedPreferencesHelper extends ApplicationSharedPreference {

    @Inject
    public SharedPreferencesHelper(@ApplicationContext Context context, @PreferenceName String preferenceFileName) {
        super(context, preferenceFileName);
    }
}
