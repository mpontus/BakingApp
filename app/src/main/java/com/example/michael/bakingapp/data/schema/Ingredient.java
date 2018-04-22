package com.example.michael.bakingapp.data.schema;

import com.google.gson.annotations.SerializedName;

public class Ingredient {

    private float quantity;

    private Measure measure;

    private String ingredient;

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public enum Measure {

        @SerializedName("CUP")
        CUP,

        @SerializedName("TBLSP")
        TBLSP,

        @SerializedName("TSP")
        TSP,

        @SerializedName("K")
        K,

        @SerializedName("G")
        G,

        @SerializedName("OZ")
        OZ,

        @SerializedName("UNIT")
        UNIT

    }
}
