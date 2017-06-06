package com.example.android.bakingapplication.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapplication.IngredientsFragment;
import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.model.KRecipe;

public class SelectedDetailActivity extends AppCompatActivity {

    public KRecipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_detail);

        recipe = getIntent().getParcelableExtra("selected_recipe");

        setTitle(recipe.getDessertName());

        IngredientsFragment ingredientsFragment = new IngredientsFragment();

        ingredientsFragment.setIngredientList(recipe.getIngredientList());

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.ingredients_container, ingredientsFragment)
                .commit();
    }
}
