package com.example.android.bakingapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapplication.IngredientsFragment;
import com.example.android.bakingapplication.MasterRecipeDetailFragment;
import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.model.KRecipe;

public class DetailListActivity extends AppCompatActivity implements MasterRecipeDetailFragment.onRecipeDetailItemSelected {

    public KRecipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);

        recipe = getIntent().getParcelableExtra("selected_recipe");

        setTitle(recipe.getDessertName());
    }

    public KRecipe getRecipe() {
        return recipe;
    }

    @Override
    public void onRecipeDetailButtonClicked(int buttonPressed) {
        Class selectedDetailDestination = SelectedDetail.class;
        Context context = this;
        Intent intentToStartSelectedDetailActivity = new Intent(context, selectedDetailDestination);
        intentToStartSelectedDetailActivity.putExtra("selected_recipe", recipe);
        startActivity(intentToStartSelectedDetailActivity);
    }
}
