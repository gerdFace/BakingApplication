package com.example.android.bakingapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.bakingapplication.DetailListFragment;
import com.example.android.bakingapplication.IngredientsFragment;
import com.example.android.bakingapplication.InstructionFragment;
import com.example.android.bakingapplication.R;

import butterknife.ButterKnife;

public class DetailListActivity extends AppCompatActivity implements DetailListFragment.DetailItemCallbacks {

    public static final String NAME_OF_FOOD_SELECTED_KEY = "name_of_food_selected";
    public static final String ID_OF_FOOD_SELECTED_KEY = "id_of_food_selected";
    public static final String SHORT_DESCRIPTION_OF_STEP_SELECTED = "short_description_of_step_selected";
	public static final String SAVED_RECIPE_NAME = "saved_recipe_name";
	public static final String SAVED_RECIPE_ID = "saved_recipe_id";

	private static final String TAG = DetailListActivity.class.getSimpleName();
	
	private String nameOfFoodItem;
    private int foodID;
    private boolean twoPane;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail);

        ButterKnife.bind(this);

        ((BakingApplication)getApplication()).getAppComponent().inject(this);

        if (savedInstanceState != null) {
		    nameOfFoodItem = savedInstanceState.getString(SAVED_RECIPE_NAME);
            foodID = savedInstanceState.getInt(SAVED_RECIPE_ID, 0);
        } else {
            nameOfFoodItem = getIntent().getStringExtra(NAME_OF_FOOD_SELECTED_KEY);
            foodID = getIntent().getIntExtra(ID_OF_FOOD_SELECTED_KEY, 0);
        }

        Log.d(TAG, "onCreate: " + nameOfFoodItem + "ID: " + foodID);

        Fragment detailListFragment = DetailListFragment.newInstance(nameOfFoodItem, foodID);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_list_container, detailListFragment)
                    .commit();

	    twoPane = findViewById(R.id.ingredient_and_instruction_container) != null;

        setTitle(nameOfFoodItem);
    }

    @Override
    public void onRecipeDetailButtonClicked(String nameOfStep) {

        Log.d(TAG, "name of button clicked: " + nameOfStep);

        if (!twoPane) {
	        Bundle bundle = new Bundle();
	        bundle.putString(SHORT_DESCRIPTION_OF_STEP_SELECTED, nameOfStep);
	        bundle.putString(NAME_OF_FOOD_SELECTED_KEY, nameOfFoodItem);
            bundle.putInt(ID_OF_FOOD_SELECTED_KEY, foodID);

            Intent intentToStartDetailPagerActivity = new Intent(this, DetailPagerActivity.class);
            intentToStartDetailPagerActivity.putExtras(bundle);
            startActivity(intentToStartDetailPagerActivity);
	        
		// TODO fix: orientation change on tablet causes crash
		// TODO fix: update with constraintSet
        } else {
            if (nameOfStep.equals("Ingredients")) {

                Fragment instructionFragment = InstructionFragment.newInstance(nameOfFoodItem, nameOfStep);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.ingredient_and_instruction_container, instructionFragment)
                        .commit();
            } else {
                Fragment ingredientsFragment = IngredientsFragment.newInstance(nameOfFoodItem);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.ingredient_and_instruction_container, ingredientsFragment)
                        .commit();
            }
        }
    }
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(SAVED_RECIPE_NAME, nameOfFoodItem);
        outState.putInt(SAVED_RECIPE_ID, foodID);
	}
}
