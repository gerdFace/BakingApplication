package com.example.android.bakingapplication.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.adapter.IngredientsAdapter;
import com.example.android.bakingapplication.model.Ingredient;
import com.example.android.bakingapplication.repository.RecipeRepository;
import com.example.android.bakingapplication.repository.RecipeRepositoryImpl;
import com.example.android.bakingapplication.view.activity.BakingApplication;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsFragment extends Fragment {

	private static final String TAG = IngredientsFragment.class.getSimpleName();
	private static final String ARG_RECIPE_ID_KEY = "recipe_id_key";
	private int recipeId;
	private IngredientsAdapter ingredientAdapter;
	private List<Ingredient> ingredientList;

	@Inject
	RecipeRepositoryImpl recipeRepository;

	@BindView(R.id.rv_ingredient_list)
    RecyclerView rvIngredientList;

	public IngredientsFragment() {
    }
    
    public static IngredientsFragment newInstance(int recipeId) {
        Bundle args = new Bundle();
        args.putInt(ARG_RECIPE_ID_KEY, recipeId);

        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        ingredientsFragment.setArguments(args);
        return ingredientsFragment;
    }
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {
			recipeId = getArguments().getInt(ARG_RECIPE_ID_KEY);
		} else {
			recipeId = savedInstanceState.getInt(ARG_RECIPE_ID_KEY);
		}
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ingredient_list, container, false);

		((BakingApplication)getActivity().getApplication()).getRecipeRepositoryComponent().inject(this);

		ButterKnife.bind(this, view);

		recipeRepository.getIngredients(recipeId, new RecipeRepository.GetIngredientsCallback() {
			@Override
			public void onIngredientsLoaded(List<Ingredient> ingredients) {
				ingredientList = ingredients;
			}

			@Override
			public void onDataNotAvailable(String failureMessage) {
				Log.d(TAG, failureMessage);
			}
		});

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayout.VERTICAL, false);
        
		rvIngredientList.setLayoutManager(layoutManager);

		updateUI();
		
        return view;
    }
    
    private void updateUI() {

	    if (ingredientAdapter == null) {
		    ingredientAdapter = new IngredientsAdapter(ingredientList);
		    rvIngredientList.setAdapter(ingredientAdapter);
	    } else {
		    ingredientAdapter.notifyDataSetChanged();
	    }
    }
	
	@Override
	public void onResume() {
		super.onResume();
		updateUI();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(ARG_RECIPE_ID_KEY, recipeId);
	}
}
