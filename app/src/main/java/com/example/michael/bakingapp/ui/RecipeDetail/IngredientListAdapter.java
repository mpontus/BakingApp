package com.example.michael.bakingapp.ui.RecipeDetail;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.michael.bakingapp.R;
import com.example.michael.bakingapp.data.schema.Ingredient;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder> {
    private Ingredient[] ingredients;

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_item, parent, false);

        return new IngredientViewHolder(itemView);
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

        @BindView(R.id.ingredient)
        TextView ingredientView;

        @BindView(R.id.quantity)
        TextView quantityView;

        public IngredientViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void setIngredient(Ingredient ingredient) {
            ingredientView.setText(ingredient.getIngredient());
            quantityView.setText(getQuantityString(ingredient.getQuantity(), ingredient.getMeasure()));
        }

        private String getQuantityString(float quantity, Ingredient.Measure measure) {
            Resources resources = itemView.getResources();
            int measureStringResource = getMeasureResourceString(measure);
            DecimalFormat quantityFormat = quantity == (int) quantity
                    ? new DecimalFormat("#")
                    : new DecimalFormat("#.#");

            return resources.getQuantityString(measureStringResource,
                    (int) quantity,
                    quantityFormat.format(quantity));
        }

        private int getMeasureResourceString(Ingredient.Measure measure) {
            switch (measure) {
                case CUP:
                    return R.plurals.quantity_cup;
                case TBLSP:
                    return R.plurals.quantity_tblsp;
                case TSP:
                    return R.plurals.quantity_tsp;
                case K:
                    return R.plurals.quantity_k;
                case G:
                    return R.plurals.quantity_g;
                case OZ:
                    return R.plurals.quantity_oz;
                case UNIT:
                    return R.plurals.quantity_unit;
                default:
                    throw new RuntimeException("Unknown measurement");
            }
        }
    }
}
