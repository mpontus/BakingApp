package com.example.michael.bakingapp.ui.RecipeDetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.michael.bakingapp.R;

import javax.inject.Inject;

public class StepListAdapter extends RecyclerView.Adapter<StepViewHolder> {
    private int itemCount;

    private RecipeDetailContract.StepItemPresenterFactory stepItemPresenterFactory;

    @Inject
    public StepListAdapter(RecipeDetailContract.StepItemPresenterFactory stepItemPresenterFactory) {
        this.stepItemPresenterFactory = stepItemPresenterFactory;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_item, parent, false);

        return new StepViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        RecipeDetailContract.StepItemPresenter presenter =
                stepItemPresenterFactory.getStepItemPresenter(holder, position);

        holder.attachPresenter(presenter);
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;

        notifyDataSetChanged();
    }
}
