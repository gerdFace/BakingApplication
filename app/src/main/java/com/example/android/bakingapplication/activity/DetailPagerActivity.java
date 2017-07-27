package com.example.android.bakingapplication.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.bakingapplication.InstructionFragment;
import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.model.RecipeData;
import com.example.android.bakingapplication.model.Step;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

import static com.example.android.bakingapplication.activity.DetailListActivity.ID_OF_FOOD_SELECTED_KEY;
import static com.example.android.bakingapplication.activity.DetailListActivity.SHORT_DESCRIPTION_OF_STEP_SELECTED;
import static com.example.android.bakingapplication.activity.DetailListActivity.NAME_OF_FOOD_SELECTED_KEY;

public class DetailPagerActivity extends AppCompatActivity {

	private static final String TAG = DetailPagerActivity.class.getSimpleName();

	private List<Step> stepDetailList;
	private String nameOfFoodItem;
	private int foodItemID;

	@Inject
	Realm realm;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pager);

		((BakingApplication)getApplication()).getAppComponent().inject(this);

		final String shortDescriptionOfStepSelected = getIntent().getStringExtra(SHORT_DESCRIPTION_OF_STEP_SELECTED);

		nameOfFoodItem = getIntent().getStringExtra(NAME_OF_FOOD_SELECTED_KEY);

		foodItemID = getIntent().getIntExtra(ID_OF_FOOD_SELECTED_KEY, 0);

		setTitle(nameOfFoodItem);

		stepDetailList = realm.where(RecipeData.class)
				.equalTo("id", foodItemID)
				.findFirst().getSteps();
		
	    ViewPager viewPager = (ViewPager) findViewById(R.id.step_view_pager);
		
	    FragmentManager fragmentManager = getSupportFragmentManager();
	
	    viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
		    @Override
		    public Fragment getItem(int position) {
				    return InstructionFragment
						    .newInstance(nameOfFoodItem, shortDescriptionOfStepSelected);
//			    }
		    }
		
		    @Override
		    public int getCount() {
			    return stepDetailList.size();
		    }
	    });
	    
	    for (int i = 0; i < stepDetailList.size(); i ++) {
		    Log.d(TAG, "stepDescriptionList: " + stepDetailList.get(i) + "shortDescriptionOfStepSelected: " + shortDescriptionOfStepSelected);
		    if (stepDetailList.get(i).getShortDescription().equals(shortDescriptionOfStepSelected)) {
			    viewPager.setCurrentItem(i);
			    break;
		    }
	    }


    }
}
