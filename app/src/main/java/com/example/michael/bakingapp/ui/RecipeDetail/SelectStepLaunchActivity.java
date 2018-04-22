package com.example.michael.bakingapp.ui.RecipeDetail;

import com.example.michael.bakingapp.data.schema.Step;

import javax.inject.Inject;

public class SelectStepLaunchActivity implements RecipeDetailContract.SelectStepStrategy {
    private RecipeDetailContract.View view;

    @Inject
    public SelectStepLaunchActivity(RecipeDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void onSelectStep(Step step) {
        view.launchStepActivity(step);
    }
}
