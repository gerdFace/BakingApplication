package com.example.android.bakingapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import com.example.android.bakingapplication.R;
import com.example.android.bakingapplication.adapter.RecipeCardAdapter;
import com.example.android.bakingapplication.model.FakeRecipeData;
import com.example.android.bakingapplication.model.KRecipe;
import com.example.android.bakingapplication.model.RecipeDatum;
import com.example.android.bakingapplication.retrofit.RecipeService;

import java.util.List;
import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements RecipeCardAdapter.RecipeCardAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

//    @Inject
//    FakeRecipeData fakeRecipeData;

    @Inject
    Retrofit retrofit;

    private List<RecipeDatum> recipeList;


    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((BakingApplication) getApplication()).getAppComponent().inject(this);

        Call<List<RecipeDatum>> recipeCall = retrofit.create(RecipeService.class).getRecipes();

        recipeCall.enqueue(new Callback<List<RecipeDatum>>() {

            @Override
            public void onResponse(Call<List<RecipeDatum>> call, Response<List<RecipeDatum>> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    recipeList = response.body();
                    Log.d(TAG, "Recipe data was loaded from website");
                    configureUI();
                }
            }

            @Override
            public void onFailure(Call<List<RecipeDatum>> call, Throwable t) {
                Log.d(TAG, "onFailure: Could not load recipe data from network path" + t.toString());
            }
        });

//        Log.d(TAG, "onCreate: detail list contains: " + recipeList.get(0).getIngredients().get(0) + recipeList.get(1).getSteps().get(0));
    }

    private void configureUI() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_recipe_list);

        RecipeCardAdapter recipeCardAdapter = new RecipeCardAdapter(this, recipeList, this);
        // FakeRecipeData fakeRecipeData = FakeRecipeData.get();
//        List<KRecipe> recipeList = fakeRecipeData.getRecipeList();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recipeCardAdapter);
    }
    
    @Override
    public void onRecipeSelected(String selectedRecipe) {
        Class recipeDetailActivityDestination = DetailListActivity.class;
        Intent openRecipeDetailActivity = new Intent(this, recipeDetailActivityDestination);
        openRecipeDetailActivity.putExtra("name_of_food_item_selected", selectedRecipe);
        Log.d(TAG, "onRecipeSelected: " + selectedRecipe);
        startActivity(openRecipeDetailActivity);
    }
}






















