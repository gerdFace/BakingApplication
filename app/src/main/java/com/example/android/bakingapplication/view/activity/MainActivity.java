package com.example.android.bakingapplication.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.adapter.RecipeCardAdapter;
import com.example.android.bakingapplication.model.RecipeData;
import com.example.android.bakingapplication.presentation.MainActivityPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeCardAdapter.RecipeCardAdapterOnClickHandler, MainActivityView {

    private Parcelable layoutManagerSavedState;

    @Inject
    MainActivityPresenter mainActivityPresenter;

    @BindView(R.id.rv_recipe_list)
    RecyclerView recipeCardRecyclerView;

    @BindView(R.id.connectivity_error_message)
    TextView connectivityErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((BakingApplication) getApplication()).getApplicationComponent().inject(this);

        ButterKnife.bind(this);
    }

    @Override
    public void onRecipeSelected(String recipeName, int recipeId) {
        Class recipeDetailActivityDestination = DetailListActivity.class;
        Intent openRecipeDetailActivity = new Intent(this, recipeDetailActivityDestination);
        openRecipeDetailActivity.putExtra("id_of_recipe_selected", recipeId);
        openRecipeDetailActivity.putExtra("name_of_recipe_selected", recipeName);
        startActivity(openRecipeDetailActivity);
    }

    @Override
    public void showRecipes(List<RecipeData> recipeList) {
        int numberOfColumns = !getResources().getBoolean(R.bool.isTablet) ? 1 : 2;

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns);
        recipeCardRecyclerView.setLayoutManager(layoutManager);

        RecipeCardAdapter recipeCardAdapter = new RecipeCardAdapter(this, recipeList, this);
        recipeCardRecyclerView.setAdapter(recipeCardAdapter);

        restoreLayoutManagerPosition();
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        if (!mainActivityPresenter.isDeviceOnline(this)) {
            connectivityErrorMessage.setVisibility(View.VISIBLE);
            recipeCardRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainActivityPresenter.setView(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (layoutManagerSavedState != null) {
            outState.putParcelable("saved_layout", recipeCardRecyclerView.getLayoutManager().onSaveInstanceState());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        layoutManagerSavedState = savedInstanceState.getParcelable("saved_layout");
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void restoreLayoutManagerPosition() {
        if (layoutManagerSavedState != null) {
            recipeCardRecyclerView.getLayoutManager().onRestoreInstanceState(layoutManagerSavedState);
        }
    }
}