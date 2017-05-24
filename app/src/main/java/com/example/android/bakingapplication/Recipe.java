package com.example.android.bakingapplication;

import android.util.Log;

public class Recipe {

    public static final String TAG = Recipe.class.getSimpleName();
    private String DessertName;
    private String numberOfSteps;
    private String numberOfIngredients;
    private String numberOfServings;
    private int thumbnail;

    public Recipe(String dessertName, String numberOfSteps, String numberOfIngredients, String numberOfServings, int thumbnail) {
        DessertName = dessertName;
        this.numberOfSteps = numberOfSteps;
        this.numberOfIngredients = numberOfIngredients;
        this.numberOfServings = numberOfServings;
        this.thumbnail = thumbnail;
    }

    public String getDessertName() {
        return DessertName;
    }

    public void setDessertName(String dessertName) {
        DessertName = dessertName;
    }

    public String getNumberOfSteps() {
        return numberOfSteps;
    }

    public void setNumberOfSteps(String numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }

    public String getNumberOfIngredients() {
        return numberOfIngredients;
    }

    public void setNumberOfIngredients(String numberOfIngredients) {
        this.numberOfIngredients = numberOfIngredients;
    }

    public String getNumberOfServings() {
        return numberOfServings;
    }

    public void setNumberOfServings(String numberOfServings) {
        this.numberOfServings = numberOfServings;
    }

    public int getThumbnail() {
        Log.d(TAG, "Thumbnail URL: " + thumbnail);
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
