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
    private static final String SCROLL_POSITION = "scroll_position";

    private int recipeId;
    private int scrollPosition = 0;
    private boolean isIngredientListDisplayed = false;
    private RecyclerView.LayoutManager layoutManager;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            recipeId = getArguments().getInt(RECIPE_ID_KEY, 0);
        } else {
            recipeId = savedInstanceState.getInt(RECIPE_ID_KEY);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_list_before, container, false);

        ((BakingApplication)getActivity().getApplication()).getApplicationComponent().inject(this);

        ButterKnife.bind(this, view);

        layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);

        if (savedInstanceState != null) {
            scrollPosition = savedInstanceState.getInt(SCROLL_POSITION);
        }

        rvDetailList.setLayoutManager(layoutManager);

        updateViewWithConstraintSet();

        return view;
    }

    @Override
    public void showSteps(List<Step> steps) {
        if (detailListAdapter == null) {
            detailListAdapter = new DetailListAdapter(callbacks);
            detailListAdapter.updateDetailListAdapter(steps);

            rvDetailList.setAdapter(detailListAdapter);
        } else {
            detailListAdapter.updateDetailListAdapter(steps);
            rvDetailList.scrollToPosition(scrollPosition);
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
        outState.putInt(SCROLL_POSITION, layoutManager.getChildCount());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void updateViewWithConstraintSet() {
        constraintSet.clone(getActivity().getApplicationContext(), R.layout.fragment_detail_list_after);

        ingredientCardContainer.setOnClickListener(v -> {
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
                TransitionManager.beginDelayedTransition(constraintLayout);
                constraintSet.setVisibility(R.id.ingredient_fragment_container, View.GONE);
                constraintSet.applyTo(constraintLayout);

                isIngredientListDisplayed = false;
            }
        });
    }
}


