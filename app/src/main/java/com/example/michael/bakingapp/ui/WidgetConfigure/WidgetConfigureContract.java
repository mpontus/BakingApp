package com.example.michael.bakingapp.ui.WidgetConfigure;

import com.example.michael.bakingapp.data.schema.Recipe;

public class WidgetConfigureContract {
    interface View {
        void setRecipes(Recipe[] recipes);

        void finishSuccess(int widgetId);
    }

    interface Presenter {
        void attach();

        void detach();

        void onSubmit(Recipe recipe);
    }
}
