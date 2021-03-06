package com.example.michael.bakingapp.ui.RecipeDetail;

import com.example.michael.bakingapp.data.Navigator;
import com.example.michael.bakingapp.data.schema.Recipe;
import com.example.michael.bakingapp.data.schema.Step;
import com.google.android.exoplayer2.SimpleExoPlayer;

import javax.inject.Inject;

public class RecipeDetailPresenter implements RecipeDetailContract.Presenter {

    private RecipeDetailContract.View view;
    private Recipe recipe;
    private Navigator navigator;
    private SimpleExoPlayer exoPlayer;

    @Inject
    public RecipeDetailPresenter(RecipeDetailContract.View view, Recipe recipe, Navigator navigator, SimpleExoPlayer exoPlayer) {
        this.view = view;
        this.recipe = recipe;
        this.navigator = navigator;
        this.exoPlayer = exoPlayer;
    }

    @Override
    public void attach() {
        view.setRecipe(recipe);


        Step[] steps = recipe.getSteps();

        if (steps.length > 0) {
            view.setDefaultStep(steps[0]);
        }
    }

    @Override
    public void detach() {
    }

    @Override
    public void onStepClick(Step step) {
        if (view.isMasterDetailLayout()) {
            exoPlayer.setPlayWhenReady(false);
            view.setStep(step);


            return;
        }

        Step[] steps = recipe.getSteps();

        navigator.launchStepDetailActivity(recipe, step);
    }
}
