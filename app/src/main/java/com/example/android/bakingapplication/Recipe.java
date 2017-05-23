package com.example.android.bakingapplication;


public class Recipe {

    private String DessertName;
    private int numberOfSteps;
    private int numberOfIngredients;
    private int numberOfServings;
    private String thumbnail;

    public Recipe() {
    }


    public Recipe(String dessertName, int numberOfSteps, int numberOfIngredients, int numberOfServings, String thumbnail) {
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

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    public void setNumberOfSteps(int numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }

    public int getNumberOfIngredients() {
        return numberOfIngredients;
    }

    public void setNumberOfIngredients(int numberOfIngredients) {
        this.numberOfIngredients = numberOfIngredients;
    }

    public int getNumberOfServings() {
        return numberOfServings;
    }

    public void setNumberOfServings(int numberOfServings) {
        this.numberOfServings = numberOfServings;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
