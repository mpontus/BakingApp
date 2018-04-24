package com.example.michael.bakingapp.ui.RecipeDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.example.michael.bakingapp.R;
import com.example.michael.bakingapp.ui.StepDetail.StepDetailFragment;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class RecipeDetailActivity extends DaggerAppCompatActivity
        implements RecipeDetailContract.View {

    @Inject
    RecipeDetailContract.Presenter presenter;

    public static final String EXTRA_RECIPE = "EXTRA_RECIPE";

    RecipeDetailFragment recipeDetailFragment;
    StepDetailFragment stepDetailFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipeDetailFragment = new RecipeDetailFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.main, recipeDetailFragment);

        if (findViewById(R.id.secondary) != null) {
            stepDetailFragment = new StepDetailFragment();

            fragmentTransaction.add(R.id.secondary, stepDetailFragment);
        }

        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.attach();
    }

    @Override
    protected void onPause() {
        super.onPause();

        presenter.detach();
    }

    @Override
    public void setIngredientCount(int count) {
        recipeDetailFragment.setIngredientsCount(count);
    }

    @Override
    public void setStepCount(int count) {
        recipeDetailFragment.setStepsCount(count);
    }

    @Override
    public void setStepVideoUrl(String videoUrl) {
        if (stepDetailFragment != null) {
            stepDetailFragment.setVideoUrl(videoUrl);
        }
    }

    @Override
    public void setStepDescription(String description) {
        if (stepDetailFragment != null) {
            stepDetailFragment.setDescription(description);
        }
    }

    public boolean isMasterDetailLayout() {
        return stepDetailFragment != null;
    }
}
