package com.example.android.bakingapplication.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.bakingapplication.IngredientsFragment;
import com.example.android.bakingapplication.InstructionFragment;
import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.model.KRecipe;
import java.util.ArrayList;
import static com.example.android.bakingapplication.activity.DetailListActivity.NAME_OF_DETAIL_BUTTON_CLICKED;
import static com.example.android.bakingapplication.activity.DetailListActivity.SELECTED_RECIPE_KEY;

public class DetailPagerActivity extends AppCompatActivity {

	private static final String TAG = DetailPagerActivity.class.getSimpleName();
	
	private ArrayList<String> stepDetailList;
	private ArrayList<String> ingredientList;
	private ArrayList<String> stepDescriptionList;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_detail);
	
	    KRecipe recipe = getIntent().getParcelableExtra(SELECTED_RECIPE_KEY);
		
		String nameOfButtonClicked = getIntent().getStringExtra(NAME_OF_DETAIL_BUTTON_CLICKED);
	    
	    stepDetailList = recipe.getDetailList();
	    
	    stepDescriptionList = recipe.getStepDescriptionList();
	    
	    ingredientList = recipe.getIngredientList();

        setTitle(recipe.getDessertName());
	
	    ViewPager viewPager = (ViewPager) findViewById(R.id.step_view_pager);
	    
	    FragmentManager fragmentManager = getSupportFragmentManager();
	
	    viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
		    @Override
		    public Fragment getItem(int position) {
			    if (position == 0) {
				    return IngredientsFragment
						    .newInstance(ingredientList);
			    } else {
				    return InstructionFragment
						    .newInstance(stepDescriptionList);
			    }
		    }
		
		    @Override
		    public int getCount() {
			    return stepDetailList.size();
		    }
	    });
	    
	    for (int i = 0; i < stepDetailList.size(); i ++) {
		    Log.d(TAG, "stepDescriptionList: " + stepDetailList.get(i) + "nameOfButtonClicked: " + nameOfButtonClicked);
		    if (stepDetailList.get(i).equals(nameOfButtonClicked)) {
			    viewPager.setCurrentItem(i);
			    break;
		    }
	    }
    }
}
