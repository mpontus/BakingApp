package com.example.michael.bakingapp.ui.StepDetail;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import dagger.android.support.DaggerFragment;

public class StepDetailFragment extends DaggerFragment {

    // We use FrameLayout to host the inflated layout to simplify its repopulation during
    // orientation chnage
    private FrameLayout frameLayout;

    // We store step details in order to repopulate the views after configuraiton change
    private Step step;

    @Nullable
    @BindView(R.id.description)
    TextView descriptionView;

    @BindView(R.id.player)
    PlayerView playerView;

    @Inject
    SimpleExoPlayer player;

    @Inject
    ExtractorMediaSource.Factory mediaSourceFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        frameLayout = new FrameLayout(getActivity());

        return inflateLayout(inflater);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        frameLayout.removeAllViews();

        inflateLayout(LayoutInflater.from(getContext()));
    }


    /**
     * We handle configuration changes manually in order to continue playback on rotation.
     * <p>
     * We extract the inflation logic in its own method in order to simplify re-inflation on
     * orientation change.
     */
    private View inflateLayout(LayoutInflater inflater) {
        View itemView = inflater.inflate(R.layout.step_detail, null);

        ButterKnife.bind(this, itemView);

        playerView.setPlayer(player);

        frameLayout.addView(itemView);

        // Repopulate the views if the step details were already set.
        if (step != null) {
            setStep(step);
        }

        return frameLayout;
    }

    public void setStep(Step step) {
        this.step = step;

        if (descriptionView != null) {
            descriptionView.setText(step.getDescription());
        }


        if (player.getContentPosition() == 0) {
            Uri uri = Uri.parse(step.getVideoURL());
            ExtractorMediaSource mediaSource = mediaSourceFactory.createMediaSource(uri);

            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        }
    }

}
