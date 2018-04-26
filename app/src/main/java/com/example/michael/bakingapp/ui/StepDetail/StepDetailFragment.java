package com.example.michael.bakingapp.ui.StepDetail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.michael.bakingapp.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends Fragment {

    @BindView(R.id.description)
    TextView descriptionView;

    @BindView(R.id.player)
    PlayerView playerView;

    private SimpleExoPlayer player;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.step_detail, container, false);

        ButterKnife.bind(this, itemView);

//        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//        TrackSelection.Factory videoTrackSelectionFactory =
//                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector();

        player = ExoPlayerFactory.newSimpleInstance(itemView.getContext(), trackSelector);
        playerView.setPlayer(player);

        return itemView;
    }

    public void setDescription(String description) {
        descriptionView.setText(description);
    }

    public void setVideoUrl(String videoUrl) {
        Context context = getContext();

        if (context == null) {
            return;
        }


        DefaultDataSourceFactory dataSourceFactory =
                new DefaultDataSourceFactory(context, Util.getUserAgent(context, "BakingApp"));

        ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(videoUrl));

        player.prepare(mediaSource);

        player.setPlayWhenReady(true);
    }
}
