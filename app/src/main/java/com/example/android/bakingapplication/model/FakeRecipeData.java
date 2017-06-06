package com.example.android.bakingapplication.model;

import android.util.Log;
import com.example.android.bakingapplication.R;

import java.util.ArrayList;
import java.util.List;

public class FakeRecipeData {

    public static final String TAG = FakeRecipeData.class.getSimpleName();
    private List<KRecipe> recipeList;
    private List<String> ingredientList = new ArrayList<>();
    private List<String> detailList = new ArrayList<>();

    public FakeRecipeData(List<KRecipe> recipeList) {
        this.recipeList = recipeList;
    }


    public void addFakeRecipes() {
        int[] thumbnails = new int[] {
                R.mipmap.nutella_pie,
                R.mipmap.brownies,
                R.mipmap.yellow_cake,
                R.mipmap.cheesecake};

        ingredientList.add("2 eggs");
        ingredientList.add("1 cup Flour");
        ingredientList.add("1 cup Sugar");
        ingredientList.add("10 oz Nutella");

        detailList.add("Ingredients");
        detailList.add("Step 1");
        detailList.add("Step 2");
        detailList.add("Step 3");
        detailList.add("Step 4");
        detailList.add("Step 5");

        KRecipe testRecipe = new KRecipe("Nutella Pie", "6", "8", "8", detailList, ingredientList, thumbnails[0]);
        Log.d(TAG, "Image URL: " + thumbnails[0]);
        recipeList.add(testRecipe);

        testRecipe = new KRecipe("Brownies", "6", "10", "10", detailList, ingredientList, thumbnails[1]);
        recipeList.add(testRecipe);

        testRecipe = new KRecipe("Yellow Cake", "3", "10", "15", detailList, ingredientList, thumbnails[2]);
        recipeList.add(testRecipe);

        testRecipe = new KRecipe("Cheesecake", "10", "4", "6", detailList, ingredientList, thumbnails[3]);
        recipeList.add(testRecipe);

    }

}


