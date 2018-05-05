package com.example.michael.bakingapp.ui.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.michael.bakingapp.R;
import com.example.michael.bakingapp.data.Preferences;
import com.example.michael.bakingapp.data.Repository;
import com.example.michael.bakingapp.data.schema.Ingredient;
import com.example.michael.bakingapp.data.schema.Recipe;

import io.reactivex.disposables.CompositeDisposable;

public class BakingAppRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final Context context;
    private final int widgetId;
    private final Repository repository;
    private final Preferences preferences;
    private final CompositeDisposable compositeDisposable;
    private Ingredient[] ingredients;

    public BakingAppRemoteViewsFactory(Context context,
                                       Intent intent,
                                       Repository repository,
                                       Preferences preferences,
                                       CompositeDisposable compositeDisposable) {
        this.context = context;
        this.widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        this.repository = repository;
        this.preferences = preferences;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void onCreate() {
        Recipe recipe = repository.getRecipeByIdSync(preferences.getRecipeIdForWidget(widgetId));

        this.ingredients = recipe.getIngredients();
    }

    @Override
    public void onDataSetChanged() {
        // Only neede when you call notifyAppWidgetViewDataChanged on AppWidgetManager
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
    }

    @Override
    public int getCount() {
        if (ingredients == null) {
            return 0;
        }

        return ingredients.length;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (ingredients == null) {
            return null;
        }

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_item);
        rv.setTextViewText(R.id.ingredient, ingredients[position].getIngredient());

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        // Returning null for default loading view
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
