package com.example.michael.bakingapp.ui.utils;

import android.content.res.Resources;

import com.example.michael.bakingapp.R;
import com.example.michael.bakingapp.data.schema.Ingredient;

import java.text.DecimalFormat;

import javax.inject.Inject;

public class QuantityFormatter {
    private final Resources resources;

    @Inject
    public QuantityFormatter(Resources resources) {
        this.resources = resources;
    }

    public String getQuantityString(float quantity, Ingredient.Measure measure) {
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
