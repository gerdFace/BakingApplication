package com.example.android.bakingapplication;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class MockRecipeModel {

    private List<KRecipe> recipeDataList;
    private ArrayList<KIngredient> ingredientList;
    private ArrayList<KStep> stepList;

    public MockRecipeModel() {
	    recipeDataList = new ArrayList<>();
	    ingredientList = new ArrayList<>();
	    stepList = new ArrayList<>();
	    addFakeRecipes();
    }
	
	public List<KRecipe> getRecipeDataList() {
		return recipeDataList;
	}
	
	public KRecipe getKRecipe(String foodItemName) {
		for (KRecipe kRecipe : recipeDataList) {
			if (kRecipe.getDessertName().equals(foodItemName)) {
				return kRecipe;
			}
		}
		return null;
	}
	
	public void addFakeRecipes() {
        int[] thumbnails = new int[] {
                R.drawable.nutella_pie,
                R.drawable.brownies,
                R.drawable.yellow_cake,
                R.drawable.cheesecake};

        ingredientList.add("2 eggs");
        ingredientList.add("1 cup Flour");
        ingredientList.add("1 cup Sugar");
        ingredientList.add("10 oz Nutella");

        stepList.add("Ingredients");
        stepList.add("Step 1");
        stepList.add("Step 2");
        stepList.add("Step 3");
        stepList.add("Step 4");
        stepList.add("Step 5");

        stepDescriptionList.add("Prep the cookie crust.");
        stepDescriptionList.add("Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.");
        stepDescriptionList.add("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4");

        KRecipe testRecipe = new KRecipe("Nutella Pie", "6", "8", "8", stepList, ingredientList, stepDescriptionList, thumbnails[0]);
        Log.d(TAG, "Image URL: " + thumbnails[0]);
        recipeDataList.add(testRecipe);

        testRecipe = new KRecipe("Brownies", "6", "10", "10", stepList, ingredientList, stepDescriptionList, thumbnails[1]);
        recipeDataList.add(testRecipe);

        testRecipe = new KRecipe("Yellow Cake", "3", "10", "15", stepList, ingredientList, stepDescriptionList, thumbnails[2]);
        recipeDataList.add(testRecipe);

        testRecipe = new KRecipe("Cheesecake", "10", "4", "6", stepList, ingredientList, stepDescriptionList, thumbnails[3]);
        recipeDataList.add(testRecipe);

    }

}


