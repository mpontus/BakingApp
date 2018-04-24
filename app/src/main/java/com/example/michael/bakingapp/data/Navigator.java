package com.example.michael.bakingapp.data;

import android.content.Context;
import android.content.Intent;

import com.example.michael.bakingapp.data.schema.Recipe;
import com.example.michael.bakingapp.data.schema.Step;
import com.example.michael.bakingapp.di.ActivityContext;
import com.example.michael.bakingapp.ui.RecipeDetail.RecipeDetailActivity;
import com.example.michael.bakingapp.ui.StepDetail.StepDetailActivity;
import com.google.gson.Gson;

import javax.inject.Inject;

public class Navigator {
    private Context context;
    private Gson gson;

    @Inject
    public Navigator(@ActivityContext Context context, Gson gson) {
        this.context = context;
        this.gson = gson;
    }

    public void launchRecipeDetailActivity(Recipe recipe) {
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        String serializedRecipe = gson.toJson(recipe);

        intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE, serializedRecipe);

        context.startActivity(intent);
    }

    public void launchStepDetailActivity(Step step) {
        Intent intent = new Intent(context, StepDetailActivity.class);
        String serializedStep = gson.toJson(step);

        intent.putExtra(StepDetailActivity.EXTRA_STEP, serializedStep);

        context.startActivity(intent);
    }
}
