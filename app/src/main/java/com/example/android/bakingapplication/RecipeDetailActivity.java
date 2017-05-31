package com.example.android.bakingapplication;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RecipeDetailActivity extends AppCompatActivity {

    public KRecipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipe = getIntent().getParcelableExtra("selected_recipe");


    }

    public KRecipe getRecipe() {
        return recipe;
    }
}
