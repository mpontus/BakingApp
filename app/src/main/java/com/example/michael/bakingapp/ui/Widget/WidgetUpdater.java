package com.example.michael.bakingapp.ui.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
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

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

        repository.getRecipeById(recipeId)
                .subscribe(recipe -> {
                    views.setTextViewText(R.id.appwidget_text, recipe.getName());

                    // Instruct the widget manager to update the widget
                    appWidgetManager.updateAppWidget(appWidgetId, views);
                });
    }
}
