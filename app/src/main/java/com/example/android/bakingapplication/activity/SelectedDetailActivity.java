package com.example.android.bakingapplication.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapplication.IngredientsFragment;
import com.example.android.bakingapplication.InstructionFragment;
import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.model.KRecipe;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.bakingapplication.activity.DetailListActivity.POSITION_OF_DETAIL_BUTTON_CLICKED;
import static com.example.android.bakingapplication.activity.DetailListActivity.SELECTED_RECIPE_KEY;

public class SelectedDetailActivity extends AppCompatActivity {

	static final int NUM_ITEMS = 10;
	
    private ViewPager viewPager;
	private ArrayList<String> stepDetailList;
	private ArrayList<String> ingredientList;
    public KRecipe recipe;
    public int positionOfButtonClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_detail);

        recipe = getIntent().getParcelableExtra(SELECTED_RECIPE_KEY);

        positionOfButtonClicked = getIntent().getIntExtra(POSITION_OF_DETAIL_BUTTON_CLICKED, 0);
	    
	    stepDetailList = recipe.getDetailList();
	    
	    ingredientList = recipe.getIngredientList();

        setTitle(recipe.getDessertName());
	    
	    viewPager = (ViewPager) findViewById(R.id.step_view_pager);
	    
	    final FragmentManager fragmentManager = getSupportFragmentManager();
	
	    viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
		    @Override
		    public Fragment getItem(int position) {
			    if (position > 0) {
				    return InstructionFragment
						    .newInstance(stepDetailList);
//
//				    fragmentManager.beginTransaction()
//				                   .add(R.id.ingredient_and_instruction_container, instructionFragment)
//				                   .commit();
				
//				    return instructionFragment;
				
			    } else {
				    return IngredientsFragment
						    .newInstance(ingredientList);
				
//				    fragmentManager.beginTransaction()
//				                   .add(R.id.ingredient_and_instruction_container, ingredientsFragment)
//				                   .commit();
				
//				    return ingredientsFragment;
			    }
			
		    }
		
		    @Override
		    public int getCount() {
			    return stepDetailList.size();
		    }
	    });
    }
}
