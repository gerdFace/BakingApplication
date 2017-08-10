package com.example.android.bakingapplication;

import android.util.Log;

import com.example.android.bakingapplication.model.Ingredient;
import com.example.android.bakingapplication.model.RecipeData;
import com.example.android.bakingapplication.model.Step;

import java.util.ArrayList;
import java.util.List;

public class MockRecipeData {

    private List<RecipeData> recipeList;
    private List<Ingredient> ingredientList;
    private ArrayList<Step> stepList;

    public MockRecipeData() {
	    recipeList = new ArrayList<>();
	    ingredient1 = new ArrayList<>();
	    stepList = new ArrayList<>();
	    addFakeRecipes();
    }
	
	public List<KRecipe> getRecipeList() {
		return recipeList;
	}


	public void addFakeIngredients() {

        ingredient1.add("2 eggs");
        ingredient1.add("1 cup Flour");
        ingredient1.add("1 cup Sugar");
        ingredient1.add("10 oz Nutella");

        stepList.add("Ingredients");
        stepList.add("Step 1");
        stepList.add("Step 2");
        stepList.add("Step 3");
        stepList.add("Step 4");
        stepList.add("Step 5");

        stepDescriptionList.add("Prep the cookie crust.");
        stepDescriptionList.add("Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.");
        stepDescriptionList.add("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4");

        KRecipe testRecipe = new KRecipe("Nutella Pie", "6", "8", "8", stepList, ingredient1, stepDescriptionList, thumbnails[0]);
        Log.d(TAG, "Image URL: " + thumbnails[0]);
        recipeList.add(testRecipe);

        testRecipe = new KRecipe("Brownies", "6", "10", "10", stepList, ingredient1, stepDescriptionList, thumbnails[1]);
        recipeList.add(testRecipe);

        testRecipe = new KRecipe("Yellow Cake", "3", "10", "15", stepList, ingredient1, stepDescriptionList, thumbnails[2]);
        recipeList.add(testRecipe);

        testRecipe = new KRecipe("Cheesecake", "10", "4", "6", stepList, ingredient1, stepDescriptionList, thumbnails[3]);
        recipeList.add(testRecipe);

    }

}


