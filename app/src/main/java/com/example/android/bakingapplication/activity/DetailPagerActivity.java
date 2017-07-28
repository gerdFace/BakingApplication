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

import static com.example.android.bakingapplication.activity.DetailListActivity.ID_OF_FOOD_SELECTED;
import static com.example.android.bakingapplication.activity.DetailListActivity.NAME_OF_FOOD_SELECTED;

public class DetailPagerActivity extends AppCompatActivity {

	private static final String TAG = DetailPagerActivity.class.getSimpleName();
	private static final String POSITION_OF_STEP_SELECTED = "position_of_step_selected";

	private List<Step> stepDetailList;
	private String nameOfFoodItem;
	private int positionOfStepSelected;
	private int foodItemID;

	@Inject
	Realm realm;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pager);

		((BakingApplication)getApplication()).getAppComponent().inject(this);

		positionOfStepSelected = getIntent().getIntExtra(POSITION_OF_STEP_SELECTED, 0);

		nameOfFoodItem = getIntent().getStringExtra(NAME_OF_FOOD_SELECTED);

		foodItemID = getIntent().getIntExtra(ID_OF_FOOD_SELECTED, 0);

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
						    .newInstance(stepDetailList.get(position));
//			    }
		    }
		
		    @Override
		    public int getCount() {
			    return stepDetailList.size();
		    }
	    });
	    
	    for (int i = 0; i < stepDetailList.size(); i ++) {
			Log.d(TAG, "stepDescriptionList: " + stepDetailList.get(i) + "Position of step selected: " + positionOfStepSelected);
			if (i == positionOfStepSelected) {
				viewPager.setCurrentItem(i);
				break;
			}
		}
    }
}
