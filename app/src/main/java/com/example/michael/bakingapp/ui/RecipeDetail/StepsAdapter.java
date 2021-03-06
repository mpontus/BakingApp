package com.example.michael.bakingapp.ui.RecipeDetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.michael.bakingapp.R;
import com.example.michael.bakingapp.data.schema.Step;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {
    private ViewHolder.OnClickListener onClickListener;

    private Step[] steps;

    public StepsAdapter(ViewHolder.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_item, parent, false);

        return new ViewHolder(itemView, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setStep(steps[position]);
    }

    @Override
    public int getItemCount() {
        if (steps == null) {
            return 0;
        }

        return steps.length;
    }

    public void setSteps(Step[] steps) {
        this.steps = steps;

        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnClickListener onClickListener;

        private Step step;

        @BindView(R.id.title)
        TextView titleView;

        @BindView(R.id.hasVideoIcon)
        ImageView hasVideoIcon;

        @BindView(R.id.noVideoIcon)
        ImageView noVideoIcon;

        @BindView(R.id.thumbnail)
        ImageView thumbnailView;

        public ViewHolder(View itemView, OnClickListener onClickListener) {
            super(itemView);
            this.onClickListener = onClickListener;

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        public void setStep(Step step) {
            this.step = step;

            titleView.setText(step.getShortDescription());

            if (step.getVideoURL().isEmpty()) {
                hasVideoIcon.setVisibility(View.GONE);
                noVideoIcon.setVisibility(View.VISIBLE);
            } else {
                noVideoIcon.setVisibility(View.GONE);
                hasVideoIcon.setVisibility(View.VISIBLE);
            }

            if (!step.getThumbnailURL().isEmpty()) {
                Picasso.get().load(step.getThumbnailURL()).into(thumbnailView);
            } else {
                thumbnailView.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            this.onClickListener.onClick(this.step);
        }

        interface OnClickListener {
            public void onClick(Step step);
        }
    }
}
