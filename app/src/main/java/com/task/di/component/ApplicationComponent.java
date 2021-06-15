package com.task.di.component;

import android.app.Application;
import com.task.BaseApplication;
import com.task.di.builder.ActivityBuilderModule;
import com.task.di.builder.FragmentBuilderModule;
import com.task.di.module.ApplicationModule;
import com.task.di.module.ContextModule;
import com.task.di.module.NetworkModule;
import com.task.di.module.SqliteDatabaseModule;
import javax.inject.Singleton;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(
        modules = {
                /* Use AndroidInjectionModule.class if you're not using support library */
                AndroidInjectionModule.class,

                /* Application Module objects use by activity, fragment anywhere */
                ApplicationModule.class,

                ContextModule.class,
                SqliteDatabaseModule.class,
                NetworkModule.class,
                ActivityBuilderModule.class,
                FragmentBuilderModule.class
        }
)
public interface ApplicationComponent extends AndroidInjector<BaseApplication> {

    /**
     * This Builder will be constructed by Dagger
     * @BindsInstance binds the current instance into the DI graph,
     * It means that we can use this instance anywhere in our DI graph
     */
    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }

    @Override
    void inject(BaseApplication baseApplication);
}