package com.example.android.bakingapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapplication.DetailListFragment;
import com.example.android.bakingapplication.IngredientsFragment;
import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.model.KRecipe;

public class DetailListActivity extends AppCompatActivity implements DetailListFragment.onRecipeDetailItemSelected {

    public static final String SELECTED_RECIPE_KEY = "selected_recipe";

    public KRecipe recipe;

    public boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail);

        recipe = getIntent().getParcelableExtra(SELECTED_RECIPE_KEY);

        setTitle(recipe.getDessertName());

        if (findViewById(R.id.detail_list_container) != null) {
            Fragment detailListFragment = new DetailListFragment();

            FragmentManager detailFragmentManager = getSupportFragmentManager();
            detailFragmentManager.beginTransaction()
                    .add(R.id.detail_list_container, detailListFragment)
                    .commit();

            twoPane = true;
        } else {
            twoPane = false;
        }
    }

    public KRecipe getRecipe() {
        return recipe;
    }

    @Override
    public void onRecipeDetailButtonClicked(int buttonPressed) {

        if (!twoPane) {

            Class selectedDetailDestination = SelectedDetailActivity.class;

            Context context = this;

            Intent intentToStartSelectedDetailActivity = new Intent(context, selectedDetailDestination);
            intentToStartSelectedDetailActivity.putExtra(SELECTED_RECIPE_KEY, recipe);
            startActivity(intentToStartSelectedDetailActivity);
        } else {
            IngredientsFragment ingredientsFragment = new IngredientsFragment();

            ingredientsFragment.setIngredientList(recipe.getIngredientList());

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.ingredients_container, ingredientsFragment)
                    .commit();
        }
    }
}
