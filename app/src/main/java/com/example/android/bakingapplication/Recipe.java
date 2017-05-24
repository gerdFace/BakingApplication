package com.example.android.bakingapplication;


public class Recipe {

    private String DessertName;
    private String numberOfSteps;
    private String numberOfIngredients;
    private String numberOfServings;
    private String thumbnail;

    public Recipe() {
    }


    public Recipe(String dessertName, String numberOfSteps, String numberOfIngredients, String numberOfServings, String thumbnail) {
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
