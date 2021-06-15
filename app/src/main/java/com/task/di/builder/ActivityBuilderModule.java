package com.task.di.builder;

import com.task.ui.task.TaskActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract TaskActivity bindTaskActivity();
}
