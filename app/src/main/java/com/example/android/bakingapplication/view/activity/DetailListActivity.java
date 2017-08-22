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

import java.util.List;

import javax.inject.Inject;

public class DetailListActivity extends AppCompatActivity implements DetailListFragment.DetailItemCallbacks, DetailListActivityView {

    @Inject
    DetailListActivityPresenter detailListActivityPresenter;

    public static final String NAME_OF_FOOD_SELECTED = "name_of_recipe_selected";
    public static final String ID_OF_RECIPE_SELECTED = "id_of_recipe_selected";
    private static final String POSITION_OF_STEP_SELECTED = "position_of_step_selected";
	private static final String SAVED_RECIPE_NAME = "saved_recipe_name";
	private static final String SAVED_RECIPE_ID = "saved_recipe_id";

	private String recipeName;
    private int recipeId;
    private boolean twoPane;
    private List<Step> steps;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail);

        ((BakingApplication)getApplication()).getApplicationComponent().inject(this);

        if (savedInstanceState != null) {
		    recipeName = savedInstanceState.getString(SAVED_RECIPE_NAME);
            recipeId = savedInstanceState.getInt(SAVED_RECIPE_ID, 1);
        } else {
            recipeName = getIntent().getStringExtra(NAME_OF_FOOD_SELECTED);
            recipeId = getIntent().getIntExtra(ID_OF_RECIPE_SELECTED, 1);
        }

        Fragment detailListFragment = DetailListFragment.newInstance(recipeId);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_list_container, detailListFragment)
                    .commit();

	    twoPane = findViewById(R.id.ingredient_and_instruction_container) != null;

        setTitle(recipeName);
    }

    @Override
    public void onRecipeDetailButtonClicked(final int position) {

        if (!twoPane) {
	        Bundle bundle = new Bundle();
	        bundle.putInt(POSITION_OF_STEP_SELECTED, position);
	        bundle.putString(NAME_OF_FOOD_SELECTED, recipeName);
            bundle.putInt(ID_OF_RECIPE_SELECTED, recipeId);

            Intent intentToStartDetailPagerActivity = new Intent(this, DetailPagerActivity.class);
            intentToStartDetailPagerActivity.putExtras(bundle);
            startActivity(intentToStartDetailPagerActivity);
	        
		// TODO fix: orientation change on tablet causes crash
		// TODO update with constraintSet
        } else {
            Fragment instructionFragment = InstructionFragment.newInstance(steps.get(position));

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ingredient_and_instruction_container, instructionFragment)
                    .commit();
            }
        }

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(SAVED_RECIPE_NAME, recipeName);
        outState.putInt(SAVED_RECIPE_ID, recipeId);
	}

    @Override
    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    @Override
    public void showErrorMessage(String failureMessage) {
        Log.d("Error loading steps: ", failureMessage);
    }

    @Override
    public int getRecipeId() {
        return recipeId;
    }

    @Override
    protected void onResume() {
        super.onResume();
        detailListActivityPresenter.setView(this);
    }
}
