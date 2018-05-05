package com.example.michael.bakingapp.ui.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.michael.bakingapp.R;
import com.example.michael.bakingapp.data.Preferences;
import com.example.michael.bakingapp.data.Repository;

import javax.inject.Inject;

public class WidgetUpdater {

    private Context context;

    private Repository repository;

    private Preferences preferences;

    @Inject
    public WidgetUpdater(Context context, Repository repository, Preferences preferences) {
        this.context = context;
        this.repository = repository;
        this.preferences = preferences;
    }

    public void updateWidget(int appWidgetId) {
        long recipeId = preferences.getRecipeIdForWidget(appWidgetId);

        if (recipeId == -1) {
            return;
        }

        Intent intent = new Intent(context, BakingAppWidgetSevice.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        // When intents are compared, the extras are ignored, so we need to embed the extras
        // into the data so that the extras will not be ignored.
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        views.setRemoteAdapter(R.id.ingredients, intent);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
