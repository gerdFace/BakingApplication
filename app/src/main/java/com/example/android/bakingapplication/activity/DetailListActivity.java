package com.example.android.bakingapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.example.android.bakingapplication.DetailListFragment;
import com.example.android.bakingapplication.IngredientsFragment;
import com.example.android.bakingapplication.InstructionFragment;
import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.model.FakeRecipeData;

public class DetailListActivity extends AppCompatActivity implements DetailListFragment.DetailItemCallbacks {

    public static final String NAME_OF_FOOD_ITEM_SELECTED_KEY = "name_of_food_item_selected";
    public static final String NAME_OF_DETAIL_BUTTON_SELECTED_KEY = "name_of_button";
	public static final String SAVED_RECIPE = "saved_recipe";
    
	private static final String TAG = DetailListActivity.class.getSimpleName();
	
	private String nameOfFoodItem;
    private boolean twoPane;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail);
	
	    if (savedInstanceState != null) {
		    nameOfFoodItem = savedInstanceState.getString(SAVED_RECIPE);
	    } else {
	        nameOfFoodItem = getIntent().getStringExtra(NAME_OF_FOOD_ITEM_SELECTED_KEY);
		    
		    Fragment detailListFragment = DetailListFragment.newInstance(nameOfFoodItem);
		
		    getSupportFragmentManager().beginTransaction()
		                               .add(R.id.detail_list_container, detailListFragment)
		                               .commit();
	    }
		
	    twoPane = findViewById(R.id.ingredient_and_instruction_container) != null;
		
		if (getSupportActionBar() == null) {
			setTitle(nameOfFoodItem);
		}
    }

    @Override
    public void onRecipeDetailButtonClicked(int position) {

        Log.d(TAG, "position of button clicked: " + position);

        if (!twoPane) {

            Class selectedDetailDestination = DetailPagerActivity.class;

            Context context = this;

            String nameOfButtonSelected = FakeRecipeData.get().getKRecipe(nameOfFoodItem).getDetailList().get(position);
	
	        Bundle bundle = new Bundle();
	        bundle.putString(NAME_OF_DETAIL_BUTTON_SELECTED_KEY, nameOfButtonSelected);
	        bundle.putString(NAME_OF_FOOD_ITEM_SELECTED_KEY, nameOfFoodItem);

            Intent intentToStartSelectedDetailActivity = new Intent(context, selectedDetailDestination);
            intentToStartSelectedDetailActivity.putExtras(bundle);
            startActivity(intentToStartSelectedDetailActivity);
	        
		// TODO fix: orientation change on tablet causes crash
        } else {
            if (position > 0) {
	            
                Fragment instructionFragment = InstructionFragment.newInstance(nameOfFoodItem);

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
		outState.putString(SAVED_RECIPE, nameOfFoodItem);
	}
}
