package com.example.android.bakingapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.bakingapplication.DetailListFragment;
import com.example.android.bakingapplication.InstructionFragment;
import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.model.RecipeData;
import com.example.android.bakingapplication.model.Step;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.realm.Realm;

public class DetailListActivity extends AppCompatActivity implements DetailListFragment.DetailItemCallbacks {

    @Inject
    Realm realm;

    public static final String NAME_OF_FOOD_SELECTED = "name_of_food_selected";
    public static final String ID_OF_FOOD_SELECTED = "id_of_food_selected";
    public static final String POSITION_OF_STEP_SELECTED = "position_of_step_selected";
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
            nameOfFoodItem = getIntent().getStringExtra(NAME_OF_FOOD_SELECTED);
            foodID = getIntent().getIntExtra(ID_OF_FOOD_SELECTED, 0);
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
    public void onRecipeDetailButtonClicked(int position) {

        Log.d(TAG, "name of button clicked: " + position);

        if (!twoPane) {
	        Bundle bundle = new Bundle();
	        bundle.putInt(POSITION_OF_STEP_SELECTED, position);
	        bundle.putString(NAME_OF_FOOD_SELECTED, nameOfFoodItem);
            bundle.putInt(ID_OF_FOOD_SELECTED, foodID);

            Intent intentToStartDetailPagerActivity = new Intent(this, DetailPagerActivity.class);
            intentToStartDetailPagerActivity.putExtras(bundle);
            startActivity(intentToStartDetailPagerActivity);
	        
		// TODO fix: orientation change on tablet causes crash
		// TODO update with constraintSet
        } else {
            Step step = realm.where(RecipeData.class)
                    .equalTo("id", foodID)
                    .findFirst()
                    .getSteps()
                    .get(position);

                Fragment instructionFragment = InstructionFragment.newInstance(step);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.ingredient_and_instruction_container, instructionFragment)
                        .commit();
            }
        }

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(SAVED_RECIPE_NAME, nameOfFoodItem);
        outState.putInt(SAVED_RECIPE_ID, foodID);
	}
}
