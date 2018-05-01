package com.example.michael.bakingapp.ui.RecipeList;

import com.example.michael.bakingapp.data.schema.Recipe;

public interface RecipeListContract {

    interface View {
        void setRecipes(Recipe[] recipes);
    }

    interface Presenter {
        void attach();

        void detach();

        void onRecipeClick(Recipe recipe);
    }
}
