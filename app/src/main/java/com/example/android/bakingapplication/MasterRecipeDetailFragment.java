package com.example.android.bakingapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.bakingapplication.activity.RecipeDetailActivity;
import com.example.android.bakingapplication.model.KRecipe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MasterRecipeDetailFragment extends Fragment {

    public interface onRecipeDetailItemSelected {
        void onRecipeDetailButtonClicked(int buttonPressed);
    }

    public static final String TAG = RecipeDetailActivity.class.getClass().getSimpleName();

    @BindView(R.id.ingredients)
    Button bIngredients;

    public KRecipe recipe;

    public onRecipeDetailItemSelected listener;


    public MasterRecipeDetailFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recipe = ((RecipeDetailActivity) this.getActivity()).getRecipe();
        Log.d(TAG, "onActivityCreated: " + recipe.getDessertName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_master_recipe_detail, container, false);
        ButterKnife.bind(this, view);
        bIngredients.setText(R.string.detail_fragment_ingredients_title);
        bIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRecipeDetailButtonClicked(v.getId());
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (onRecipeDetailItemSelected) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                                                 + " must implement OnImageClickListener");
        }
    }
}


