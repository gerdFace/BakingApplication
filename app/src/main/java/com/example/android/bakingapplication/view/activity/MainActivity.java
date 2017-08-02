package com.example.android.bakingapplication.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.adapter.RecipeCardAdapter;
import com.example.android.bakingapplication.model.RecipeData;
import com.example.android.bakingapplication.repository.RecipeRepository;
import com.example.android.bakingapplication.repository.RecipeRepositoryImpl;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements RecipeCardAdapter.RecipeCardAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private List<RecipeData> recipeList;

    @Inject
    RecipeRepositoryImpl recipeRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((BakingApplication) getApplication()).getRecipeRepositoryComponent().inject(this);

        recipeRepository.getRecipes(new RecipeRepository.LoadRecipesCallback() {
            @Override
            public void onRecipesLoaded(List<RecipeData> recipes) {
                recipeList = recipes;
            }

            @Override
            public void onDataNotAvailable(String failureMessage) {
                Log.d(TAG, failureMessage);
            }
        });

        configureUI();
    }

    private void configureUI() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_recipe_list);

        RecipeCardAdapter recipeCardAdapter = new RecipeCardAdapter(this, recipeList, this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recipeCardAdapter);
    }

    @Override
    public void onRecipeSelected(String nameOfFoodItemSelected, int foodID) {
        Class recipeDetailActivityDestination = DetailListActivity.class;
        Intent openRecipeDetailActivity = new Intent(this, recipeDetailActivityDestination);
        openRecipeDetailActivity.putExtra("id_of_food_selected", foodID);
        openRecipeDetailActivity.putExtra("name_of_food_selected", nameOfFoodItemSelected);
        Log.d(TAG, "onRecipeSelected: " + nameOfFoodItemSelected + "ID: " + foodID);
        startActivity(openRecipeDetailActivity);
    }
}























