package com.example.android.bakingapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.bakingapplication.DetailListFragment;
import com.example.android.bakingapplication.IngredientsFragment;
import com.example.android.bakingapplication.InstructionFragment;
import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.model.KRecipe;

public class DetailListActivity extends AppCompatActivity implements DetailListFragment.DetailItemCallbacks {

    public static final String SELECTED_RECIPE_KEY = "selected_recipe";
    public static final String POSITION_OF_DETAIL_BUTTON_CLICKED = "position_of_button";
    private static final String TAG = DetailListActivity.class.getSimpleName();

    private KRecipe recipe;

    private boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipe = getIntent().getParcelableExtra(SELECTED_RECIPE_KEY);
        setContentView(R.layout.activity_master_detail);


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
    public void onRecipeDetailButtonClicked(int position) {

        Log.d(TAG, "position of button clicked: " + position);

        if (!twoPane) {

            Class selectedDetailDestination = SelectedDetailActivity.class;

            Context context = this;

            Bundle bundle = new Bundle();
            bundle.putInt(POSITION_OF_DETAIL_BUTTON_CLICKED, position);
            bundle.putParcelable(SELECTED_RECIPE_KEY, recipe);

            Intent intentToStartSelectedDetailActivity = new Intent(context, selectedDetailDestination);
            intentToStartSelectedDetailActivity.putExtras(bundle);
            startActivity(intentToStartSelectedDetailActivity);
        } else {

            if (position > 0) {

                InstructionFragment instructionFragment = new InstructionFragment();

                instructionFragment.setStepDescriptionList(recipe.getStepDescriptionList());

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.ingredient_and_instruction_container, instructionFragment)
                        .commit();
            } else {
                IngredientsFragment ingredientsFragment = new IngredientsFragment();

                ingredientsFragment.setIngredientList(recipe.getIngredientList());

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.ingredient_and_instruction_container, ingredientsFragment)
                        .commit();
            }
        }
    }
}
