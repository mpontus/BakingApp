package com.example.michael.bakingapp.data;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class Preferences {

    public static final long NONEXISTENT_RECIPE_ID = -1;

    private static final String WIDGET_RECIPE_PREFERENCE_PREFIX = "widget_recipe_";

    private final SharedPreferences sharedPreferences;

    @Inject
    public Preferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public long getRecipeIdForWidget(int widgetId) {
        return sharedPreferences.getLong(getWidgetRecipePreferenceName(widgetId),
                NONEXISTENT_RECIPE_ID);
    }

    public void setRecipeIdForWidget(int widgetId, long recipeId) {
        sharedPreferences.edit()
                .putLong(getWidgetRecipePreferenceName(widgetId), recipeId)
                .apply();
    }

    public void deleteWidgetPreferences(int widgetId) {
        sharedPreferences
                .edit()
                .remove(getWidgetRecipePreferenceName(widgetId))
                .apply();
    }


    private String getWidgetRecipePreferenceName(int widgetId) {
        return WIDGET_RECIPE_PREFERENCE_PREFIX + widgetId;
    }
}
