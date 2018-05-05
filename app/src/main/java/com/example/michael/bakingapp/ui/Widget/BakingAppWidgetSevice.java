package com.example.michael.bakingapp.ui.Widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.example.michael.bakingapp.data.Preferences;
import com.example.michael.bakingapp.data.Repository;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;

public class BakingAppWidgetSevice extends RemoteViewsService {

    @Inject
    Repository repository;

    @Inject
    Preferences preferences;

    @Inject
    Provider<CompositeDisposable> compositeDisposableProvider;

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);

        super.onCreate();
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingAppRemoteViewsFactory(
                getApplicationContext(),
                intent,
                repository,
                preferences,
                compositeDisposableProvider.get());
    }
}
