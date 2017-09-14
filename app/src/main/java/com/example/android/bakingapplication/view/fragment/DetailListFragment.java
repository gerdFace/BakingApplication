package com.example.android.bakingapplication.view.fragment;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.android.bakingapplication.BakingApplicationWidget;
import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.adapter.DetailListAdapter;
import com.example.android.bakingapplication.model.Step;
import com.example.android.bakingapplication.presentation.DetailListFragmentPresenter;
import com.example.android.bakingapplication.view.activity.BakingApplication;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailListFragment extends Fragment implements DetailListFragmentView{

    private static final String RECIPE_ID_KEY = "recipe_id";
    private static final String IS_INGREDIENT_FRAGMENT_ATTACHED = "is_ingredient_fragment_attached";

    private int recipeId;
    private boolean isIngredientFragmentAttached;
    private DetailItemCallbacks callbacks;
	private DetailListAdapter detailListAdapter;
    private ConstraintSet constraintSet = new ConstraintSet();

    @Inject
    DetailListFragmentPresenter detailListFragmentPresenter;

    @BindView(R.id.rv_detail_list)
    RecyclerView rvDetailList;

    @BindView(R.id.ingredient_card_container)
    CardView ingredientCardContainer;

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
	
	public static DetailListFragment newInstance(int recipeId) {
		Bundle args = new Bundle();
		args.putInt(RECIPE_ID_KEY, recipeId);

		DetailListFragment detailListFragment = new DetailListFragment();
		detailListFragment.setArguments(args);
		return detailListFragment;
	}

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_list_before, container, false);

        ButterKnife.bind(this, view);

        if (savedInstanceState == null) {
            recipeId = getArguments().getInt(RECIPE_ID_KEY, 0);
            isIngredientFragmentAttached = false;
        } else {
            recipeId = savedInstanceState.getInt(RECIPE_ID_KEY);
            isIngredientFragmentAttached = savedInstanceState.getBoolean(IS_INGREDIENT_FRAGMENT_ATTACHED);
        }

        updateViewForIngredientList();

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((BakingApplication)getActivity().getApplication()).getApplicationComponent().inject(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);

        rvDetailList.setLayoutManager(layoutManager);

        ingredientCardContainer.setOnClickListener(v -> {
            updateViewForIngredientList();
        });
    }

    @Override
    public void showSteps(List<Step> steps) {
        if (detailListAdapter == null) {
            detailListAdapter = new DetailListAdapter(callbacks);
            detailListAdapter.updateDetailListAdapter(steps);

            rvDetailList.setAdapter(detailListAdapter);
        } else {
            detailListAdapter.updateDetailListAdapter(steps);
        }
    }

    @Override
    public int getRecipeId() {
        return recipeId;
    }

    @Override
    public void updateWidgets() {
        Context context = getActivity();
        BakingApplicationWidget bakingApplicationWidget = new BakingApplicationWidget();
        bakingApplicationWidget.setLastAccessedRecipeId(recipeId);
        int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, BakingApplicationWidget.class));
        bakingApplicationWidget.onUpdate(context, AppWidgetManager.getInstance(context), ids);
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
        detailListFragmentPresenter.setView(this);
    }
	
    @Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
        outState.putInt(RECIPE_ID_KEY, recipeId);
        outState.putBoolean(IS_INGREDIENT_FRAGMENT_ATTACHED, !isIngredientFragmentAttached);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void updateConstraintLayout(int layoutId) {
        constraintSet.clone(getActivity().getApplicationContext(), layoutId);
        TransitionManager.beginDelayedTransition(constraintLayout);
        constraintSet.applyTo(constraintLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void updateViewForIngredientList() {
        if (isIngredientFragmentAttached) {
            Fragment ingredientsFragment = IngredientsFragment.newInstance(recipeId);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.ingredient_fragment_container, ingredientsFragment)
                    .addToBackStack(null)
                    .commit();

            updateConstraintLayout(R.layout.fragment_detail_list_after);

            isIngredientFragmentAttached = false;
        } else {
            updateConstraintLayout(R.layout.fragment_detail_list_before);
            isIngredientFragmentAttached= true;
        }
    }
}


