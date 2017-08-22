package com.example.android.bakingapplication.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.model.Step;
import com.example.android.bakingapplication.presentation.DetailPagerActivityPresenter;
import com.example.android.bakingapplication.view.ExoPlayerVideoHandler;
import com.example.android.bakingapplication.view.fragment.InstructionFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.bakingapplication.view.activity.DetailListActivity.ID_OF_RECIPE_SELECTED;
import static com.example.android.bakingapplication.view.activity.DetailListActivity.NAME_OF_FOOD_SELECTED;

public class DetailPagerActivity extends AppCompatActivity implements DetailPagerActivityView{

	private static final String POSITION_OF_STEP_SELECTED = "position_of_step_selected";

	private List<Step> stepDetailList;
	private String nameOfFoodItem;
	private int positionOfStepSelected;
	private int recipeId;

	@Inject
	DetailPagerActivityPresenter detailPagerActivityPresenter;

	@BindView(R.id.step_view_pager)
	ViewPager stepViewPager;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pager);

		((BakingApplication)getApplication()).getApplicationComponent().inject(this);

		ButterKnife.bind(this);

		if (savedInstanceState == null) {
			positionOfStepSelected = getIntent().getIntExtra(POSITION_OF_STEP_SELECTED, 0);

			nameOfFoodItem = getIntent().getStringExtra(DetailListActivity.NAME_OF_FOOD_SELECTED);

			recipeId = getIntent().getIntExtra(ID_OF_RECIPE_SELECTED, 0);
		} else {
			positionOfStepSelected = savedInstanceState.getInt(POSITION_OF_STEP_SELECTED, 0);

			nameOfFoodItem = savedInstanceState.getString(NAME_OF_FOOD_SELECTED);

			recipeId = savedInstanceState.getInt(ID_OF_RECIPE_SELECTED, 0);
		}

		setTitle(nameOfFoodItem);
    }

    private void setViewPager() {
		FragmentManager fragmentManager = getSupportFragmentManager();

		stepViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		stepViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
			@Override
			public Fragment getItem(int position) {
				return InstructionFragment
						.newInstance(stepDetailList.get(position));
			}

			@Override
			public int getCount() {
				return stepDetailList.size();
			}
		});

		for (int i = 0; i < stepDetailList.size(); i ++) {
			if (i == positionOfStepSelected) {
				stepViewPager.setCurrentItem(i);
				break;
			}
		}
	}

	private void setPresenterView() {
		detailPagerActivityPresenter.setView(this);
	}

	@Override
	public void setSteps(List<Step> steps) {
		stepDetailList = steps;
		setViewPager();
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
	protected void onStart() {
		super.onStart();
		setPresenterView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setPresenterView();

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(ID_OF_RECIPE_SELECTED, recipeId);
		outState.putInt(POSITION_OF_STEP_SELECTED, positionOfStepSelected);
		outState.putString(NAME_OF_FOOD_SELECTED, nameOfFoodItem);
	}
}
