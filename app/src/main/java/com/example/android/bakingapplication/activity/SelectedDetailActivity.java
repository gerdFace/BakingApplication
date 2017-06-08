package com.example.android.bakingapplication.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapplication.IngredientsFragment;
import com.example.android.bakingapplication.InstructionFragment;
import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.model.KRecipe;

import static com.example.android.bakingapplication.activity.DetailListActivity.POSITION_OF_DETAIL_BUTTON_CLICKED;
import static com.example.android.bakingapplication.activity.DetailListActivity.SELECTED_RECIPE_KEY;

public class SelectedDetailActivity extends AppCompatActivity {

    public KRecipe recipe;
    public int positionOfButtonClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_detail);

        recipe = getIntent().getParcelableExtra(SELECTED_RECIPE_KEY);

        positionOfButtonClicked = getIntent().getIntExtra(POSITION_OF_DETAIL_BUTTON_CLICKED, 0);

        setTitle(recipe.getDessertName());

        if (positionOfButtonClicked > 0) {
            InstructionFragment instructionFragment = new InstructionFragment();

            instructionFragment.setStepDescriptionList(recipe.getStepDescriptionList());

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.ingredient_and_instruction_container, instructionFragment)
                    .commit();
        } else {
            IngredientsFragment ingredientsFragment = new IngredientsFragment();

            ingredientsFragment.setIngredientList(recipe.getIngredientList());

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.ingredient_and_instruction_container, ingredientsFragment)
                    .commit();
        }
    }
}
