package com.task.di.module;

import com.task.data.local.sharedpreferences.SharedPreferencesConstants;
import com.task.di.qualifier.applicationlevel.PreferenceName;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    @Provides
    @PreferenceName
    @Singleton
    String providePreferenceName() {
        return SharedPreferencesConstants.SHARED_PREFERENCES_FILE_NAME;
    }
}
