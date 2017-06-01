package com.example.android.bakingapplication.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapplication.IngredientsFragment;
import com.example.android.bakingapplication.MasterRecipeDetailFragment;
import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.model.KRecipe;

public class RecipeDetailActivity extends AppCompatActivity implements MasterRecipeDetailFragment.onRecipeDetailItemSelected {

    public KRecipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipe = getIntent().getParcelableExtra("selected_recipe");

        setTitle(recipe.getDessertName());
    }

    public KRecipe getRecipe() {
        return recipe;
    }

    @Override
    public void onRecipeDetailButtonClicked(int buttonPressed) {
        IngredientsFragment ingredientsFragment = new IngredientsFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.ingredients_container, ingredientsFragment)
                .commit();
    }
}
