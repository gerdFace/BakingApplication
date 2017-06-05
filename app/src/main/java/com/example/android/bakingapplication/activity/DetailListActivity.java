package com.example.android.bakingapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapplication.MasterRecipeDetailFragment;
import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.model.KRecipe;

public class DetailListActivity extends AppCompatActivity implements MasterRecipeDetailFragment.onRecipeDetailItemSelected {

    public static final String SELECTED_RECIPE_KEY = "selected_recipe";

    public KRecipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);

        recipe = getIntent().getParcelableExtra(SELECTED_RECIPE_KEY);

        setTitle(recipe.getDessertName());
    }

    public KRecipe getRecipe() {
        return recipe;
    }

    @Override
    public void onRecipeDetailButtonClicked(int buttonPressed) {
        Class selectedDetailDestination = SelectedDetailActivity.class;
        Context context = this;
        Intent intentToStartSelectedDetailActivity = new Intent(context, selectedDetailDestination);
        intentToStartSelectedDetailActivity.putExtra(SELECTED_RECIPE_KEY, recipe);
        startActivity(intentToStartSelectedDetailActivity);
    }
}
