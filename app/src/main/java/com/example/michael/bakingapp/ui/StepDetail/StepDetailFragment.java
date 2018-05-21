package com.example.michael.bakingapp.ui.StepDetail;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.michael.bakingapp.R;
import com.example.michael.bakingapp.data.schema.Step;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import dagger.android.support.DaggerFragment;

public class StepDetailFragment extends DaggerFragment {

    // We use FrameLayout to host the inflated layout to simplify its repopulation during
    // orientation chnage
    private FrameLayout frameLayout;

    // We store step details in order to repopulate the views after configuraiton change
    private Step step;

    // Disable player fullscreen on configuration change
    private boolean fullscreenEnabled = false;

    // Hide controls in master-detail layout
    private boolean hideControls = false;

    private View.OnClickListener onNextClick;

    private View.OnClickListener onPrevClick;

    @Nullable
    @BindView(R.id.description)
    TextView descriptionView;

    @BindView(R.id.player)
    PlayerView playerView;

    @Nullable
    @BindView(R.id.controls)
    ViewGroup controlsView;

    @Nullable
    @BindView(R.id.btnNext)
    Button nextButton;

    @Nullable
    @BindView(R.id.btnPrev)
    Button prevButton;

    @Inject
    SimpleExoPlayer player;

    @Inject
    ExtractorMediaSource.Factory mediaSourceFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        frameLayout = new FrameLayout(getActivity());

        Configuration configuration = getResources().getConfiguration();
        boolean fullscreen = fullscreenEnabled &&
                configuration.orientation == Configuration.ORIENTATION_LANDSCAPE;

        return inflateLayout(inflater, fullscreen);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        frameLayout.removeAllViews();

        boolean fullscreen = fullscreenEnabled &&
                newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE;

        inflateLayout(LayoutInflater.from(getContext()), fullscreen);
    }


    /**
     * We handle configuration changes manually in order to continue playback on rotation.
     * <p>
     * We extract the inflation logic in its own method in order to simplify re-inflation on
     * orientation change.
     */
    private View inflateLayout(LayoutInflater inflater, boolean onlyPlayer) {
        ViewGroup itemView = (ViewGroup) inflater.inflate(onlyPlayer
                        ? R.layout.step_detail_fullscreen
                        : R.layout.step_detail,
                null);

        ButterKnife.bind(this, itemView);

        playerView.setPlayer(player);
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        frameLayout.addView(itemView);

        // Repopulate the views if the step details were already set.
        if (step != null && descriptionView != null) {
            descriptionView.setText(step.getDescription());
        }

        if (this.hideControls && this.controlsView != null) {
            this.controlsView.setVisibility(View.GONE);
        }


        return frameLayout;
    }

    public void setStep(Step step) {
        this.step = step;

        if (descriptionView != null) {
            String description = step.getDescription();

            if (description.isEmpty()) {
                description = step.getShortDescription();
            }

            descriptionView.setText(description);
        }


        if (!step.getVideoURL().isEmpty()) {
            Uri uri = Uri.parse(step.getVideoURL());
            ExtractorMediaSource mediaSource = mediaSourceFactory.createMediaSource(uri);

            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
            playerView.setVisibility(View.VISIBLE);
        } else {
            playerView.setVisibility(View.GONE);
        }
    }

    public void setFullscreenEnabled(boolean fullscreenEnabled) {
        this.fullscreenEnabled = fullscreenEnabled;
    }

    public void setHideControls(boolean hideControls) {
        this.hideControls = hideControls;
    }

    public void setOnNextClick(View.OnClickListener onNextClick) {
        this.onNextClick = onNextClick;
    }

    public void setOnPrevClick(View.OnClickListener onPrevClick) {
        this.onPrevClick = onPrevClick;
    }

    public void setNextStepHidden(boolean isHidden) {
        if (this.nextButton == null) {
            return;
        }

        this.nextButton.setVisibility(isHidden ? View.GONE : View.VISIBLE);
    }

    public void setPrevStepHidden(boolean isHidden) {
        if (this.prevButton == null) {
            return;
        }

        this.prevButton.setVisibility(isHidden ? View.GONE : View.VISIBLE);
    }

    @Optional
    @OnClick(R.id.btnNext)
    void handleNextClick(View view) {
        if (this.onNextClick != null) {
            this.onNextClick.onClick(view);
        }
    }

    @Optional
    @OnClick(R.id.btnPrev)
    void handlePrevClick(View view) {
        if (this.onPrevClick != null) {
            this.onPrevClick.onClick(view);
        }
    }
}
