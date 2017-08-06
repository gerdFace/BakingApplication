package com.example.android.bakingapplication.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.model.Step;
import com.example.android.bakingapplication.presentation.DetailListActivityPresenter;
import com.example.android.bakingapplication.view.fragment.DetailListFragment;
import com.example.android.bakingapplication.view.fragment.InstructionFragment;

import javax.inject.Inject;

public class DetailListActivity extends AppCompatActivity implements DetailListFragment.DetailItemCallbacks, DetailListActivityView {

    @Inject
    DetailListActivityPresenter detailListActivityPresenter;

    public static final String NAME_OF_FOOD_SELECTED = "name_of_food_selected";
    public static final String ID_OF_RECIPE_SELECTED = "id_of_food_selected";
    public static final String POSITION_OF_STEP_SELECTED = "position_of_step_selected";
	public static final String SAVED_RECIPE_NAME = "saved_recipe_name";
	public static final String SAVED_RECIPE_ID = "saved_recipe_id";

	private String nameOfFoodItem;
    private int recipeId;
    private int positionOfStepSelected;
    private boolean twoPane;
    private Step step;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail);

        ((BakingApplication)getApplication()).getApplicationComponent().inject(this);

        if (savedInstanceState != null) {
		    nameOfFoodItem = savedInstanceState.getString(SAVED_RECIPE_NAME);
            recipeId = savedInstanceState.getInt(SAVED_RECIPE_ID, 0);
        } else {
            nameOfFoodItem = getIntent().getStringExtra(NAME_OF_FOOD_SELECTED);
            recipeId = getIntent().getIntExtra(ID_OF_RECIPE_SELECTED, 0);
        }

        Fragment detailListFragment = DetailListFragment.newInstance(recipeId);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_list_container, detailListFragment)
                    .commit();

	    twoPane = findViewById(R.id.ingredient_and_instruction_container) != null;

        setTitle(nameOfFoodItem);
    }

    @Override
    public void onRecipeDetailButtonClicked(final int position) {

        positionOfStepSelected = position;

        if (!twoPane) {
	        Bundle bundle = new Bundle();
	        bundle.putInt(POSITION_OF_STEP_SELECTED, position);
	        bundle.putString(NAME_OF_FOOD_SELECTED, nameOfFoodItem);
            bundle.putInt(ID_OF_RECIPE_SELECTED, recipeId);

            Intent intentToStartDetailPagerActivity = new Intent(this, DetailPagerActivity.class);
            intentToStartDetailPagerActivity.putExtras(bundle);
            startActivity(intentToStartDetailPagerActivity);
	        
		// TODO fix: orientation change on tablet causes crash
		// TODO update with constraintSet
        } else {
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
        outState.putInt(SAVED_RECIPE_ID, recipeId);
	}

    @Override
    public void setStep(Step step) {
        this.step = step;
    }

    @Override
    public void showErrorMessage(String failureMessage) {
        Log.d("Error loading step: ", failureMessage);
    }

    @Override
    public int getRecipeId() {
        return recipeId;
    }

    @Override
    public int getStepSelectedPosition() {
        return positionOfStepSelected;
    }

    @Override
    protected void onResume() {
        super.onResume();
        detailListActivityPresenter.setView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        detailListActivityPresenter.setView(this);
    }
}
