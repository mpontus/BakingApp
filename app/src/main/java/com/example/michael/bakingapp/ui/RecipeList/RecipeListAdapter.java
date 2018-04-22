package com.example.michael.bakingapp.ui.RecipeList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.michael.bakingapp.R;

import javax.inject.Inject;


public class RecipeListAdapter extends RecyclerView.Adapter<RecipeItemViewHolder> {
    private int itemCount;

    private RecipeListContract.ItemPresenterFactory itemPresenterFactory;

    @Inject
    public RecipeListAdapter(RecipeListContract.ItemPresenterFactory itemPresenterFactory) {
        this.itemPresenterFactory = itemPresenterFactory;
    }

    @NonNull
    @Override
    public RecipeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false);

        return new RecipeItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeItemViewHolder holder, int position) {
        RecipeListContract.ItemPresenter presenter =
                itemPresenterFactory.getItemPresenter(holder, position);

        holder.attachPresenter(presenter);
    }

    @Override
    public int getItemCount() {
        return this.itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;

        notifyDataSetChanged();
    }
}
