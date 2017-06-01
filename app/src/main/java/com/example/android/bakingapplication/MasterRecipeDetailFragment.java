package com.example.android.bakingapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MasterRecipeDetailFragment extends Fragment {

    public static final String TAG = RecipeDetailActivity.class.getClass().getSimpleName();

    @BindView(R.id.ingredients)
    TextView tVIngredients;

    public KRecipe recipe;

    public MasterRecipeDetailFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recipe = ((RecipeDetailActivity)this.getActivity()).getRecipe();
        Log.d(TAG, "onActivityCreated: " + recipe.getDessertName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_master_recipe_detail, container, false);
        ButterKnife.bind(this, view);
        tVIngredients.setText(R.string.detail_fragment_ingredients_title);
        return view;

    }
}
