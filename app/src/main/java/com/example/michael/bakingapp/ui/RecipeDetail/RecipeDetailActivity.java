package com.example.michael.bakingapp.ui.RecipeDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.example.michael.bakingapp.R;
import com.example.michael.bakingapp.data.schema.Recipe;
import com.example.michael.bakingapp.data.schema.Step;
import com.example.michael.bakingapp.ui.StepDetail.StepDetailFragment;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class RecipeDetailActivity extends DaggerAppCompatActivity
        implements RecipeDetailContract.View, StepsAdapter.ViewHolder.OnClickListener {

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

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
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
    public void setRecipe(Recipe recipe) {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(recipe.getName());
        }

        recipeDetailFragment.setRecipe(recipe);
    }

    @Override
    public void setStep(Step step) {
        if (stepDetailFragment != null) {
            stepDetailFragment.setStep(step);
        }
    }

    @Override
    public void onClick(Step step) {
        presenter.onStepClick(step);
    }

    @Override
    public boolean isMasterDetailLayout() {
        return stepDetailFragment != null;
    }
}
