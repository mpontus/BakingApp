package com.example.michael.bakingapp.ui.WidgetConfigure;

import android.appwidget.AppWidgetManager;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class WidgetConfigureModule {
    @Binds
    abstract WidgetConfigureContract.View provideView(WidgetConfigureActivity activity);

    @Binds
    abstract WidgetConfigureContract.Presenter providePresenter(WidgetConfigurePresenter presenter);

    @Named("widgetId")
    @Provides
    static int provideWidgetId(WidgetConfigureActivity activity) {
        int widgetId = WidgetConfigureActivity.getAppWidgetId(activity);

        if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            throw new RuntimeException("Invalid widget id");
        }

        return widgetId;
    }

}
