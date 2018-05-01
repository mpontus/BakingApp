package com.example.michael.bakingapp.ui.RecipeList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.michael.bakingapp.R;
import com.example.michael.bakingapp.data.schema.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {

    private Recipe[] recipes;

    private ViewHolder.OnClickListener onClickListener;

    public RecipeListAdapter(ViewHolder.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false);

        return new ViewHolder(itemView, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipes[position];

        holder.setRecipe(recipe);
    }

    @Override
    public int getItemCount() {
        if (recipes == null) {
            return 0;
        }

        return recipes.length;
    }

    public void setRecipes(Recipe[] recipes) {
        this.recipes = recipes;

        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OnClickListener onClickListener;

        private Recipe recipe;

        @BindView(R.id.title)
        TextView titleView;

        ViewHolder(View itemView, OnClickListener onClickListener) {
            super(itemView);
            this.onClickListener = onClickListener;

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        public void setRecipe(Recipe recipe) {
            this.recipe = recipe;

            titleView.setText(recipe.getName());
        }

        public void onClick(View view) {
            this.onClickListener.onClick(this.recipe);
        }

        interface OnClickListener {
            void onClick(Recipe recipe);
        }
    }
}
