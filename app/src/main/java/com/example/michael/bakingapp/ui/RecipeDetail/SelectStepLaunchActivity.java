package com.example.michael.bakingapp.ui.RecipeDetail;

import com.example.michael.bakingapp.data.Navigator;
import com.example.michael.bakingapp.data.schema.Step;

import javax.inject.Inject;

public class SelectStepLaunchActivity implements RecipeDetailContract.SelectStepStrategy {
    private Navigator navigator;

    @Inject
    public SelectStepLaunchActivity(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void onSelectStep(Step step) {
        navigator.launchStepDetailActivity(step);
    }
}
