package com.example.michael.bakingapp.ui.RecipeDetail;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.michael.bakingapp.R;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

class IngredientViewHolder extends RecyclerView.ViewHolder
        implements RecipeDetailContract.IngredientView {

    @BindView(R.id.ingredient)
    TextView ingredientView;

    @BindView(R.id.quantity)
    TextView quantityView;

    private RecipeDetailContract.IngredientItemPresenter presenter;
    private float quantity = 0;
    private RecipeDetailContract.Measure measure = RecipeDetailContract.Measure.UNIT;

    public IngredientViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void attachPresenter(RecipeDetailContract.IngredientItemPresenter presenter) {
        if (this.presenter != null) {
            this.presenter.detach();
        }

        this.presenter = presenter;

        presenter.attach();
    }

    @Override
    public void setIngredient(String ingredient) {
        ingredientView.setText(ingredient);
    }

    @Override
    public void setQuantity(float quantity) {
        this.quantity = quantity;

        updateQuantityView();
    }

    @Override
    public void setMeasure(RecipeDetailContract.Measure measure) {
        this.measure = measure;

        updateQuantityView();
    }

    private void updateQuantityView() {
        quantityView.setText(getQuantityString(quantity, measure));
    }

    private String getQuantityString(float quantity, RecipeDetailContract.Measure measure) {
        Resources resources = itemView.getResources();
        int measureStringResource = getMeasureResourceString(measure);
        DecimalFormat quantityFormat = quantity == (int) quantity
                ? new DecimalFormat("#")
                : new DecimalFormat("#.#");

        return resources.getQuantityString(measureStringResource,
                (int) quantity,
                quantityFormat.format(quantity));
    }

    private int getMeasureResourceString(RecipeDetailContract.Measure measure) {
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
