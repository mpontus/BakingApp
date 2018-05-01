package com.example.michael.bakingapp.ui.RecipeDetail;

import com.example.michael.bakingapp.data.Navigator;
import com.example.michael.bakingapp.data.schema.Recipe;
import com.example.michael.bakingapp.data.schema.Step;

import javax.inject.Inject;

public class RecipeDetailPresenter implements RecipeDetailContract.Presenter {

    private RecipeDetailContract.View view;
    private Recipe recipe;
    private Navigator navigator;

    @Inject
    public RecipeDetailPresenter(RecipeDetailContract.View view, Recipe recipe, Navigator navigator) {
        this.view = view;
        this.recipe = recipe;
        this.navigator = navigator;
    }

    @Override
    public void attach() {
        view.setRecipe(recipe);
    }

    @Override
    public void detach() {
    }

    @Override
    public void onStepClick(Step step) {
        if (view.isMasterDetailLayout()) {
            view.setStep(step);

            return;
        }

        navigator.launchStepDetailActivity(step);
    }
}
