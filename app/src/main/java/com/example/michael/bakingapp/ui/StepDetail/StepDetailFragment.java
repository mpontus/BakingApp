package com.example.michael.bakingapp.ui.StepDetail;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.michael.bakingapp.R;
import com.example.michael.bakingapp.data.schema.Step;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

public class StepDetailFragment extends DaggerFragment {

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
        View itemView = inflater.inflate(R.layout.step_detail, container, false);

        ButterKnife.bind(this, itemView);

        TrackSelector trackSelector = new DefaultTrackSelector();

        playerView.setPlayer(player);

        return itemView;
    }

    public void setStep(Step step) {
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
