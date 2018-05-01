package com.example.michael.bakingapp.ui.RecipeDetail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.michael.bakingapp.R;
import com.example.michael.bakingapp.data.schema.Ingredient;

public class IngredientsAdapter extends BaseAdapter {
    private Ingredient[] ingredients;

    @Override
    public int getCount() {
        if (ingredients == null) {
            return 0;
        }

        return ingredients.length;
    }

    @Override
    public Ingredient getItem(int position) {
        return ingredients[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ingredient_item, parent, false);
        }

        Ingredient ingredient = getItem(position);

        ((TextView) convertView.findViewById(R.id.ingredient))
                .setText(ingredient.getIngredient());

        ((TextView) convertView.findViewById(R.id.quantity))
                .setText(ingredient.getIngredient());

        return convertView;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = ingredients;

        notifyDataSetChanged();
    }
}
