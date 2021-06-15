package com.task.di.module;

import android.app.Application;
import android.content.Context;
import com.task.di.qualifier.applicationlevel.ApplicationContext;
import javax.inject.Singleton;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class ContextModule {

    @Binds
    @ApplicationContext
    @Singleton
    abstract Context provideContext(Application application);
}