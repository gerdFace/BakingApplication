package com.example.android.bakingapplication.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.adapter.RecipeCardAdapter;
import com.example.android.bakingapplication.model.RecipeData;
import com.example.android.bakingapplication.presentation.MainActivityPresenter;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements RecipeCardAdapter.RecipeCardAdapterOnClickHandler, MainActivityView {

    @Inject
    MainActivityPresenter mainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((BakingApplication) getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    public void onRecipeSelected(String nameOfFoodItemSelected, int foodID) {
        Class recipeDetailActivityDestination = DetailListActivity.class;
        Intent openRecipeDetailActivity = new Intent(this, recipeDetailActivityDestination);
        openRecipeDetailActivity.putExtra("id_of_food_selected", foodID);
        openRecipeDetailActivity.putExtra("name_of_food_selected", nameOfFoodItemSelected);
        startActivity(openRecipeDetailActivity);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRecipes(List<RecipeData> recipeList) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_recipe_list);

        RecipeCardAdapter recipeCardAdapter = new RecipeCardAdapter(this, recipeList, this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recipeCardAdapter);
    }

    @Override
    public void showErrorMessage(String errorMessage) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mainActivityPresenter.setView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainActivityPresenter.setView(this);
    }
}























