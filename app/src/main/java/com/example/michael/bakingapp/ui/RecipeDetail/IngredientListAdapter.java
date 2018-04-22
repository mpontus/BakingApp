package com.example.michael.bakingapp.ui.RecipeDetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.michael.bakingapp.R;

import javax.inject.Inject;

class IngredientListAdapter extends RecyclerView.Adapter<IngredientViewHolder> {
    private int itemCount;

    private RecipeDetailContract.IngredientItemPresenterFactory ingredientItemPresenterFactory;

    @Inject
    IngredientListAdapter(RecipeDetailContract.IngredientItemPresenterFactory ingredientItemPresenterFactory) {
        this.ingredientItemPresenterFactory = ingredientItemPresenterFactory;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_item, parent, false);

        return new IngredientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        RecipeDetailContract.IngredientItemPresenter ingredientItemPresenter =
                ingredientItemPresenterFactory.getIngredientItemPresenter(holder, position);

        holder.attachPresenter(ingredientItemPresenter);
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
