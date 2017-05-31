package com.example.android.bakingapplication;


import android.util.Log;

import java.util.List;

public class FakeRecipeData {

    public static final String TAG = FakeRecipeData.class.getSimpleName();
    private List<KRecipe> recipeList;

    public FakeRecipeData(List<KRecipe> recipeList) {
        this.recipeList = recipeList;
    }

    public void addFakeRecipes() {
        int[] thumbnails = new int[] {
                R.mipmap.nutella_pie,
                R.mipmap.brownies,
                R.mipmap.yellow_cake,
                R.mipmap.cheesecake};

        KRecipe testRecipe = new KRecipe("Nutella Pie", "6", "8", "8", thumbnails[0]);
        Log.d(TAG, "Image URL: " + thumbnails[0]);
        recipeList.add(testRecipe);

        testRecipe = new KRecipe("Brownies", "6", "10", "10", thumbnails[1]);
        recipeList.add(testRecipe);

        testRecipe = new KRecipe("Yellow Cake", "3", "10", "15", thumbnails[2]);
        recipeList.add(testRecipe);

        testRecipe = new KRecipe("Cheesecake", "10", "4", "6", thumbnails[3]);
        recipeList.add(testRecipe);

    }

}
