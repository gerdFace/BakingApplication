package com.example.android.bakingapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.android.bakingapplication.adapter.IngredientsAdapter;
import com.example.android.bakingapplication.model.FakeRecipeData;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsFragment extends Fragment {
	
	private static final String ARG_FOOD_ITEM_KEY= "food_item_key";
	
	private String nameOfFoodItem;
	private IngredientsAdapter ingredientsAdapter;
	
	@BindView(R.id.rv_ingredient_list)
    RecyclerView rvIngredientList;
	
	public IngredientsFragment() {
    }
    
    public static IngredientsFragment newInstance(String nameOfFoodItem) {
        Bundle args = new Bundle();
        args.putString(ARG_FOOD_ITEM_KEY, nameOfFoodItem);
        
        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        ingredientsFragment.setArguments(args);
        return ingredientsFragment;
    }
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		nameOfFoodItem = getArguments().getString(ARG_FOOD_ITEM_KEY);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ingredient_list, container, false);

        ButterKnife.bind(this, view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayout.VERTICAL, false);
        
		rvIngredientList.setLayoutManager(layoutManager);

		updateUI();
		
        return view;
    }
    
    private void updateUI() {
	    ArrayList<String> ingredientList = FakeRecipeData.get().getKRecipe(nameOfFoodItem)
	                                                     .getIngredientList();
	
	    if (ingredientsAdapter == null) {
		    ingredientsAdapter = new IngredientsAdapter(ingredientList);
		    rvIngredientList.setAdapter(ingredientsAdapter);
	    } else {
		    ingredientsAdapter.notifyDataSetChanged();
	    }
    }
	
	@Override
	public void onResume() {
		super.onResume();
		updateUI();
	}
}
