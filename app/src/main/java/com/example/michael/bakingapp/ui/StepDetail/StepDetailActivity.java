package com.example.michael.bakingapp.ui.StepDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.michael.bakingapp.R;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


public class StepDetailActivity extends DaggerAppCompatActivity implements StepDetailContract.View {

    @Inject
    StepDetailContract.Presenter presenter;

    public static final String EXTRA_STEP = "EXTRA_STEP";
    private StepDetailFragment stepDetailFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        stepDetailFragment = new StepDetailFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main, stepDetailFragment)
                .commit();
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
    public void setVideoUrl(String videoUrl) {
        stepDetailFragment.setVideoUrl(videoUrl);
    }

    @Override
    public void setDescription(String description) {
        stepDetailFragment.setDescription(description);
    }
}
