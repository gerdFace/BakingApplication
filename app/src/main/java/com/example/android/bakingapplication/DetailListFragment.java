package com.example.android.bakingapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.android.bakingapplication.activity.DetailListActivity;
import com.example.android.bakingapplication.model.KRecipe;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailListFragment extends Fragment {

    public static final String TAG = DetailListActivity.class.getClass().getSimpleName();

    @BindView(R.id.ingredients)
    Button bIngredients;

    public KRecipe recipe;

    public onRecipeDetailItemSelected listener;

    public DetailListFragment() {
    }

    public interface onRecipeDetailItemSelected {
        void onRecipeDetailButtonClicked(int buttonPressed);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recipe = ((DetailListActivity) this.getActivity()).getRecipe();

        Log.d(TAG, "onActivityCreated: " + recipe.getDessertName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_list, container, false);
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


