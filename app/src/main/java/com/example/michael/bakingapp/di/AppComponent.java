package com.example.michael.bakingapp.di;

import com.example.michael.bakingapp.BakingAppApplication;

import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {
        ApplicationModule.class,
        RepositoryModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class
})
public interface AppComponent {
    void inject(BakingAppApplication application);
}
