package com.example.michael.bakingapp.di;

import com.example.michael.bakingapp.BakingAppApplication;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        RepositoryModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class
})
public interface AppComponent {
    void inject(BakingAppApplication application);
}
