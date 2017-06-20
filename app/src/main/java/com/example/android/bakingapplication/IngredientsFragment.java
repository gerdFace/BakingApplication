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

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsFragment extends Fragment {
	
	private static final String ARG_INGREDIENT_LIST = "ingredient_list";
	
	@BindView(R.id.rv_ingredient_list)
    RecyclerView rvIngredientList;

    public ArrayList<String> ingredientList;

    public IngredientsFragment() {
    }
    
    public static IngredientsFragment newInstance(ArrayList<String> stepDescriptionList) {
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_INGREDIENT_LIST, stepDescriptionList);
        
        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        ingredientsFragment.setArguments(args);
        return ingredientsFragment;
    }
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ingredientList = getArguments().getStringArrayList(ARG_INGREDIENT_LIST);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ingredient_list, container, false);

        ButterKnife.bind(this, view);

        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(ingredientList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayout.VERTICAL, false);

        rvIngredientList.setLayoutManager(layoutManager);

        rvIngredientList.setAdapter(ingredientsAdapter);

        return view;
    }

    public void setIngredientList(ArrayList<String> ingredientList) {
        this.ingredientList = ingredientList;
    }
}
