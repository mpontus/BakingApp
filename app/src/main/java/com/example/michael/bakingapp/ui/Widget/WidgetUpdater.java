package com.example.michael.bakingapp.ui.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.michael.bakingapp.R;
import com.example.michael.bakingapp.data.Preferences;
import com.example.michael.bakingapp.data.Repository;
import com.example.michael.bakingapp.data.schema.Recipe;
import com.example.michael.bakingapp.ui.RecipeDetail.RecipeDetailActivity;
import com.google.gson.Gson;

import javax.inject.Inject;

public class WidgetUpdater {

    private Context context;

    private Repository repository;

    private Preferences preferences;

    private Gson gson;

    @Inject
    public WidgetUpdater(Context context, Repository repository, Preferences preferences, Gson gson) {
        this.context = context;
        this.repository = repository;
        this.preferences = preferences;
        this.gson = gson;
    }

    public void updateWidget(int appWidgetId) {
        long recipeId = preferences.getRecipeIdForWidget(appWidgetId);

        if (recipeId == -1) {
            return;
        }

        Recipe recipe = repository.getRecipeByIdSync(recipeId);
        Intent intent = new Intent(context, BakingAppWidgetSevice.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        // When intents are compared, the extras are ignored, so we need to embed the extras
        // into the data so that the extras will not be ignored.
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        views.setTextViewText(R.id.recipe, recipe.getName());
        views.setRemoteAdapter(R.id.ingredients, intent);

        // Set up onClick pending intent
        Intent openActivityIntent = new Intent(context, RecipeDetailActivity.class);
        openActivityIntent.putExtra(RecipeDetailActivity.EXTRA_RECIPE, gson.toJson(recipe));
        PendingIntent openActivityPendingIntent = PendingIntent.getActivity(context, 0, openActivityIntent, 0);
        views.setOnClickPendingIntent(R.id.root, openActivityPendingIntent);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
