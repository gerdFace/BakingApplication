package com.example.android.bakingapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bakingapplication.activity.RecipeDetailActivity;
import com.example.android.bakingapplication.adapter.IngredientsAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsFragment extends Fragment {

    @BindView(R.id.rv_ingredients)
    RecyclerView ingredientsRecyclerView;

    public List<String> ingredientList;

    public IngredientsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);

        ButterKnife.bind(this, view);

        ingredientList = ((RecipeDetailActivity) this.getActivity()).getRecipe().getIngredientList();

        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(ingredientList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayout.VERTICAL, false);

        ingredientsRecyclerView.setLayoutManager(layoutManager);

        ingredientsRecyclerView.setAdapter(ingredientsAdapter);

        return view;
    }

}
