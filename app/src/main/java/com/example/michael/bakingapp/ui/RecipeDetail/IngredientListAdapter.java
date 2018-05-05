package com.example.michael.bakingapp.ui.RecipeDetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.michael.bakingapp.R;
import com.example.michael.bakingapp.data.schema.Ingredient;
import com.example.michael.bakingapp.ui.utils.QuantityFormatter;

import butterknife.BindView;
import butterknife.ButterKnife;

class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder> {

    private final QuantityFormatter quantityFormatter;

    private Ingredient[] ingredients;

    IngredientListAdapter(QuantityFormatter quantityFormatter) {
        this.quantityFormatter = quantityFormatter;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_item, parent, false);

        return new IngredientViewHolder(itemView, quantityFormatter);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.setIngredient(ingredients[position]);
    }

    @Override
    public int getItemCount() {
        if (ingredients == null) {
            return 0;
        }

        return ingredients.length;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = ingredients;

        notifyDataSetChanged();
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {

        private final QuantityFormatter quantityFormatter;

        @BindView(R.id.ingredient)
        TextView ingredientView;

        @BindView(R.id.quantity)
        TextView quantityView;

        public IngredientViewHolder(View itemView, QuantityFormatter quantityFormatter) {
            super(itemView);
            this.quantityFormatter = quantityFormatter;

            ButterKnife.bind(this, itemView);
        }

        public void setIngredient(Ingredient ingredient) {
            String quantityString = quantityFormatter.getQuantityString(ingredient.getQuantity(),
                    ingredient.getMeasure());

            ingredientView.setText(ingredient.getIngredient());
            quantityView.setText(quantityString);
        }
    }
}
