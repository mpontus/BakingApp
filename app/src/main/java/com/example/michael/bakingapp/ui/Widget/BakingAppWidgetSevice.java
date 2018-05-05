package com.example.michael.bakingapp.ui.Widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.example.michael.bakingapp.data.Preferences;
import com.example.michael.bakingapp.data.Repository;
import com.example.michael.bakingapp.ui.utils.QuantityFormatter;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class BakingAppWidgetSevice extends RemoteViewsService {

    @Inject
    Repository repository;

    @Inject
    Preferences preferences;

    @Inject
    QuantityFormatter quantityFormatter;

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
                quantityFormatter);
    }
}
