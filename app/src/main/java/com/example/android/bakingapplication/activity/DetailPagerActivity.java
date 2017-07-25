package com.example.android.bakingapplication.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.bakingapplication.IngredientsFragment;
import com.example.android.bakingapplication.InstructionFragment;
import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.model.FakeRecipeData;

import java.util.ArrayList;

import javax.inject.Inject;

import io.realm.Realm;

import static com.example.android.bakingapplication.activity.DetailListActivity.ID_OF_FOOD_SELECTED_KEY;
import static com.example.android.bakingapplication.activity.DetailListActivity.NAME_OF_DETAIL_BUTTON_SELECTED_KEY;
import static com.example.android.bakingapplication.activity.DetailListActivity.NAME_OF_FOOD_SELECTED_KEY;

public class DetailPagerActivity extends AppCompatActivity {

	private static final String TAG = DetailPagerActivity.class.getSimpleName();

	private ArrayList<String> stepDetailList;
	private String nameOfFoodItem;
	private int foodItemID;

	@Inject
	FakeRecipeData fakeRecipeData;

	@Inject
	Realm realm;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_detail);

		((BakingApplication)getApplication()).getAppComponent().inject(this);

		String nameOfButtonClicked = getIntent().getStringExtra(NAME_OF_DETAIL_BUTTON_SELECTED_KEY);

		nameOfFoodItem = getIntent().getStringExtra(NAME_OF_FOOD_SELECTED_KEY);

		foodItemID = getIntent().getIntExtra(ID_OF_FOOD_SELECTED_KEY, 0);

		setTitle(nameOfFoodItem);
		
//		stepDetailList = fakeRecipeData.getKRecipe(nameOfFoodItem).getDetailList();

	    ViewPager viewPager = (ViewPager) findViewById(R.id.step_view_pager);
		
	    FragmentManager fragmentManager = getSupportFragmentManager();
	
	    viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
		    @Override
		    public Fragment getItem(int position) {
			    if (position == 0) {
				    return IngredientsFragment
						    .newInstance(nameOfFoodItem);
			    } else {
				    return InstructionFragment
						    .newInstance(nameOfFoodItem);
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
