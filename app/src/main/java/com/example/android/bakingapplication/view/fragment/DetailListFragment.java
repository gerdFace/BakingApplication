package com.example.android.bakingapplication.view.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.adapter.DetailListAdapter;
import com.example.android.bakingapplication.model.RecipeData;
import com.example.android.bakingapplication.model.Step;
import com.example.android.bakingapplication.repository.RecipeRepository;
import com.example.android.bakingapplication.repository.RecipeRepositoryImpl;
import com.example.android.bakingapplication.view.activity.BakingApplication;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailListFragment extends Fragment {

    private static final String ID_OF_FOOD_ITEM_KEY = "id_of_food_item_key";

    private int recipeId;
    private boolean isIngredientListDisplayed = false;
    private DetailItemCallbacks callbacks;
	private DetailListAdapter detailListAdapter;
    private List<Step> detailList;
    private ConstraintSet constraintSet = new ConstraintSet();

    @Inject
    RecipeRepositoryImpl recipeRepository;

    @BindView(R.id.rv_detail_list)
    RecyclerView rvDetailList;

    @BindView(R.id.ingredient_drop_toggle)
    TextView ingredientDropToggle;

    @BindView(R.id.fragment_detail_list_constraint_container)
    ConstraintLayout constraintLayout;

    @BindView(R.id.ingredient_fragment_container)
    FrameLayout ingredientFrameLayout;

    public DetailListFragment() {
    }

    // Interface that enables fragment to communicate with host activity
    public interface DetailItemCallbacks {
        void onRecipeDetailButtonClicked(int position);
    }
	
	
	public static DetailListFragment newInstance(int foodID) {
		Bundle args = new Bundle();
		args.putInt(ID_OF_FOOD_ITEM_KEY, foodID);

		DetailListFragment detailListFragment = new DetailListFragment();
		detailListFragment.setArguments(args);
		return detailListFragment;
	}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            recipeId = getArguments().getInt(ID_OF_FOOD_ITEM_KEY, 0);
        } else {
            recipeId = savedInstanceState.getInt(ID_OF_FOOD_ITEM_KEY);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_list, container, false);

        ((BakingApplication)getActivity().getApplication()).getRecipeRepositoryComponent().inject(this);

        ButterKnife.bind(this, view);

        constraintSet.clone(getActivity().getApplicationContext(), R.layout.fragment_detail_list_after);

        ingredientDropToggle.setOnClickListener(v -> {
            if (!isIngredientListDisplayed) {
                TransitionManager.beginDelayedTransition(constraintLayout);
                constraintSet.setVisibility(R.id.ingredient_fragment_container, View.VISIBLE);
                constraintSet.applyTo(constraintLayout);

                Fragment ingredientsFragment = IngredientsFragment.newInstance(recipeId);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.ingredient_fragment_container, ingredientsFragment)
                        .commit();

                isIngredientListDisplayed = true;

            } else {
                // TODO check does fragment instance remain after view is gone?
                TransitionManager.beginDelayedTransition(constraintLayout);
                constraintSet.setVisibility(R.id.ingredient_fragment_container, View.GONE);
                constraintSet.applyTo(constraintLayout);

                isIngredientListDisplayed = false;

            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);

        rvDetailList.setLayoutManager(layoutManager);

        // TODO save Instance, add else statement -- does statePager auto save instance?
        recipeRepository.getRecipe(recipeId, new RecipeRepository.GetRecipeCallback() {
            @Override
            public void onRecipeLoaded(RecipeData recipe) {
                detailList = recipe.getSteps();
            }

            @Override
            public void onDataNotAvailable(String failureMessage) {

            }
        });

        updateUI();

        return view;
    }

	private void updateUI() {
		if (detailListAdapter == null) {
			detailListAdapter = new DetailListAdapter(detailList, callbacks);
			
			rvDetailList.setAdapter(detailListAdapter);
		} else {
			detailListAdapter.notifyDataSetChanged();
		}
	}

    // Ensure that host activity implements the callback interface
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callbacks = (DetailItemCallbacks) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                                                 + " must implement OnImageClickListener");
        }
    }

    // Reset callback when fragment detaches from host activity
    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }
	
	@Override
	public void onResume() {
		super.onResume();
		updateUI();
	}
	
		@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
        outState.putInt(ID_OF_FOOD_ITEM_KEY, recipeId);
	}
}


