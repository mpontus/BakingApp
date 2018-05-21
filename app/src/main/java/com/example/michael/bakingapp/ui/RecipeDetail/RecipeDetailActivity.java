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
import com.google.gson.Gson;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class RecipeDetailActivity extends DaggerAppCompatActivity
        implements RecipeDetailContract.View, StepsAdapter.ViewHolder.OnClickListener {

    public static final String EXTRA_RECIPE = "EXTRA_RECIPE";
    private static final String SAVED_STEP = "SAVED_STEP";

    @Inject
    RecipeDetailContract.Presenter presenter;

    @Inject
    Gson gson;

    private RecipeDetailFragment recipeDetailFragment;
    private StepDetailFragment stepDetailFragment;
    private Step selectedStep = null;
    private Bundle savedInstanceState = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipeDetailFragment = new RecipeDetailFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.main, recipeDetailFragment);

        if (findViewById(R.id.secondary) != null) {
            stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setHideControls(true);

            fragmentTransaction.replace(R.id.secondary, stepDetailFragment);
        }

        fragmentTransaction.commit();

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        this.savedInstanceState = savedInstanceState;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (selectedStep != null) {
            outState.putString(SAVED_STEP, gson.toJson(selectedStep));
        }

        super.onSaveInstanceState(outState);
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

        if (savedInstanceState != null) {
            String serializedStep = savedInstanceState.getString(SAVED_STEP);

            if (serializedStep != null) {
                setStep(gson.fromJson(serializedStep, Step.class));
            }
        }
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
            selectedStep = step;
            stepDetailFragment.setStep(step);
        }
    }

    @Override
    public void setDefaultStep(Step step) {
        if (selectedStep == null) {
            setStep(step);
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
