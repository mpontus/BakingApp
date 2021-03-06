package com.example.michael.bakingapp.ui.StepDetail;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.michael.bakingapp.R;
import com.example.michael.bakingapp.data.schema.Step;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


public class StepDetailActivity extends DaggerAppCompatActivity implements StepDetailContract.View {

    @Inject
    StepDetailContract.Presenter presenter;

    public static final String EXTRA_RECIPE = "EXTRA_RECIPE";
    public static final String EXTRA_STEP = "EXTRA_STEP";
    private static final String FRAGMENT_TAG = "FRAGMENT_TAG";

    private StepDetailFragment stepDetailFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            stepDetailFragment = new StepDetailFragment();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, stepDetailFragment, FRAGMENT_TAG)
                    .commit();
        } else {
            stepDetailFragment = (StepDetailFragment) getSupportFragmentManager()
                    .findFragmentByTag(FRAGMENT_TAG);
        }

        stepDetailFragment.setFullscreenEnabled(true);
        stepDetailFragment.setOnNextClick(view -> {
            this.presenter.onNextClick();
        });
        stepDetailFragment.setOnPrevClick(view -> {
            this.presenter.onPrevClick();
        });

        applyWindowConfiguration(getResources().getConfiguration());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        applyWindowConfiguration(newConfig);
    }

    private void applyWindowConfiguration(Configuration config) {
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportActionBar().hide();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getSupportActionBar().show();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
    public void setStep(Step step) {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(step.getShortDescription());
        }

        stepDetailFragment.setStep(step);
    }

    @Override
    public void setNextStepHidden(boolean isHidden) {
        stepDetailFragment.setNextStepHidden(isHidden);
    }

    @Override
    public void setPrevStepHidden(boolean isHidden) {
        stepDetailFragment.setPrevStepHidden(isHidden);
    }
}
