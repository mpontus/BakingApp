package com.example.michael.bakingapp.ui.RecipeDetail;

import com.example.michael.bakingapp.data.schema.Step;

import javax.inject.Inject;


public class SelectStepUpdateView implements RecipeDetailContract.SelectStepStrategy {
    private RecipeDetailContract.View view;

    @Inject
    public SelectStepUpdateView(RecipeDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void onSelectStep(Step step) {
        view.setStepVideoUrl(step.getVideoURL());

        view.setStepDescription(step.getDescription());
    }
}
